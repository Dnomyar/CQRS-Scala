package fr.damienraymond.cqrs.example.model

import java.util.UUID

import fr.damienraymond.cqrs.example.model.product.events.errors.{CantBuyZeroProduct, ProductNotAvailable}
import fr.damienraymond.cqrs.example.model.product.{Percentage, Price, Product, ProductStock}
import org.scalatest.{FlatSpec, Matchers}
import org.scalactic.TolerantNumerics

class ProductSpec extends FlatSpec with Matchers {

  val epsilon = 1e-5f

  implicit val floatEq = TolerantNumerics.tolerantDoubleEquality(epsilon)

  "It" should "be possible to add product to a stock" in {
    val p = Product(
      UUID.randomUUID(),
      "pasta",
      Price(1),
      ProductStock(10)
    )

    val pUpdated = p.addProductsInStock(13)

    pUpdated.stock.numberOfAvailableProducts should be (10 + 13)
  }


  "It" should "be possible to put a product on sale" in {
    val p = Product(
      UUID.randomUUID(),
      "pasta",
      Price(100),
      ProductStock(10)
    )

    val pUpdated = p.putOnSaleBy(Percentage(0.2f))

    assert(pUpdated.price.value === 80d)
  }


  "It" should "not be possible to buy product less than 1 product " in {
    val p = Product(
      UUID.randomUUID(),
      "pasta",
      Price(100),
      ProductStock(10)
    )

    val res = p.buy(-10)

    res should be (Left(CantBuyZeroProduct(p)))
  }

  "It" should "be possible to buy product if there is enough product in stock" in {
    val p = Product(
      UUID.randomUUID(),
      "pasta",
      Price(100),
      ProductStock(10)
    )

    val res = p.buy(10)

    res should be (Right(Some(Product(
      p.id,
      p.name,
      p.price,
      ProductStock(0)
    ))))
  }

  "It" should "not be possible to buy product if there is not enough product in stock" in {
    val p = Product(
      UUID.randomUUID(),
      "pasta",
      Price(100),
      ProductStock(10)
    )

    val res = p.buy(100)

    res should be (Left(ProductNotAvailable(p, 10, 100)))
  }

}
