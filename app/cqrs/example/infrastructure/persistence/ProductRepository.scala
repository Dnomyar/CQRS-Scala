package fr.damienraymond.cqrs.example.infrastructure.persistence

import java.util.UUID

import fr.damienraymond.cqrs.core.persistence.InMemoryRepository
import fr.damienraymond.cqrs.example.model.product.{Price, Product, ProductStock}

class ProductRepository extends InMemoryRepository[UUID, Product] {

  this.add(Product(
    ProductRepository.productId1,
    "Pasta",
    Price(1.99),
    StoreRepository.storeId,
    ProductStock(ProductRepository.productId1, 12)
  ))

}

object ProductRepository {
  val productId1 = UUID.randomUUID()
}







