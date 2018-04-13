package fr.damienraymond.cqrs.core.infrastructure.bus

import com.google.inject.Inject
import fr.damienraymond.cqrs.core
import fr.damienraymond.cqrs.core._
import fr.damienraymond.cqrs.core.event.Event
import fr.damienraymond.cqrs.core.infrastructure.event.SynchronizedEventBusMiddleware
import fr.damienraymond.cqrs.core.middleware.{CommandMiddleware, Middleware, QueryMiddleware}
import org.apache.commons.lang3.exception.ExceptionContext

import scala.concurrent.{ExecutionContext, Future}
import scala.language.higherKinds
import scala.reflect.runtime.universe
import scala.reflect.runtime.universe._


/**
  * TODO : A Query bus can have command
  */
trait MessageBus {
//
//  protected val middlewares: List[Middleware]
//
//  protected val classHandlerMap: Map[Type, Handler[Message[_], _]]
//
//  protected lazy val middlewareChain: Chain =
//    middlewares.foldRight[Chain](HandlerInvocation(classHandlerMap))(MiddlewareChainLink)
//
//
//  def dispatch[RETURN_TYPE](message: Message[RETURN_TYPE])(implicit messageClass: Manifest[Message[RETURN_TYPE]]): Either[String, (RETURN_TYPE, List[Event[_]])] =
//    middlewareChain.handleMiddlewareAndCallNext(message)
//
//
//
//  trait Chain {
//    def handleMiddlewareAndCallNext[RETURN_T](message: Message[RETURN_T]): Either[String, (RETURN_T, List[Event[_]])]
//  }
//
//  case class MiddlewareChainLink(current: Middleware, next: Chain) extends Chain {
//    override def handleMiddlewareAndCallNext[RETURN_T](message: Message[RETURN_T]): Either[String, (RETURN_T, List[Event[_]])] = {
//      println(s"Applying middleware : $current")
//      current.apply[RETURN_T](message, () => next.handleMiddlewareAndCallNext(message))
//    }
//  }
//
//  case class HandlerInvocation(handlers: Map[Type, Handler[Message[_], _]]) extends Chain {
//    override def handleMiddlewareAndCallNext[RETURN_T](message: Message[RETURN_T]): Either[String, (RETURN_T, List[Event[_]])] = {
//      handlers
//        .get(typeOf[message]) match {
//        case Some(handler) =>
//          Right(
//            handler
//              .asInstanceOf[Handler[Message[RETURN_T], RETURN_T]]
//              .handle(message))
//        case None => Left("No handler found") // TODO <-
//      }
//    }
//  }


}


class CommandBus @Inject()(handlers: Set[CommandHandler[Command[Any],Any]],
                           eventBusMiddleware: SynchronizedEventBusMiddleware)(implicit ec: ExecutionContext) extends Logger {

  protected val middlewares: List[CommandMiddleware] = List(eventBusMiddleware)

  protected val classHandlerMap: Map[universe.Type, CommandHandler[Command[_], _]] =
    handlers.map(handler => handler.messageType -> handler).toMap

  protected lazy val middlewareChain: Chain =
    middlewares.foldRight[Chain](HandlerInvocation(classHandlerMap))(MiddlewareChainLink)


  def dispatch[RETURN_TYPE](message: Command[RETURN_TYPE]): Future[(RETURN_TYPE, List[Event[_]])] =
    middlewareChain.handleMiddlewareAndCallNext(message)



  trait Chain {
    def handleMiddlewareAndCallNext[RETURN_T](message: Command[RETURN_T]): Future[(RETURN_T, List[Event[_]])]
  }

  case class MiddlewareChainLink(current: CommandMiddleware, next: Chain) extends Chain {
    override def handleMiddlewareAndCallNext[RETURN_T](message: Command[RETURN_T]): Future[(RETURN_T, List[Event[_]])] = {
      println(s"Applying middleware : $current")
      current.apply[RETURN_T](message, () => next.handleMiddlewareAndCallNext(message))
    }
  }

  case class HandlerInvocation(handlers: Map[Type, CommandHandler[Command[_], _]]) extends Chain {
    override def handleMiddlewareAndCallNext[RETURN_T](message: Command[RETURN_T]): Future[(RETURN_T, List[Event[_]])] = {
      handlers
        .get(message.thisType) match {
        case Some(handler) =>
          logger.debug(s"Calling ${handler.getClass}")
          Future(handler
            .asInstanceOf[CommandHandler[Command[RETURN_T], RETURN_T]]
            .handle(message))
        case None => Future.failed(new NoHandlerFoundException(message.thisType.toString))
      }
    }
  }
}


class QueryBus @Inject()(handlers: Set[QueryHandler[Query[Any],Any]]) extends Logger {

  protected val middlewares: List[QueryMiddleware] = List.empty

  protected val classHandlerMap: Map[universe.Type, QueryHandler[Query[_], _]] =
    handlers.map(handler => handler.messageType -> handler).toMap

  protected lazy val middlewareChain: Chain =
    middlewares.foldRight[Chain](HandlerInvocation(classHandlerMap))(MiddlewareChainLink)


  def dispatch[RETURN_TYPE](message: Query[RETURN_TYPE]): Future[RETURN_TYPE] =
    middlewareChain.handleMiddlewareAndCallNext(message)



  trait Chain {
    def handleMiddlewareAndCallNext[RETURN_T](message: Query[RETURN_T]): Future[RETURN_T]
  }

  case class MiddlewareChainLink(current: QueryMiddleware, next: Chain) extends Chain {
    override def handleMiddlewareAndCallNext[RETURN_T](message: Query[RETURN_T]): Future[RETURN_T] = {
      println(s"Applying middleware : $current")
      current.apply[RETURN_T](message, () => next.handleMiddlewareAndCallNext(message))
    }
  }

  case class HandlerInvocation(handlers: Map[Type, QueryHandler[Query[_], _]]) extends Chain {
    override def handleMiddlewareAndCallNext[RETURN_T](message: Query[RETURN_T]): Future[RETURN_T] = {
      handlers
        .get(message.thisType) match {
        case Some(handler) =>
          logger.debug(s"Calling ${handler.getClass}")
          handler
            .handle(message)
            .asInstanceOf[Future[RETURN_T]]
        case None => Future.failed(new NoHandlerFoundException(message.thisType.toString))
      }
    }
  }
}

