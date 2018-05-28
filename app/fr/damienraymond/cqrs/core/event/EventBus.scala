package fr.damienraymond.cqrs.core.event

import scala.concurrent.Future
import scala.reflect.runtime.universe._

trait EventBus {
  def publish(events: List[Event[_]]): Future[Unit]
}
