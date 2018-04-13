package fr.damienraymond.cqrs.example.model.seller.events.errors

import java.util.UUID

import fr.damienraymond.cqrs.core.event.error.BusinessError

case class SomeProductsWerentFound(sellerName: String, requestedProductToBeAssigned: Set[UUID], notFoundProducts: Set[UUID]) extends BusinessError[Product]
