package fr.damienraymond.cqrs.core.entity

import java.util.Objects

trait BaseEntity[T_ID] extends Entity[T_ID] {

  override def hashCode(): Int = Objects.hashCode(id)

  override def equals(obj: scala.Any): Boolean = {
    if (this == obj) return true
    if (obj == null || (getClass != obj.getClass)) return false
    Objects.equals(obj.asInstanceOf[BaseEntity[_]].id, id)
  }

}
