package fr.damienraymond.cqrs.example.commands

import java.util.UUID
import javax.inject.Inject

import fr.damienraymond.cqrs.core.event.Event
import fr.damienraymond.cqrs.core.persistence.UnitOfWork
import fr.damienraymond.cqrs.core.{Command, CommandHandler, Handler, Logger}
import fr.damienraymond.cqrs.example.infrastructure.persistence.{ProductRepository, StoreRepository}
import fr.damienraymond.cqrs.example.model.product.events.ProductBought
import fr.damienraymond.cqrs.example.model.product.Product
import fr.damienraymond.cqrs.example.model.product.events.errors.ProductNotFound
import fr.damienraymond.cqrs.example.model.store.{Store, StoreNotFound}

case class BuyProducts(storeId: UUID, productId: UUID, numberOfProduct: Long = 1) extends Command[Unit]


class BuyProductsHandler @Inject() (productRepository: ProductRepository) extends CommandHandler[BuyProducts, Unit] with Logger {
  override def handle(message: BuyProducts): (Unit, List[Event[_]]) = {

    logger.info("Calling BuyProductsHandler")

    product(message) match {
      case None => (Unit, List(ProductNotFound(message.storeId, message.productId)))
      case Some(product) =>

        product.buy(message.numberOfProduct) match {
          case Left(error) => (Unit, List(error))
          case Right(None) => (Unit, List.empty)
          case Right(Some(newProduct)) =>
            //uow.registerNew(newProduct)
            (Unit, List(ProductBought(message.storeId, product, message.numberOfProduct)))
        }


    }
  }

  def product(command: BuyProducts): Option[Product] = productRepository.get(command.productId)
}

