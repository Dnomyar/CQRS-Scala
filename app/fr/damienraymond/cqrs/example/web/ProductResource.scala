package fr.damienraymond.cqrs.example.web

import com.google.inject.Inject
import fr.damienraymond.cqrs.core.infrastructure.bus.QueryBus
import fr.damienraymond.cqrs.example.queries.product.all.handlers.AllProducts
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext

class ProductResource @Inject() (cc: ControllerComponents,
                                 queryBus: QueryBus)(implicit ec: ExecutionContext) extends AbstractController(cc) {


  def index = Action.async {
    queryBus.dispatch(AllProducts())
      .map(ps => Ok(Json.toJson(ps)))
      .recover{
        case e => InternalServerError(e.toString)
      }
  }


}
