package fr.damienraymond.cqrs.core.module

import java.util.UUID

import fr.damienraymond.cqrs.core.{Command, CommandHandler, Query, QueryHandler}
import fr.damienraymond.cqrs.core.event.{Event, EventCaptor}
import fr.damienraymond.cqrs.core.persistence.InMemoryRepository
import fr.damienraymond.cqrs.example.model.product.ProductRepository
import net.codingwell.scalaguice.{ScalaModule, ScalaMultibinder}
import fr.damienraymond.cqrs.example.model.product.Product
import fr.damienraymond.cqrs.example.model.seller.{Seller, SellerRepository}

class FakeModule extends ScalaModule {
  override def configure(): Unit = {
    bind[ProductRepository].toInstance(new InMemoryRepository[UUID, Product] with ProductRepository)
    bind[SellerRepository].toInstance(new InMemoryRepository[UUID, Seller] with SellerRepository)

    provideEventCaptors()
    provideCommandHandlers()
    provideQueryHandlers()
  }


  def provideEventCaptors(): Unit = {
    println("Binding EventCaptors...")
    ScalaMultibinder.newSetBinder[EventCaptor[Event[_]]](binder)
  }

  def provideCommandHandlers(): Unit = {
    println("Binding CommandHandlers...")
    ScalaMultibinder.newSetBinder[CommandHandler[Command[_], _]](binder)
  }

  def provideQueryHandlers(): Unit = {
    println("Binding QueryHandlers...")
    ScalaMultibinder.newSetBinder[QueryHandler[Query[_], _]](binder)
  }
}
