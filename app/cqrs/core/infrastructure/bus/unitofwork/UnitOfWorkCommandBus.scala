package fr.damienraymond.cqrs.core.infrastructure.bus.unitofwork

import fr.damienraymond.cqrs.core.Command
import fr.damienraymond.cqrs.core.event.Event

import scala.concurrent.Future

trait UnitOfWorkCommandBus {

  def dispatch[MESSAGE <: Command[RETURN_TYPE], RETURN_TYPE](message: MESSAGE)(implicit messageClass: Manifest[MESSAGE]): Future[(RETURN_TYPE, List[Event[_]])]

}
