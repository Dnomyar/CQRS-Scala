package fr.damienraymond.cqrs.core.module

import com.google.inject.Provides
import com.google.inject.multibindings.Multibinder
import fr.damienraymond.cqrs.core._
import fr.damienraymond.cqrs.core.event.{Event, EventBus, EventBusMiddleware, EventCaptor}
import fr.damienraymond.cqrs.core.infrastructure.bus.{CommandBusImplementation, QueryBusImplementation}
import fr.damienraymond.cqrs.core.infrastructure.event.{EventBusImplementation, EventBusMiddlewareImplementation}
import fr.damienraymond.cqrs.core.infrastructure.unitofwork.UnitOfWorkImplementation
import fr.damienraymond.cqrs.core.middleware.{CommandMiddleware, QueryMiddleware}
import fr.damienraymond.cqrs.core.persistence.UnitOfWork
import net.codingwell.scalaguice.{ScalaModule, ScalaMultibinder}



class CoreModule extends ScalaModule {

  override def configure() = {
    bind[EventBus].to[EventBusImplementation]
    bind[UnitOfWork].to[UnitOfWorkImplementation]
    bind[QueryBus].to[QueryBusImplementation]
    bind[CommandBus].to[CommandBusImplementation]
    bind[EventBusMiddleware].to[EventBusMiddlewareImplementation]
  }


  @Provides
  def provideCommandMiddlewares(eventBusMiddleware: EventBusMiddleware): List[CommandMiddleware] = List(eventBusMiddleware)


  @Provides
  def provideQueryMiddlewares: List[QueryMiddleware] = List()

}
