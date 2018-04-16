package fr.damienraymond.cqrs.core.persistence

import fr.damienraymond.cqrs.core.entity.AggregateRoot

import scala.concurrent.Future

trait Repository[T_ID, T_ROOT <: AggregateRoot[T_ID]] {

  def get(id: T_ID): Future[Option[T_ROOT]]

  def add(root: T_ROOT): Future[Unit]

  def delete(id: T_ID): Future[Unit]

  def getAll: Future[List[T_ROOT]]

  def save(root: T_ROOT): Future[Unit]

}
