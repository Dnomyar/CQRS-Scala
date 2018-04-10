package fr.damienraymond.cqrs.example.infrastructure.persistence

import java.util.UUID

import fr.damienraymond.cqrs.core.persistence.InMemoryRepository
import fr.damienraymond.cqrs.example.model.store.Store

object StoreRepository {
  val storeId = UUID.randomUUID()
  val store = Store(storeId)
}

