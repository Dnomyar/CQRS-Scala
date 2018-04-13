package fr.damienraymond.cqrs.example.queries.product.allusingrepositories.handlers

import java.util.UUID

import com.google.inject.Inject
import fr.damienraymond.cqrs.core.{Query, QueryHandler}
import fr.damienraymond.cqrs.example.infrastructure.persistence.{ProductRepository, SellerRepository}
import fr.damienraymond.cqrs.example.queries.product.allusingrepositories.model.{ProductView, SellerView}
import fr.damienraymond.cqrs.example.model.product.Product

import scala.concurrent.Future

case class AllProductsQuery() extends Query[List[ProductView]]

class AllProductsQueryHandler @Inject()(productRepository: ProductRepository,
                                        sellerRepository: SellerRepository,
                                        productToProductView: ProductToProductView) extends QueryHandler[AllProductsQuery, List[ProductView]] {
  override def handle(message: AllProductsQuery): Future[List[ProductView]] = {
    val sellers = sellerRepository.getAll

    Future.successful(
      productRepository
        .getAll
        .flatMap(productToProductView.toProductView(sellers)(_).toList)
    )
  }

}
