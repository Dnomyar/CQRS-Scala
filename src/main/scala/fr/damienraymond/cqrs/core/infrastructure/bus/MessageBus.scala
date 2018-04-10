package fr.damienraymond.cqrs.core.infrastructure.bus

import fr.damienraymond.cqrs.core
import fr.damienraymond.cqrs.core.{Handler, Message}
import fr.damienraymond.cqrs.core.event.Event
import fr.damienraymond.cqrs.core.middleware.Middleware

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


  def dispatch[RETURN_TYPE](message: Message[RETURN_TYPE])(implicit messageClass: Manifest[Message[RETURN_TYPE]]): Either[String, (RETURN_TYPE, List[Event[_]])] =
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




