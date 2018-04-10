package fr.damienraymond.cqrs.core.event

import fr.damienraymond.cqrs.core.Message

trait Event[T] extends Message[T]
