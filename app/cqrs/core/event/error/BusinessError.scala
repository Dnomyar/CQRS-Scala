package fr.damienraymond.cqrs.core.event.error

import fr.damienraymond.cqrs.core.event.Event

trait BusinessError[T] extends Event[T]
