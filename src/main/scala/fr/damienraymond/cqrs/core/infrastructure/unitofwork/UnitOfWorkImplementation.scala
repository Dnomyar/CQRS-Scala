package fr.damienraymond.cqrs.core.infrastructure.unitofwork

import fr.damienraymond.cqrs.core.persistence.UnitOfWork

import scala.concurrent.Future

class UnitOfWorkImplementation extends UnitOfWork {
  override def registerNew[T](obj: T): Unit = {
    println("[UOW] registerNew")
  }

  override def registerDirty[T](obj: T): Unit = {
    println("[UOW] registerDirty")
  }

  override def registerClean[T](obj: T): Unit = {
    println("[UOW] registerClean")
  }

  override def registerDeleted[T](obj: T): Unit = {
    println("[UOW] registerDeleted")
  }

  override def commit: Future[Unit] = {
    Future.successful(println("[UOW] commit"))
  }
}
