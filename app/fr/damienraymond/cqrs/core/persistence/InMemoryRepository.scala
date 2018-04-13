package fr.damienraymond.cqrs.core.persistence

import fr.damienraymond.cqrs.core.entity.AggregateRoot

// TODO to actor
trait InMemoryRepository[T_ID, T_ROOT <: AggregateRoot[T_ID]] extends Repository[T_ID, T_ROOT] {

  private var elements = Map.empty[T_ID, T_ROOT]

  override def get(id: T_ID): Option[T_ROOT] = elements.get(id)

  override def add(root: T_ROOT): Unit = {
    elements += (root.id -> root)
  }

  override def delete(root: T_ROOT): Unit = {
    elements = elements.filterNot(_._1 == root.id)
  }

  override def getAll: List[T_ROOT] = elements.values.toList

  override def save(root: T_ROOT): Unit = {
    elements = elements.updated(root.id, root)
  }
}
