package fr.damienraymond.cqrs.core

import scala.reflect.runtime.universe._

trait Message[+RETURN_TYPE] {

  def messageType[T <: Message[_] : TypeTag]: Type = typeOf[T]

}

trait Command[+RETURN_TYPE] extends Message[RETURN_TYPE]

trait Query[+RETURN_TYPE] extends Message[RETURN_TYPE]
