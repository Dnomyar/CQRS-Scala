package fr.damienraymond.cqrs.example.model.seller

import java.util.UUID

import fr.damienraymond.cqrs.core.entity.UUIDAggregateRoot
import play.api.libs.json.Json


case class Seller(id: UUID,
                  name: String,
                  products: Set[UUID]) extends UUIDAggregateRoot



object Seller {
  implicit val format = Json.format[Seller]
}
