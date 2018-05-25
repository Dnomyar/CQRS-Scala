package fr.damienraymond.cqrs.example.infrastructure.persistence

import java.util.UUID

import com.google.inject.{Inject, Singleton}
import fr.damienraymond.cqrs.core.persistence.MongoRepository
import fr.damienraymond.cqrs.example.model.seller.{Seller, SellerRepository}
import play.modules.reactivemongo.ReactiveMongoApi

import scala.concurrent.ExecutionContext

@Singleton
class SellerRepositoryImplementation @Inject()(reactiveMongoApi: ReactiveMongoApi)(implicit ec: ExecutionContext) extends MongoRepository[UUID, Seller](reactiveMongoApi) with SellerRepository {
  override protected def collectionName: String = "sellers"
}

