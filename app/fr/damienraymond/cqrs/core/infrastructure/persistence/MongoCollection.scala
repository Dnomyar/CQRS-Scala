package fr.damienraymond.cqrs.core.infrastructure.persistence

import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.ExecutionContext

trait MongoCollection {

  implicit val ec: ExecutionContext

  protected val reactiveMongoApi: ReactiveMongoApi
  protected val mongoCollectionName: String

  protected val collection = reactiveMongoApi.database.map(_.collection[JSONCollection](mongoCollectionName))
}
