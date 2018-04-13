package fr.damienraymond.cqrs.example.commands

import java.util.UUID

import com.google.inject.Inject
import fr.damienraymond.cqrs.core.event.Event
import fr.damienraymond.cqrs.core.{Command, CommandHandler}
import fr.damienraymond.cqrs.example.infrastructure.persistence.{ProductRepository, SellerRepository}
import fr.damienraymond.cqrs.example.model.seller.Seller
import fr.damienraymond.cqrs.example.model.seller.events.ProductsAssignedToSeller
import fr.damienraymond.cqrs.example.model.seller.events.errors.SomeProductsWerentFound

case class
AssignProductsToSellerCommand(sellerName: String, products: Set[UUID]) extends Command[Option[UUID]]



class AssignProductsToSellerCommandHandler @Inject()(productRepository: ProductRepository,
                                                     sellerRepository: SellerRepository) extends CommandHandler[AssignProductsToSellerCommand, Option[UUID]] {

  override def handle(cmd: AssignProductsToSellerCommand): (Option[UUID], List[Event[_]]) = {

    val sellerId =
      sellerRepository.getAll.find(_.name == cmd.sellerName)
        .map(_.id)
        .getOrElse(UUID.randomUUID())


    getAllProductsThatNotExist(cmd.products) match {
      case nonExistingProducts if nonExistingProducts.isEmpty =>

        val seller = Seller(
          sellerId,
          cmd.sellerName,
          cmd.products
        )

        (Some(sellerId), List(ProductsAssignedToSeller(seller)))

      case nonExistingProducts =>
        (None, List(SomeProductsWerentFound(cmd.sellerName, cmd.products, nonExistingProducts)))
    }
  }


  def getAllProductsThatNotExist(products: Set[UUID]): Set[UUID] = {
    val productsListOpt = products.map(productRepository.get)

    val productsNotFound = products.diff(productsListOpt.flatMap(_.map(_.id)))

    productsNotFound
  }

}
