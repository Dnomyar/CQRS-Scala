package fr.damienraymond.cqrs.core.persistence

import fr.damienraymond.cqrs.core.entity.AggregateRoot

import scala.concurrent.Future

class FakeRepository extends Repository[Any, AggregateRoot[Any]] {

  override def getAll: Future[List[AggregateRoot[Any]]] = ???

  override def get(id: Any): Future[Option[AggregateRoot[Any]]] = ???

  override def add(root: AggregateRoot[Any]): Future[Unit] = ???

  override def delete(id: Any): Future[Unit] = ???

  override def save(root: AggregateRoot[Any]): Future[Unit] = ???

}
