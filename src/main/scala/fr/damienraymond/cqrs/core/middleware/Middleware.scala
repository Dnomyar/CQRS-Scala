package fr.damienraymond.cqrs.core.middleware

import fr.damienraymond.cqrs.core.{Command, Message}
import fr.damienraymond.cqrs.core.event.Event

trait Middleware {

  def apply[RETURN_T](value: Message[RETURN_T], next: () => Either[String, (RETURN_T, List[Event[_]])]): Either[String, (RETURN_T, List[Event[_]])]

}

/**
  * TODO : how to allow only messages with type subtype command
  */

trait CommandMiddleware extends Middleware
