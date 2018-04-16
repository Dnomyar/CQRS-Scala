package fr.damienraymond.cqrs.example.commands

import java.util.UUID

import com.google.inject.Inject
import fr.damienraymond.cqrs.core.event.Event
import fr.damienraymond.cqrs.core.{Command, CommandHandler}
import fr.damienraymond.cqrs.example.infrastructure.persistence.{ProductRepository, SellerRepository}
import fr.damienraymond.cqrs.example.model.seller.Seller
import fr.damienraymond.cqrs.example.model.seller.events.ProductsAssignedToSeller
import fr.damienraymond.cqrs.example.model.seller.events.errors.SomeProductsWerentFound

import scala.concurrent.{ExecutionContext, Future}

case class AssignProductsToSellerCommand(sellerName: String, products: Set[UUID]) extends Command[Option[UUID]]



class AssignProductsToSellerCommandHandler @Inject()(productRepository: ProductRepository,
                                                     sellerRepository: SellerRepository)(implicit ec: ExecutionContext) extends CommandHandler[AssignProductsToSellerCommand, Option[UUID]] {

  override def handle(cmd: AssignProductsToSellerCommand): Future[(Option[UUID], List[Event[_]])] = {

    val findOfCreateSellerId = (sellers: List[Seller]) =>
      sellers.find(_.name == cmd.sellerName)
        .map(_.id)
        .getOrElse(UUID.randomUUID())


    sellerRepository.getAll
      .map(findOfCreateSellerId)
      .flatMap{ sellerId =>
        getAllProductsThatNotExist(cmd.products).map {
          case nonExistingProducts if nonExistingProducts.isEmpty =>

            val seller = Seller(
              sellerId,
              cmd.sellerName,
              cmd.products
            )

            sellerRepository.save(seller)

            (Some(sellerId), List(ProductsAssignedToSeller(seller)))

          case nonExistingProducts =>
            (None, List(SomeProductsWerentFound(cmd.sellerName, cmd.products, nonExistingProducts)))
        }
      }
  }


  def getAllProductsThatNotExist(products: Set[UUID]): Future[Set[UUID]] = {
    Future.sequence(products.map(productRepository.get)).map{ productsListOpt =>
      products.diff(productsListOpt.flatMap(_.map(_.id)))
    }
  }

}
