package fr.damienraymond.cqrs.core.event

import fr.damienraymond.cqrs.core.middleware.CommandMiddleware

import scala.concurrent.Future

trait EventBusMiddleware {

  def intercept[T](event: Event[T], next: () => Future[Either[String, (T, List[Event[_]])]])

}
