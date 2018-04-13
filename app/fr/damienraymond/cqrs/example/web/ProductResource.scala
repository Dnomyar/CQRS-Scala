package fr.damienraymond.cqrs.example.web

import java.util.UUID

import com.google.inject.Inject
import fr.damienraymond.cqrs.core.infrastructure.bus.{CommandBus, QueryBus}
import fr.damienraymond.cqrs.example.commands.BuyProductsCommand
import fr.damienraymond.cqrs.example.queries.product.allusingrepositories.handlers.{AllProducts, ProductById}
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext

class ProductResource @Inject() (cc: ControllerComponents,
                                 queryBus: QueryBus,
                                 commandBus: CommandBus)(implicit ec: ExecutionContext) extends AbstractController(cc) {


  def index = Action.async {
    queryBus.dispatch(AllProducts())
      .map(ps => Ok(Json.toJson(ps)))
      .recover{
        case e => InternalServerError(e.toString)
      }
  }


  def buy(productId: UUID) = Action.async(parse.json[BuyProductsCommand]) { implicit request =>
    (for {
      (_, _) <- commandBus.dispatch(request.body)
      product <- queryBus.dispatch(ProductById(request.body.productId))
    } yield product)
      .map(product => Ok(Json.toJson(product)))
      .recover{
        case e => InternalServerError(e.toString)
      }
  }


}
