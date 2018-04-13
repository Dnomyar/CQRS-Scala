package fr.damienraymond.cqrs.example.model.product.events

import fr.damienraymond.cqrs.core.event.Event

case class ProductAdded(product: Product) extends Event[Product]
