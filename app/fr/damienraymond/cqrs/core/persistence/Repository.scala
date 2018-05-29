package fr.damienraymond.cqrs.core.persistence

import fr.damienraymond.cqrs.core.entity.AggregateRoot

import scala.concurrent.Future

trait Repository[T_ID, T_ROOT <: AggregateRoot[T_ID]] {

  def getAll: Future[List[T_ROOT]]

  def getAllPaginated(page: Int, perPage: Long): Future[List[T_ROOT]] = getAll

  def get(id: T_ID): Future[Option[T_ROOT]]

  def add(root: T_ROOT): Future[Unit]

  def delete(id: T_ID): Future[Unit]

  def save(root: T_ROOT): Future[Unit]

}
