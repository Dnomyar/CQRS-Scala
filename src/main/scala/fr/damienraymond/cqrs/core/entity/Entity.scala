package fr.damienraymond.cqrs.core.entity

trait Entity[T_ID] {

  def id: T_ID

}
