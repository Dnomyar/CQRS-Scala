package fr.damienraymond.cqrs.core.module

import com.google.inject.Provides
import com.google.inject.multibindings.Multibinder
import fr.damienraymond.cqrs.core._
import fr.damienraymond.cqrs.core.event.{Event, SynchronizedEventBus, EventCaptor}
import fr.damienraymond.cqrs.core.infrastructure.event.SynchronizedEventBusImplementation
import fr.damienraymond.cqrs.core.infrastructure.unitofwork.UnitOfWorkImplementation
import fr.damienraymond.cqrs.core.persistence.UnitOfWork
import net.codingwell.scalaguice.{ScalaModule, ScalaMultibinder}



class CoreModule extends ScalaModule {

  override def configure() = {
    bind[SynchronizedEventBus].to[SynchronizedEventBusImplementation]
    bind[UnitOfWork].to[UnitOfWorkImplementation]
  }

}
