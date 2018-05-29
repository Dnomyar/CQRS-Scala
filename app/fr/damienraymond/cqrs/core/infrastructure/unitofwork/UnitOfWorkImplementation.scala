package fr.damienraymond.cqrs.core.infrastructure.unitofwork

import fr.damienraymond.cqrs.core.Logger
import fr.damienraymond.cqrs.core.persistence.UnitOfWork

import scala.concurrent.Future

class UnitOfWorkImplementation extends UnitOfWork with Logger {
  override def registerNew[T](obj: T): Unit = {
    logger.trace("[UOW] registerNew")
  }

  override def registerDirty[T](obj: T): Unit = {
    logger.trace("[UOW] registerDirty")
  }

  override def registerClean[T](obj: T): Unit = {
    logger.trace("[UOW] registerClean")
  }

  override def registerDeleted[T](obj: T): Unit = {
    logger.trace("[UOW] registerDeleted")
  }

  override def commit: Future[Unit] = {
    logger.trace("[UOW] commit")
    Future.successful(())
  }
}
