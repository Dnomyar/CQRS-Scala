package fr.damienraymond.cqrs.core

import scala.reflect.runtime.universe._

abstract class Message[+RETURN_TYPE : TypeTag] {

  lazy val thisType: Type = {
    val cls = this.getClass
    runtimeMirror(cls.getClassLoader).classSymbol(cls).toType
  }

}


abstract class Command[+RETURN_TYPE : TypeTag] extends Message[RETURN_TYPE]

abstract class Query[+RETURN_TYPE : TypeTag] extends Message[RETURN_TYPE]
