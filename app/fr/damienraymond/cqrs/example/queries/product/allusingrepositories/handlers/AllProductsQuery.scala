package fr.damienraymond.cqrs.example.queries.product.allusingrepositories.handlers

import java.util.UUID

import com.google.inject.Inject
import fr.damienraymond.cqrs.core.{Query, QueryHandler}
import fr.damienraymond.cqrs.example.infrastructure.persistence.{ProductRepository, SellerRepository}
import fr.damienraymond.cqrs.example.queries.product.allusingrepositories.model.{ProductView, SellerView}
import fr.damienraymond.cqrs.example.model.product.Product

import scala.concurrent.{ExecutionContext, Future}

case class AllProductsQuery() extends Query[List[ProductView]]

class AllProductsQueryHandler @Inject()(productRepository: ProductRepository,
                                        sellerRepository: SellerRepository,
                                        productToProductView: ProductToProductView)(implicit ec: ExecutionContext) extends QueryHandler[AllProductsQuery, List[ProductView]] {
  override def handle(message: AllProductsQuery): Future[List[ProductView]] = {

    for {
      sellers <- sellerRepository.getAll
      products <- productRepository.getAll
    } yield products.flatMap(productToProductView.toProductView(sellers)(_).toList)

  }

}
