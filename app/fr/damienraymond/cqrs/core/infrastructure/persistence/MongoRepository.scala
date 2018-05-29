package fr.damienraymond.cqrs.core.infrastructure.persistence

import fr.damienraymond.cqrs.core.Logger
import fr.damienraymond.cqrs.core.entity.AggregateRoot
import fr.damienraymond.cqrs.core.persistence.Repository
import play.api.libs.json._
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.Cursor
import reactivemongo.api.indexes.{Index, IndexType}
import reactivemongo.play.json._
import reactivemongo.play.json.collection.{JSONCollection, _}

import scala.concurrent.{ExecutionContext, Future}

abstract class MongoRepository[T_ID, T_ROOT <: AggregateRoot[T_ID]](reactiveMongoApi: ReactiveMongoApi)(implicit ec: ExecutionContext, oformat: OFormat[T_ROOT]) extends Repository[T_ID, T_ROOT] with Logger {

  protected def collectionName: String

  private val collection = reactiveMongoApi.database.map(_.collection[JSONCollection](collectionName))

  createCollection.map(_ => createIdIndex)

  override def get(id: T_ID): Future[Option[T_ROOT]] =
    collection.flatMap(_.find(Json.obj("id" -> id.toString)).one[T_ROOT])

  override def add(root: T_ROOT): Future[Unit] =
    collection.flatMap(_.insert(Json.toJsObject(root))).map(_ => ())

  override def delete(id: T_ID): Future[Unit] =
    collection.flatMap(_.remove(Json.obj("id" -> id.toString), firstMatchOnly = true))
      .map(_ => ())

  override def getAll: Future[List[T_ROOT]] =
    collection.flatMap(_.find(Json.obj()).cursor[T_ROOT]().collect[List](Int.MaxValue, Cursor.FailOnError[List[T_ROOT]]()))

//
//  override def getAllPaginated(page: Int, perPage: Long): Future[List[T_ROOT]] = {
//    val queryOptions = new QueryOpts(skipN = skipN, batchSizeN = pageSize, flagsN = 0)
//
//
//  }
//    collection.flatMap(_.find(Json.obj()).cursor[T_ROOT](ReadPreference.primaryPreferred).collect[List](perPage, Cursor.FailOnError[List[T_ROOT]]()))
//
//      ().collect[List](Int.MaxValue, Cursor.FailOnError[List[T_ROOT]]()))
//
//      .cursor[ConvoDesc](ReadPreference.primaryPreferred).collect[List](pageSize)

  override def save(root: T_ROOT): Future[Unit] =
    collection.flatMap(_.update(
      Json.obj("id" -> root.id.toString),
      Json.toJsObject(root),
      upsert = true
    )).map(_ => ())
    .recover{
      case e: Exception => logger.error(e.toString)
    }




  private def createCollection: Future[Unit] = {
    logger.info(s"Creating collection `$collectionName` to be able to add index...")
    collection.map(_.create())
      .map(_ => logger.info(s"Collection `$collectionName` created !"))
      .recover{
        case e: Exception =>
          logger.error(s"An error occurred on creating collection `$collectionName`! Error : $e")
      }
  }

  /**
    * Adding index on field id for efficient retrival of data
    */
  private def createIdIndex: Future[Unit] = {
    logger.info(s"Indexing column `id` on collection `$collectionName`...")
    collection.flatMap(_.indexesManager.ensure(Index(Seq("id" -> IndexType.Ascending), unique = true, background = true)))
      .map{
        case true =>  logger.info(s"Index on column `id` on collection `$collectionName` created !")
        case false => logger.info(s"Index on column `id` on collection `$collectionName` already exists !")
      }.recover{
      case e: Exception =>
        logger.error(s"An error occurred on indexing column `id` on collection `$collectionName`! Error : $e")
    }
  }

}
