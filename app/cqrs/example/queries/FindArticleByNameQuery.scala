//package fr.damienraymond.cqrs.example.queries
//
//import java.util.UUID
//
//import fr.damienraymond.cqrs.core.{Query, QueryHandler}
//import fr.damienraymond.cqrs.example.model.product.Product
//
//case class FindArticleByNameQuery(name: String) extends Query[Option[Product]]
//
//
//class FindArticleByNameQueryHandler extends QueryHandler[FindArticleByNameQuery, Option[Product]] {
//  override def handle(message: FindArticleByNameQuery) = {
//
//    // sql request
//
//    Some(Article(UUID.randomUUID(), message.name, 10f))
//  }
//}