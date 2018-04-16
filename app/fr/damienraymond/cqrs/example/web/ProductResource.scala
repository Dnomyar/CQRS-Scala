package fr.damienraymond.cqrs.example.web

import java.util.UUID

import com.google.inject.Inject
import fr.damienraymond.cqrs.core.Logger
import fr.damienraymond.cqrs.core.event.error.BusinessError
import fr.damienraymond.cqrs.core.infrastructure.bus.{CommandBus, QueryBus}
import fr.damienraymond.cqrs.example.commands.BuyProductsCommand
import fr.damienraymond.cqrs.example.queries.product.allusingrepositories.handlers.{AllProductsQuery, ProductByIdQuery}
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext

class ProductResource @Inject() (cc: ControllerComponents,
                                 queryBus: QueryBus,
                                 commandBus: CommandBus)(implicit ec: ExecutionContext) extends AbstractController(cc) with Logger {


  def getAllProducts = Action.async {
    queryBus.dispatch(AllProductsQuery())
      .map(ps => Ok(Json.toJson(ps)))
      .recover{
        case e =>
          logger.error(e.toString)
          e.printStackTrace
          InternalServerError(e.toString)
      }
  }


  def findProductById(productId: UUID) = Action.async {
    queryBus.dispatch(ProductByIdQuery(productId))
      .map(ps => Ok(Json.toJson(ps)))
      .recover{
        case e =>
          logger.error(e.toString)
          e.printStackTrace
          InternalServerError(e.toString)
      }
  }


  def buy(productId: UUID) = Action.async(parse.json[BuyProductsCommand]) { implicit request =>
    commandBus.dispatch(request.body).map{
      case (_, events) =>

        val errors = events.collect{
          case error: BusinessError[_] => error.toString
        }

        if (errors.nonEmpty)
          Conflict(Json.arr(errors))
        else
          NoContent.withHeaders(LOCATION -> routes.ProductResource.findProductById(request.body.productId).url)
    }
  }





}
