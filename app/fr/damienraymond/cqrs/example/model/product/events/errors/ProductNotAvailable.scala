package fr.damienraymond.cqrs.example.model.product.events.errors

import java.util.UUID

import fr.damienraymond.cqrs.core.event.error.BusinessError
import fr.damienraymond.cqrs.example.model.product.Product

case class ProductNotAvailable(product: Product, numberOfProductAvailable: Long, requestedNumberOfProductToBuy: Long) extends BusinessError[Product]
