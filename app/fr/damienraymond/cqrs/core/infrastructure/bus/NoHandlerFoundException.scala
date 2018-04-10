package fr.damienraymond.cqrs.core.infrastructure.bus

class NoHandlerFoundException(messageType: String)
  extends Exception(s"Handler corresponding to `$messageType` not found")
