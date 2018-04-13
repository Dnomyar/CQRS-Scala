package fr.damienraymond.cqrs.example.module

import net.codingwell.scalaguice.ScalaModule

class ExampleModule extends ScalaModule {
  override def configure(): Unit = {
    bind[ApplicationStarts].asEagerSingleton()
  }
}
