package fr.damienraymond.cqrs.example.model.buyer

import java.util.UUID

import fr.damienraymond.cqrs.core.entity.UUIDEntity

case class Buyer(id: UUID) extends UUIDEntity
