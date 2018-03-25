package fr.damienraymond.cqrs.core.infrastructure.bus

import com.google.inject.Inject
import fr.damienraymond.cqrs.core.event.Event
import fr.damienraymond.cqrs.core.middleware.{CommandMiddleware, Middleware}
import fr.damienraymond.cqrs.core.{Command, CommandHandler, Handler, Message}

import scala.language.higherKinds
import scala.reflect.runtime.universe._


/**
  * TODO : A Query bus can have command
  */
trait MessageBus {

  val middlewares: List[Middleware]

  protected val classHandlerMap: Map[Type, Handler[Message[_], _]]

  private lazy val middlewareChain: Chain =
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



class CommandBusFactory @Inject()(handlers: Set[Handler[Message[Any],Any]]) {

  def create(middlewares: List[CommandMiddleware]): CommandBus = new CommandBus(handlers)(middlewares)

}

class CommandBus private[bus] (handlers: Set[Handler[Message[Any],Any]])(override val middlewares: List[CommandMiddleware] = List.empty) extends MessageBus {

  override protected val classHandlerMap: Map[Type, Handler[Message[_], _]] =
    handlers.map(handler => handler.messageType -> handler).toMap

}


