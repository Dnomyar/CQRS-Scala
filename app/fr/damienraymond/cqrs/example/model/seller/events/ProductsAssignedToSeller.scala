package fr.damienraymond.cqrs.example.model.seller.events

import fr.damienraymond.cqrs.core.event.Event
import fr.damienraymond.cqrs.example.model.seller.Seller

case class ProductsAssignedToSeller(seller: Seller) extends Event[Seller]



