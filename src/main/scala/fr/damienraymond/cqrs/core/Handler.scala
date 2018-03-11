package fr.damienraymond.cqrs.core

import fr.damienraymond.cqrs.core.event.Event

import scala.reflect.runtime.universe._


trait Handler[MESSAGE <: Message[TARGET_TYPE], TARGET_TYPE] {

  def handle(message: MESSAGE): (TARGET_TYPE, List[Event[_]])

  def messageType[T <: Message[TARGET_TYPE] : TypeTag]: Type = typeOf[T]
}


trait CommandHandler[COMMAND <: Command[TARGET_TYPE], TARGET_TYPE] {
  def handle(message: COMMAND): (TARGET_TYPE, List[Event[_]])
}

trait QueryHandler[QUERY <: Query[TARGET_TYPE], TARGET_TYPE] extends Handler[QUERY, TARGET_TYPE]
