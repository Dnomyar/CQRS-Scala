package fr.damienraymond.cqrs.example.infrastructure.persistence

import java.util.UUID

import fr.damienraymond.cqrs.core.persistence.MongoRepository
import fr.damienraymond.cqrs.example.model.product.{Price, Product, ProductRepository, ProductStock}
import com.google.inject.{Inject, Singleton}
import play.modules.reactivemongo.ReactiveMongoApi

import scala.concurrent.ExecutionContext

@Singleton
class ProductRepositoryImplementation @Inject()(reactiveMongoApi: ReactiveMongoApi)(implicit ec: ExecutionContext) extends MongoRepository[UUID, Product](reactiveMongoApi) with ProductRepository {
  override protected def collectionName: String = "products"
}






