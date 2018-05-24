package fr.damienraymond.cqrs.core.web.context.communication


case class ServiceCallException(method: String, path: String, status: Int, statusText: String)
  extends Exception(s"ServiceCall [$method $path] returned $statusText ($status)")
