package fr.damienraymond.cqrs.example.model.product

import java.util.UUID

import fr.damienraymond.cqrs.core.entity.UUIDAggregateRoot
import fr.damienraymond.cqrs.core.event.Event
import fr.damienraymond.cqrs.core.event.error.BusinessError
import fr.damienraymond.cqrs.example.model.product.events.errors.{CantBuyZeroProduct, ProductNotAvailable}
import play.api.libs.json.Json


case class Product(id: UUID,
                   name: String,
                   price: Price,
                   stock: ProductStock) extends UUIDAggregateRoot {

  def putOnSaleBy(percentage: Percentage): Product =
    Product(id, name, price.decreaseBy(percentage), stock)

  def buy(numberOfItems: Long = 1): Either[BusinessError[Product], Option[Product]] =
    if(numberOfItems < 1) Left(CantBuyZeroProduct(this))
    else if(stock.canBuyProducts(numberOfItems))
      Right(Some(copy(stock = stock.buy(numberOfItems))))
    else Left(ProductNotAvailable(this, stock.numberOfAvailableProducts, numberOfItems))

  def addProductsInStock(numberOfProductToAdd: Long): Product =
    copy(stock = stock.addNewProducts(numberOfProductToAdd))


}


object Product {
  implicit val format = Json.format[Product]
}



