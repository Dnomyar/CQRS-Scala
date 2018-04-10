package fr.damienraymond.cqrs.example.model.product.events.errors

import fr.damienraymond.cqrs.core.event.error.BusinessError
import fr.damienraymond.cqrs.example.model.product.Product

case class CantBuyZeroProduct(product: Product) extends BusinessError[Product]