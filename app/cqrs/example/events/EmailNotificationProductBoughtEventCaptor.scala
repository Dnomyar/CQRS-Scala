package fr.damienraymond.cqrs.example.events

import fr.damienraymond.cqrs.core.event.EventCaptor
import fr.damienraymond.cqrs.example.model.product.events.ProductBought

import scala.concurrent.Future

class EmailNotificationProductBoughtEventCaptor extends EventCaptor[ProductBought] {
  override def execute(event: ProductBought) = {
    Future.successful(println(s"[NOTIFICATION][EMAIL] You just bought ${event.numberOfUnits} product named ${event.product.name}"))
  }
}
