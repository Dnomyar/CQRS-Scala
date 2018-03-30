package fr.damienraymond.cqrs.core


import fr.damienraymond.cqrs.core.event.Event
import fr.damienraymond.cqrs.core.persistence.UnitOfWork

import scala.concurrent.Future
import scala.reflect.runtime.universe._


trait Handler[MESSAGE <: Message[TARGET_TYPE], TARGET_TYPE] {

  def handle(message: MESSAGE): (TARGET_TYPE, List[Event[_]])

  def messageType[T <: Message[TARGET_TYPE] : TypeTag]: Type = typeOf[T]
}


trait CommandHandler[COMMAND <: Command[TARGET_TYPE], TARGET_TYPE] {

  def handle(message: COMMAND)(implicit uow: UnitOfWork): (TARGET_TYPE, List[Event[_]])

  def messageType[T <: Message[TARGET_TYPE] : TypeTag]: Type = typeOf[T]

}

trait QueryHandler[QUERY <: Query[TARGET_TYPE], TARGET_TYPE] {

  def handle(message: QUERY): Future[TARGET_TYPE]

}
