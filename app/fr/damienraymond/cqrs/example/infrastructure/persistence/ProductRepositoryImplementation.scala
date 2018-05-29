package fr.damienraymond.cqrs.example.infrastructure.persistence

import java.util.UUID

import fr.damienraymond.cqrs.example.model.product.{Price, Product, ProductRepository, ProductStock}
import com.google.inject.{Inject, Singleton}
import fr.damienraymond.cqrs.core.infrastructure.persistence.MongoRepository
import play.modules.reactivemongo.ReactiveMongoApi

import scala.concurrent.ExecutionContext

@Singleton
class ProductRepositoryImplementation @Inject()(reactiveMongoApi: ReactiveMongoApi)(implicit ec: ExecutionContext) extends MongoRepository[UUID, Product](reactiveMongoApi) with ProductRepository {
  override protected def collectionName: String = "products"
}






