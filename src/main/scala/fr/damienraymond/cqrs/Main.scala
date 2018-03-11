package fr.damienraymond.cqrs

import java.util.UUID

import com.google.inject.Guice
import fr.damienraymond.cqrs.core.infrastructure.bus.CommandBus
import fr.damienraymond.cqrs.example.commands.{BuyProducts, PutArticleOnSale, PutArticleOnSaleHandler}
import fr.damienraymond.cqrs.example.infrastructure.persistence.StoreRepository
import fr.damienraymond.cqrs.example.web.WebServer

object Main {

  def main(args: Array[String]): Unit = {
    val injector = Guice.createInjector()
    injector.getInstance(classOf[WebServer]).run()

    injector.getInstance(classOf[Tests])

  }

}
