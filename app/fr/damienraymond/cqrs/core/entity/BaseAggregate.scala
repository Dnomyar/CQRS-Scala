package fr.damienraymond.cqrs.core.entity

trait BaseAggregate[T_ID] extends BaseEntity[T_ID] with AggregateRoot[T_ID]
