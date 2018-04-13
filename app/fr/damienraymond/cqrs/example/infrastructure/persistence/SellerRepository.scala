package fr.damienraymond.cqrs.example.infrastructure.persistence

import java.util.UUID

import com.google.inject.{Inject, Singleton}

import fr.damienraymond.cqrs.core.persistence.InMemoryRepository
import fr.damienraymond.cqrs.example.model.seller.Seller

@Singleton
class SellerRepository extends InMemoryRepository[UUID, Seller]

