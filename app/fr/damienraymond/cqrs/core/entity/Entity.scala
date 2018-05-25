package fr.damienraymond.cqrs.core.entity

import java.util.Objects

trait Entity[T_ID] {

  def id: T_ID

  override def hashCode(): Int = Objects.hashCode(id)

  override def equals(obj: scala.Any): Boolean = {
    if (obj == null || (getClass != obj.getClass)) return false
    Objects.equals(obj.asInstanceOf[Entity[_]].id, id)
  }


}
