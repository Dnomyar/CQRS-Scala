package fr.damienraymond.cqrs.example.module

import fr.damienraymond.cqrs.example.infrastructure.persistence.{ProductRepository, SellerRepository}
import net.codingwell.scalaguice.ScalaModule

class ExampleModule extends ScalaModule {
  override def configure(): Unit = {
    bind[ApplicationStarts].asEagerSingleton()
    bind[ProductRepository].asEagerSingleton()
    bind[SellerRepository].asEagerSingleton()
  }
}
