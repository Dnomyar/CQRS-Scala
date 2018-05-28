package fr.damienraymond.cqrs.core.event

import fr.damienraymond.cqrs.core.middleware.CommandMiddleware

import scala.concurrent.Future

trait EventBusMiddleware extends CommandMiddleware