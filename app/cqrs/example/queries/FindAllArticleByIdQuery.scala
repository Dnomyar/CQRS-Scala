//package fr.damienraymond.cqrs.example.queries
//
//import com.google.inject.Inject
//import fr.damienraymond.cqrs.core.{Query, QueryHandler}
//import fr.damienraymond.cqrs.example.infrastructure.persistence.ProductRepository
//import fr.damienraymond.cqrs.example.model.product.Product
//
//import scala.concurrent.Future
//
//case class FindAllArticleByIdQuery(todoRemoveMe: String) extends Query[List[Product]]
//
//
//class FindAllArticleByIdQueryHandler @Inject()(productRepository: ProductRepository) extends QueryHandler[FindAllArticleByIdQuery, List[Product]] {
//
//  override def handle(message: FindAllArticleByIdQuery) =
//    Future.successful(productRepository.getAll)
//
//}
//
//
//
