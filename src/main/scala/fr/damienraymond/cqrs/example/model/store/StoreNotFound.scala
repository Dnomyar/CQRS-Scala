package fr.damienraymond.cqrs.example.model.store

import java.util.UUID

import fr.damienraymond.cqrs.core.event.error.BusinessError

case class StoreNotFound(storeId: UUID) extends BusinessError[Store]
