package fr.damienraymond.cqrs.example.queries.product.all.handlers

import java.util.UUID

import com.google.inject.Inject
import fr.damienraymond.cqrs.core.{Query, QueryHandler}
import fr.damienraymond.cqrs.example.infrastructure.persistence.{ProductRepository, SellerRepository}
import fr.damienraymond.cqrs.example.queries.product.all.model.{ProductView, SellerView}
import fr.damienraymond.cqrs.example.model.product.Product

import scala.concurrent.Future

case class AllProducts() extends Query[List[ProductView]]

class AllProductsProductHandler @Inject()(productRepository: ProductRepository,
                                          sellerRepository: SellerRepository) extends QueryHandler[AllProducts, List[ProductView]] {
  override def handle(message: AllProducts): Future[List[ProductView]] =
    Future.successful(productRepository.getAll.flatMap(toProductView(_).toList))


  def toProductView(product: Product): Option[ProductView] = {
    sellerRepository.getAll.find(_.products.contains(product.id)).map { seller =>
      ProductView(
        product.id,
        product.name,
        product.price.value,
        product.price.value * 1.2,
        product.stock.numberOfAvailableProducts,
        product.storeId,
        SellerView(
          seller.id,
          seller.name,
          getSellerPictUrl(seller.id)
        )
      )
    }
  }


  def getSellerPictUrl(id: UUID) = s"http://localhost:9000/img/$id"

}
