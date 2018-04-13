package fr.damienraymond.cqrs.example.infrastructure.persistence

import java.util.UUID

import fr.damienraymond.cqrs.core.persistence.InMemoryRepository
import fr.damienraymond.cqrs.example.model.product.{Price, Product, ProductStock}
import com.google.inject.{Inject, Singleton}

@Singleton
class ProductRepository extends InMemoryRepository[UUID, Product]






