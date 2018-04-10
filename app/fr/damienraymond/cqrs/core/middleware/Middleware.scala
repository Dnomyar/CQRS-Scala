package fr.damienraymond.cqrs.core.middleware

import fr.damienraymond.cqrs.core.{Command, Message}
import fr.damienraymond.cqrs.core.event.Event

import scala.concurrent.Future

trait Middleware {

  def apply[RETURN_T](value: Message[RETURN_T], next: () => Either[String, (RETURN_T, List[Event[_]])]): Either[String, (RETURN_T, List[Event[_]])]

}

/**
  * TODO : how to allow only messages with type subtype command
  */

trait CommandMiddleware {

  def apply[RETURN_T](value: Message[RETURN_T], next: () => Future[(RETURN_T, List[Event[_]])]): Future[(RETURN_T, List[Event[_]])]

}


trait QueryMiddleware {

  def apply[RETURN_T](value: Message[RETURN_T], next: () => Future[RETURN_T]): Future[RETURN_T]

}
