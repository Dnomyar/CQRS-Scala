package fr.damienraymond.cqrs.core.event

import scala.reflect.runtime.universe._

import fr.damienraymond.cqrs.core.Message

import scala.concurrent.Future

abstract class EventCaptor [EVENT <: Event[_]: TypeTag] {

  def execute(event: EVENT): Future[Unit]

  def eventType: Type = typeOf[EVENT]

}
