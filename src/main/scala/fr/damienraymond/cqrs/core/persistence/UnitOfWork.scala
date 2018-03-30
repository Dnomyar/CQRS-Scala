package fr.damienraymond.cqrs.core.persistence

import com.google.inject.{Inject, Provider}

import scala.concurrent.Future

trait UnitOfWork {

  def registerNew[T](obj: T)
  def registerDirty[T](obj: T)
  def registerClean[T](obj: T)
  def registerDeleted[T](obj: T)

  def commit: Future[Unit]
}


class UnitOfWorkFactory @Inject()(uowProvider: Provider[UnitOfWork]) {
  def create: UnitOfWork = uowProvider.get()
}