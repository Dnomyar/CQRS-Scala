//package fr.damienraymond.cqrs.example
//
//import java.util.UUID
//
//import fr.damienraymond.cqrs.core.infrastructure.bus.{CommandBus, QueryBus}
//import fr.damienraymond.cqrs.example.commands.{PutArticleOnSale, PutArticleOnSaleHandler}
//import fr.damienraymond.cqrs.example.infrastructure.persistence.StoreRepository
//import fr.damienraymond.cqrs.example.model.product.Product
//import fr.damienraymond.cqrs.example.queries.{FindArticleByNameQuery, FindArticleByNameQueryHandler}
//
//object Main {
//
//  def main(args: Array[String]): Unit = {
//
//    val articleId = UUID.randomUUID()
//    val articleRepository = new StoreRepository
//
//    val putArticleOnSale = PutArticleOnSale(articleId, .2f)
//
//    val commandBus = new CommandBus(List.empty)
//
//    commandBus.bind(new PutArticleOnSaleHandler(articleRepository))
//
//    commandBus.dispatch[PutArticleOnSale, Unit](putArticleOnSale)
//
//
//
//    val query = FindArticleByNameQuery("fish stick")
//
//    val queryBus = new QueryBus(List.empty)
//
//
//    queryBus.bind(new FindArticleByNameQueryHandler)
//
//    val article = queryBus.dispatch[FindArticleByNameQuery, Option[Product]](query)
//
//    println(s"Found article : $article")
//
//  }
//
//}
