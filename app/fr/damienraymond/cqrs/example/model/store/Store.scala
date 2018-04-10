package fr.damienraymond.cqrs.example.model.store

import java.util.UUID

import fr.damienraymond.cqrs.core.entity.UUIDAggregateRoot

case class Store(id: UUID) extends UUIDAggregateRoot {

//  def buyProduct(register: RegisterRow, numberOfArticleToBuy: Long): (List[Event[_]], Option[RegisterRow]) =
//    register.removeProduct(numberOfArticleToBuy) match {
//      case Left(productNotAvailable) => (List(productNotAvailable), None)
//      case Right(registerUpdated) =>
//        val productBought = ProductBought(id, register.productId, numberOfArticleToBuy)
//        (List(productBought), Some(registerUpdated))
//    }

}
