package fr.damienraymond.cqrs.core.infrastructure.bus

import com.google.inject.Inject
import fr.damienraymond.cqrs.core.event.Event
import fr.damienraymond.cqrs.core.middleware.{CommandMiddleware, Middleware}
import fr.damienraymond.cqrs.core.persistence.UnitOfWorkFactory
import fr.damienraymond.cqrs.core.{Command, CommandHandler, Handler, Message}

import scala.concurrent.{ExecutionContext, Future}
import scala.language.higherKinds
import scala.reflect.runtime.universe._


/**
  * TODO : A Query bus can have command
  */
trait MessageBus {

  val middlewares: List[Middleware]

  protected val classHandlerMap: Map[Type, Handler[Message[_], _]]

  protected lazy val middlewareChain: Chain =
    middlewares.foldRight[Chain](HandlerInvocation(classHandlerMap))(MiddlewareChainLink)


  def dispatch[MESSAGE <: Message[RETURN_TYPE], RETURN_TYPE](message: MESSAGE)(implicit messageClass: Manifest[MESSAGE]): Either[String, (RETURN_TYPE, List[Event[_]])] =
    middlewareChain.handleMiddlewareAndCallNext(message)



  trait Chain {
    def handleMiddlewareAndCallNext[RETURN_T](message: Message[RETURN_T]): Either[String, (RETURN_T, List[Event[_]])]
  }

  case class MiddlewareChainLink(current: Middleware, next: Chain) extends Chain {
    override def handleMiddlewareAndCallNext[RETURN_T](message: Message[RETURN_T]): Either[String, (RETURN_T, List[Event[_]])] = {
      println(s"Applying middleware : $current")
      current.apply[RETURN_T](message, () => next.handleMiddlewareAndCallNext(message))
    }
  }

  case class HandlerInvocation(handlers: Map[Type, Handler[Message[_], _]]) extends Chain {
    override def handleMiddlewareAndCallNext[RETURN_T](message: Message[RETURN_T]): Either[String, (RETURN_T, List[Event[_]])] = {
      handlers
        .get(message.messageType) match {
        case Some(handler) =>
          Right(
            handler
              .asInstanceOf[Handler[Message[RETURN_T], RETURN_T]]
              .handle(message))
        case None => Left("No handler found") // TODO <-
      }
    }
  }


}


//class QueryBus(override val middlewares: List[Middleware] = List.empty) extends MessageBus



class CommandBusFactory @Inject()(handlers: Set[CommandHandler[Command[Any],Any]],
                                  uowFactory: UnitOfWorkFactory)(implicit ec: ExecutionContext) {

  def create(middlewares: List[CommandMiddleware]): UnitOfWorkCommandBus = new UnitOfWorkCommandBus(handlers, uowFactory)(middlewares)

}

//class CommandBus private[bus] (handlers: Set[Handler[Message[Any],Any]])(override val middlewares: List[CommandMiddleware] = List.empty) extends MessageBus {
//
//  override protected val classHandlerMap: Map[Type, Handler[Message[_], _]] =
//    handlers.map(handler => handler.messageType -> handler).toMap
//
//}




class UnitOfWorkCommandBus private[bus](handlers: Set[CommandHandler[Command[Any],Any]], uowFactory: UnitOfWorkFactory)(val middlewares: List[CommandMiddleware] = List.empty)(implicit ec: ExecutionContext) {


  protected lazy val middlewareChain: UnitOfWorkChain =
    middlewares.foldRight[UnitOfWorkChain](UnitOfWorkHandlerInvocation(classHandlerMap))(UnitOfWorkMiddlewareChainLink)


  protected val classHandlerMap: Map[Type, CommandHandler[Command[_], _]] =
    handlers.map(handler => handler.messageType -> handler).toMap


  def dispatch[MESSAGE <: Command[RETURN_TYPE], RETURN_TYPE](message: MESSAGE)(implicit messageClass: Manifest[MESSAGE]): Future[(RETURN_TYPE, List[Event[_]])] =
    middlewareChain.handleMiddlewareAndCallNext(message)


  trait UnitOfWorkChain {
    def handleMiddlewareAndCallNext[RETURN_T](message: Command[RETURN_T]): Future[(RETURN_T, List[Event[_]])]
  }

  case class UnitOfWorkMiddlewareChainLink(current: CommandMiddleware, next: UnitOfWorkChain) extends UnitOfWorkChain {
    override def handleMiddlewareAndCallNext[RETURN_T](message: Command[RETURN_T]): Future[(RETURN_T, List[Event[_]])] = {
      println(s"Applying middleware : $current")
      current.apply[RETURN_T](message, () => next.handleMiddlewareAndCallNext(message))
    }
  }


  case class UnitOfWorkHandlerInvocation(handlers: Map[Type, CommandHandler[Command[_], _]]) extends UnitOfWorkChain {
    override def handleMiddlewareAndCallNext[RETURN_T](message: Command[RETURN_T]): Future[(RETURN_T, List[Event[_]])] = {
      handlers
        .get(message.messageType) match {
        case Some(handler) =>
          val uow = uowFactory.create
          val res = handler
            .asInstanceOf[CommandHandler[Command[RETURN_T], RETURN_T]]
            .handle(message)(uow)
          uow.commit.map(_ => res)
        case None =>
          Future.failed(new NoHandlerFoundException) // TODO <-
      }
    }
  }

}


