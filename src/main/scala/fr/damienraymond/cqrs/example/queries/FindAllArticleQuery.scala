//package fr.damienraymond.cqrs.example.queries
//
//import java.util.UUID
//
//import com.google.inject.Inject
//import fr.damienraymond.cqrs.core.{Query, QueryHandler}
//import fr.damienraymond.cqrs.example.infrastructure.persistence.ProductRepository
//import fr.damienraymond.cqrs.example.model.product.Product
//
//import scala.concurrent.Future
//
//case class FindAllArticleQuery(id: UUID) extends Query[List[Product]]
//
//
//class FindAllArticle @Inject()(productRepository: ProductRepository) extends QueryHandler[FindAllArticleQuery, List[Product]] {
//
//  override def handle(message: FindAllArticleQuery) =
//    Future.successful(productRepository.getAllById(message.id))
//
//}
//
//
//
