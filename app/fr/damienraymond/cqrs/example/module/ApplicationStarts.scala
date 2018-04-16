package fr.damienraymond.cqrs.example.module

import com.google.inject.{Inject, Singleton}
import fr.damienraymond.cqrs.core.Logger
import fr.damienraymond.cqrs.core.infrastructure.bus.CommandBus
import fr.damienraymond.cqrs.example.commands.{AddProductCommand, AssignProductsToSellerCommand}
import fr.damienraymond.cqrs.example.infrastructure.persistence.ProductRepository
import fr.damienraymond.cqrs.example.model.product.{Price, Product, ProductStock}

import scala.concurrent.ExecutionContext


@Singleton
class ApplicationStarts @Inject()(commandBus: CommandBus)(implicit ec: ExecutionContext) extends Logger {


  (for {
    (pastaProductId, _) <- commandBus.dispatch(AddProductCommand(
      "Pasta",
      1.99,
      12
    ))

    (watchProductId, _) <- commandBus.dispatch(AddProductCommand(
      "Watch",
      1099,
      3
    ))


    _ <- commandBus.dispatch(AssignProductsToSellerCommand("Amazon", Set(pastaProductId, watchProductId)))

  } yield {
    println("Some products are loaded")
    ()
  }).recover{
    case e: Exception =>
      logger.error(e.toString)
      e.printStackTrace()
  }







}
