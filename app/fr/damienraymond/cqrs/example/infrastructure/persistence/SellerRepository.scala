package fr.damienraymond.cqrs.example.infrastructure.persistence

import java.util.UUID

import fr.damienraymond.cqrs.core.persistence.InMemoryRepository
import fr.damienraymond.cqrs.example.model.seller.Seller

class SellerRepository extends InMemoryRepository[UUID, Seller] {

  this.add(Seller(
    UUID.randomUUID(),
    "Amazon",
    Set(ProductRepository.productId1, ProductRepository.productId2)
  ))

}
