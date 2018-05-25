package fr.damienraymond.cqrs.core.module

import fr.damienraymond.cqrs.core.{Command, CommandHandler, Query, QueryHandler}
import fr.damienraymond.cqrs.core.event.{Event, EventCaptor}
import net.codingwell.scalaguice.{ScalaModule, ScalaMultibinder}

class ClassScannerModule extends ScalaModule {
  override def configure(): Unit = {
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
