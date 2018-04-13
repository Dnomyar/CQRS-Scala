package fr.damienraymond.cqrs.core.infrastructure.bus.unitofwork

import fr.damienraymond.cqrs.core.Command
import fr.damienraymond.cqrs.core.event.Event

import scala.concurrent.Future
import scala.reflect.runtime.universe._


trait UnitOfWorkCommandBus {

  def dispatch[RETURN_TYPE, MESSAGE <: Command[RETURN_TYPE] : TypeTag](message: MESSAGE): Future[(RETURN_TYPE, List[Event[_]])]

}
