package fr.damienraymond.cqrs.core.persistence

import java.util.UUID

import fr.damienraymond.cqrs.core.entity.AggregateRoot

trait UUIDRepository[T_ROOT <: AggregateRoot[UUID]] extends Repository[UUID, T_ROOT]