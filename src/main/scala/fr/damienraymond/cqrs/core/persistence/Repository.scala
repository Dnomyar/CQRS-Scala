package fr.damienraymond.cqrs.core.persistence

import fr.damienraymond.cqrs.core.entity.AggregateRoot

trait Repository[T_ID, T_ROOT <: AggregateRoot[T_ID]] {

  def get(id: T_ID): Option[T_ROOT]

  def add(root: T_ROOT): Unit

  def delete(root: T_ROOT): Unit

  def getAll: List[T_ROOT]

  def save(root: T_ROOT): Unit

}
