package fr.damienraymond.cqrs.core.infrastructure.bus.unitofwork

import com.google.inject.Inject
import fr.damienraymond.cqrs.core.middleware.CommandMiddleware
import fr.damienraymond.cqrs.core.persistence.UnitOfWorkFactory
import fr.damienraymond.cqrs.core.{Command, CommandHandler}

import scala.concurrent.ExecutionContext


class UnitOfWorkCommandBusFactory @Inject()(handlers: Set[CommandHandler[Command[Any],Any]],
                                  uowFactory: UnitOfWorkFactory)(implicit ec: ExecutionContext) {

  def create(middlewares: List[CommandMiddleware]): UnitOfWorkCommandBus = new UnitOfWorkCommandBusImplementation(handlers, uowFactory)(middlewares)

}