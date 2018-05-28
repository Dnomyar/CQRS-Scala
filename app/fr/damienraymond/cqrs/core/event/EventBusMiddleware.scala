package fr.damienraymond.cqrs.core.event

import fr.damienraymond.cqrs.core.middleware.CommandMiddleware

trait EventBusMiddleware extends CommandMiddleware