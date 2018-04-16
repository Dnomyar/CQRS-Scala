package fr.damienraymond.cqrs.example.commands

import java.util.UUID

import com.google.inject.Inject
import fr.damienraymond.cqrs.core.event.Event
import fr.damienraymond.cqrs.core.{Command, CommandHandler, Logger}
import fr.damienraymond.cqrs.example.infrastructure.persistence.ProductRepository
import fr.damienraymond.cqrs.example.model.product.{Price, Product, ProductStock}
import fr.damienraymond.cqrs.example.model.product.events.{ProductAdded, ProductStockAdded}

import scala.concurrent.{ExecutionContext, Future}

case class AddProductCommand(productName: String, price: Double, numberOfProductToAdd: Long) extends Command[UUID]


class AddProductCommandHandler @Inject()(productRepository: ProductRepository)(implicit ec: ExecutionContext) extends CommandHandler[AddProductCommand, UUID] with Logger {

  override def handle(cmd: AddProductCommand): Future[(UUID, List[Event[_]])] = {
    productRepository.getAll.map(_.find(_.name == cmd.productName)).flatMap {
      case Some(product) =>
        val productUpdated = product.addProductsInStock(cmd.numberOfProductToAdd)
        productRepository.save(productUpdated)

        Future.successful((productUpdated.id, List(ProductStockAdded(productUpdated, cmd.numberOfProductToAdd))))

      case None =>
        val productId = UUID.randomUUID()
        val newProduct = Product(
          productId,
          name = cmd.productName,
          price = Price(cmd.price),
          stock = ProductStock(productId, cmd.numberOfProductToAdd)
        )
        productRepository.save(newProduct).map{ _ =>
          (productId, List(ProductAdded(newProduct)))
        }
    }
  }
}
