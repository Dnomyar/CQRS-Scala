package fr.damienraymond.cqrs.core.persistence

import fr.damienraymond.cqrs.core.entity.AggregateRoot

import scala.concurrent.Future

// TODO to actor
trait InMemoryRepository[T_ID, T_ROOT <: AggregateRoot[T_ID]] extends Repository[T_ID, T_ROOT] {

  private var elements = Map.empty[T_ID, T_ROOT]

  override def get(id: T_ID): Future[Option[T_ROOT]] = Future.successful(elements.get(id))

  override def add(root: T_ROOT): Future[Unit] = {
    elements += (root.id -> root)
    Future.successful(())
  }

  override def delete(id: T_ID): Future[Unit] = {
    elements = elements.filterNot(_._1 == id)
    Future.successful(())
  }

  override def getAll: Future[List[T_ROOT]] = Future.successful(elements.values.toList)

  override def save(root: T_ROOT): Future[Unit] = {
    elements = elements.updated(root.id, root)
    Future.successful(())
  }
}
