package fr.damienraymond.cqrs.example.model.product

import java.util.UUID

import fr.damienraymond.cqrs.core.entity.UUIDAggregateRoot
import fr.damienraymond.cqrs.example.model.product.events.errors.CantBuyZeroProduct


case class Product(id: UUID,
                   name: String,
                   price: Price,
                   storeId: UUID,
                   stock: ProductStock) extends UUIDAggregateRoot {

  def putOnSaleBy(percentage: Percentage): Product =
    Product(id, name, price.decreaseBy(percentage), storeId, stock)

  def buy(numberOfItems: Long = 1): Either[CantBuyZeroProduct, Option[Product]] =
    if(numberOfItems > 1) Left(CantBuyZeroProduct(this))
    else if(stock.canBuyProducts(numberOfItems))
      Right(Some(copy(stock = stock.buy(numberOfItems))))
    else Right(None)

}



