package fr.damienraymond.cqrs.example.model.seller

import java.util.UUID

import fr.damienraymond.cqrs.core.entity.UUIDAggregateRoot


case class Seller(id: UUID,
                  name: String,
                  products: Set[UUID]) extends UUIDAggregateRoot
