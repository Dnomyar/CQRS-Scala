package fr.damienraymond.cqrs.example.model.product.events

import fr.damienraymond.cqrs.core.event.Event

case class ProductStockAdded(product: Product, numberOfProductAdded: Long) extends Event[Product]
