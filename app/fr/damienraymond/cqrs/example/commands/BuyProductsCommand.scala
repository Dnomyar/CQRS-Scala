package fr.damienraymond.cqrs.example.commands

import java.util.UUID

import javax.inject.Inject
import fr.damienraymond.cqrs.core.event.Event
import fr.damienraymond.cqrs.core.persistence.UnitOfWork
import fr.damienraymond.cqrs.core.{Command, CommandHandler, Logger}
import fr.damienraymond.cqrs.example.infrastructure.persistence.ProductRepository
import fr.damienraymond.cqrs.example.model.product.events.ProductBought
import fr.damienraymond.cqrs.example.model.product.Product
import fr.damienraymond.cqrs.example.model.product.events.errors.ProductNotFound
import fr.damienraymond.cqrs.example.model.store.{Store, StoreNotFound}
import play.api.libs.json.Json

case class BuyProductsCommand(productId: UUID, numberOfProduct: Long = 1) extends Command[Unit]


object BuyProductsCommand { implicit val format = Json.format[BuyProductsCommand] }


class BuyProductsCommandHandler @Inject()(productRepository: ProductRepository) extends CommandHandler[BuyProductsCommand, Unit] with Logger {
  override def handle(message: BuyProductsCommand): (Unit, List[Event[_]]) = {

    logger.info("Calling BuyProductsHandler")

    product(message) match {
      case None => (Unit, List(ProductNotFound(message.productId)))
      case Some(product) =>

        product.buy(message.numberOfProduct) match {
          case Left(error) => (Unit, List(error))
          case Right(None) => (Unit, List.empty)
          case Right(Some(newProduct)) =>
            productRepository.save(newProduct)
            (Unit, List(ProductBought(newProduct, message.numberOfProduct)))
        }


    }
  }

  def product(command: BuyProductsCommand): Option[Product] = productRepository.get(command.productId)
}

