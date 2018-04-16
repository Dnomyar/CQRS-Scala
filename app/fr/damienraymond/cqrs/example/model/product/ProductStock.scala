package fr.damienraymond.cqrs.example.model.product

import java.util.UUID

import fr.damienraymond.cqrs.core.entity.UUIDEntity
import play.api.libs.json.Json

case class ProductStock(product: UUID, numberOfAvailableProducts: Long) extends UUIDEntity {

  override def id = product

  def canBuyProducts(numberOfItems: Long): Boolean = numberOfAvailableProducts - numberOfItems >= 0

  def buy(numberOfItems: Long): ProductStock =
    copy(numberOfAvailableProducts = numberOfAvailableProducts - numberOfItems)

  def addNewProducts(numberOfProductsToAdd: Long): ProductStock =
    copy(numberOfAvailableProducts = numberOfAvailableProducts + numberOfProductsToAdd)

}



object ProductStock {
  implicit val format = Json.format[ProductStock]
}
