package fr.damienraymond.cqrs.core.event

import scala.concurrent.Future
import scala.reflect.runtime.universe._

trait SynchronizedEventBus {
  def publish(events: List[Event[_]]): Future[Unit]
}



trait AsynchronizedEventBus {
  def publish(events: List[Event[_]]): Unit
}
