package fr.damienraymond.cqrs.core.infrastructure.bus.unitofwork

import fr.damienraymond.cqrs.core.event.Event
import fr.damienraymond.cqrs.core.infrastructure.bus.NoHandlerFoundException
import fr.damienraymond.cqrs.core.middleware.CommandMiddleware
import fr.damienraymond.cqrs.core.persistence.UnitOfWorkFactory
import fr.damienraymond.cqrs.core.{Command, CommandHandler, UnitOfWorkCommandHandler}

import scala.concurrent.{ExecutionContext, Future}
import scala.reflect.runtime.universe._


class UnitOfWorkCommandBusImplementation private[bus](handlers: Set[CommandHandler[Command[Any],Any]], uowFactory: UnitOfWorkFactory)(val middlewares: List[CommandMiddleware] = List.empty)(implicit ec: ExecutionContext) extends UnitOfWorkCommandBus {


  protected lazy val middlewareChain: UnitOfWorkChain =
    middlewares.foldRight[UnitOfWorkChain](UnitOfWorkHandlerInvocation(classHandlerMap))(UnitOfWorkMiddlewareChainLink)


  protected val classHandlerMap: Map[Type, CommandHandler[Command[_], _]] =
    handlers.map(handler => handler.messageType -> handler).toMap


  override def dispatch[RETURN_TYPE, COMMAND <: Command[RETURN_TYPE] : TypeTag](message: COMMAND): Future[(RETURN_TYPE, List[Event[_]])] =
    middlewareChain.handleMiddlewareAndCallNext[RETURN_TYPE, COMMAND](message)


  trait UnitOfWorkChain {
    def handleMiddlewareAndCallNext[RETURN_T, COMMAND <: Command[RETURN_T] : TypeTag](message: COMMAND): Future[(RETURN_T, List[Event[_]])]
  }

  case class UnitOfWorkMiddlewareChainLink(current: CommandMiddleware, next: UnitOfWorkChain) extends UnitOfWorkChain {
    override def handleMiddlewareAndCallNext[RETURN_T, COMMAND <: Command[RETURN_T] : TypeTag](message: COMMAND): Future[(RETURN_T, List[Event[_]])] = {
      println(s"Applying middleware : $current")
      current.apply[RETURN_T](message, () => next.handleMiddlewareAndCallNext[RETURN_T, COMMAND](message))
    }
  }


  case class UnitOfWorkHandlerInvocation(handlers: Map[Type, CommandHandler[Command[_], _]]) extends UnitOfWorkChain {
    override def handleMiddlewareAndCallNext[RETURN_T, COMMAND <: Command[RETURN_T] : TypeTag](message: COMMAND): Future[(RETURN_T, List[Event[_]])] = {
      handlers
        .get(typeOf[COMMAND]) match {
        case Some(handler) =>
          val uow = uowFactory.create
          val res = handler
            .asInstanceOf[UnitOfWorkCommandHandler[Command[RETURN_T], RETURN_T]]
            .handle(message)(uow)
          uow.commit.map(_ => res)
        case None =>
          Future.failed(new NoHandlerFoundException(typeOf[COMMAND].toString)) // TODO <-
      }
    }
  }

}

