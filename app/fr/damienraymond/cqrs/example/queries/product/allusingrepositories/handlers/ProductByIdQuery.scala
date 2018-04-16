package fr.damienraymond.cqrs.example.queries.product.allusingrepositories.handlers

import java.util.UUID

import com.google.inject.Inject
import fr.damienraymond.cqrs.core.{Query, QueryHandler}
import fr.damienraymond.cqrs.example.infrastructure.persistence.{ProductRepository, SellerRepository}
import fr.damienraymond.cqrs.example.model.product.Product
import fr.damienraymond.cqrs.example.queries.product.allusingrepositories.model.{ProductView, SellerView}

import scala.concurrent.{ExecutionContext, Future}

case class ProductByIdQuery(productId: UUID) extends Query[Option[ProductView]]


class ProductByIdQueryHandler @Inject()(productRepository: ProductRepository,
                                        sellerRepository: SellerRepository,
                                        productToProductView: ProductToProductView)(implicit ec: ExecutionContext) extends QueryHandler[ProductByIdQuery, Option[ProductView]] {

  override def handle(message: ProductByIdQuery): Future[Option[ProductView]] = {
    for {
      sellers <- sellerRepository.getAll
      products <- productRepository.get(message.productId)
    } yield products.flatMap(productToProductView.toProductView(sellers))
  }

}