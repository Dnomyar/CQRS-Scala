package fr.damienraymond.cqrs.core.event

import scala.reflect.runtime.universe._

import fr.damienraymond.cqrs.core.Message

import scala.concurrent.Future

trait EventCaptor [EVENT <: Event[_]] {

  def execute(event: EVENT): Future[Unit]

  def eventType[T <: Message[_] : TypeTag]: Type = typeOf[T]

}
