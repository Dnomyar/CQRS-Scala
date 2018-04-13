package fr.damienraymond.cqrs.core


import fr.damienraymond.cqrs.core.event.Event
import fr.damienraymond.cqrs.core.persistence.UnitOfWork

import scala.concurrent.Future
import scala.reflect.runtime.universe._



abstract class CommandHandler[COMMAND <: Command[TARGET_TYPE] : TypeTag, TARGET_TYPE] {

  def handle(cmd: COMMAND): (TARGET_TYPE, List[Event[_]])

  def messageType: Type = typeOf[COMMAND]

}

abstract class UnitOfWorkCommandHandler[COMMAND <: Command[TARGET_TYPE] : TypeTag, TARGET_TYPE] {

  def handle(cmd: COMMAND)(implicit uow: UnitOfWork): (TARGET_TYPE, List[Event[_]])

  def messageType: Type = typeOf[COMMAND]

}

abstract class QueryHandler[QUERY <: Query[TARGET_TYPE] : TypeTag, TARGET_TYPE] {

  def handle(query: QUERY): Future[TARGET_TYPE]

  def messageType: Type = typeOf[QUERY]

}
