package fr.damienraymond.cqrs.core.event

import scala.concurrent.Future

trait EventCaptor [EVENT <: Event[_]] {

  def execute(event: EVENT): Future[Unit]

  def eventType: Class[EVENT]

}
