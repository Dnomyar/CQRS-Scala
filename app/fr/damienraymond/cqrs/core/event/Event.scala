package fr.damienraymond.cqrs.core.event

import fr.damienraymond.cqrs.core.Message

import scala.reflect.runtime.universe._

abstract class Event[T: TypeTag] extends Message[T]
