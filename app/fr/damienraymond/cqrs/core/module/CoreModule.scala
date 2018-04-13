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

    provideEventCaptors()
    provideCommandHandlers()
    provideQueryHandlers()
  }

  def provideEventCaptors(): Unit = {
    println("Binding EventCaptors...")
    val multibinder = ScalaMultibinder.newSetBinder[EventCaptor[Event[_]]](binder)
    ClassScanner.scan("fr.damienraymond.cqrs", classOf[EventCaptor[Event[_]]]){ implClass =>
      println(s"Binding $implClass")
      multibinder.addBinding.to(implClass)
    }
  }

  def provideCommandHandlers(): Unit = {
    println("Binding CommandHandlers...")
    val multibinder = ScalaMultibinder.newSetBinder[CommandHandler[Command[_], _]](binder)
    ClassScanner.scan("fr.damienraymond.cqrs", classOf[CommandHandler[Command[_], _]]){ implClass =>
      println(s"Binding $implClass")
      multibinder.addBinding.to(implClass)
    }
  }

  def provideQueryHandlers(): Unit = {
    println("Binding QueryHandlers...")
    val multibinder = ScalaMultibinder.newSetBinder[QueryHandler[Query[_], _]](binder)
    ClassScanner.scan("fr.damienraymond.cqrs", classOf[QueryHandler[Query[_], _]]){ implClass =>
      println(s"Binding $implClass")
      multibinder.addBinding.to(implClass)
    }
  }
}
