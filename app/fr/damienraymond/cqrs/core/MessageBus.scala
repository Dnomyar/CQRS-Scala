package fr.damienraymond.cqrs.core

import fr.damienraymond.cqrs.core.event.Event

import scala.concurrent.Future

trait CommandBus {

  def dispatch[RETURN_TYPE](message: Command[RETURN_TYPE]): Future[(RETURN_TYPE, List[Event[_]])]

}

trait QueryBus {

  def dispatch[RETURN_TYPE](message: Query[RETURN_TYPE]): Future[RETURN_TYPE]

}
