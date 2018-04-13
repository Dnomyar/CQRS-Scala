package fr.damienraymond.cqrs.example.model.product.events

import java.util.UUID

import fr.damienraymond.cqrs.core.event.Event
import fr.damienraymond.cqrs.example.model.product.Product
import fr.damienraymond.cqrs.example.model.store.Store

case class ProductBought(product: Product, numberOfUnits: Long) extends Event[Store]