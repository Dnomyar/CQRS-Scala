package fr.damienraymond.cqrs.example.web

import com.google.inject.Inject
import fr.damienraymond.cqrs.core.infrastructure.bus.{CommandBus, QueryBus}
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext

class App @Inject() (cc: ControllerComponents)(implicit ec: ExecutionContext) extends AbstractController(cc) {


  def renderAppPage = Action {
    Ok(fr.damienraymond.cqrs.example.web.front.html.index())
  }


}
