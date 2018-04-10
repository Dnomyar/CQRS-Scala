//package fr.damienraymond.cqrs.core.infrastructure.bus
//
//import fr.damienraymond.cqrs.core.middleware.Middleware
//import fr.damienraymond.cqrs.core.{Command, Handler, Message}
//import org.scalatest._
//
//class CommandBusSpec extends FlatSpec with Matchers {
//
//  "CommandBus" should
//    "route a message" in new Context {
//
//      val createArticle = CreateArticle(0)
//
//      val createArticleHandler = new CreateArticleHandler
//
//      val commandBus = new CommandBus(List.empty)
//
//      val article: Either[String, Article] = commandBus.dispatch[CreateArticle, Article](createArticle)
//
//      article should === (Right(Article(0)))
//  }
//
//
//
//  "A bus" should
//    "call middlewares" in new Context {
//
//    var i = 0
//
//    val createArticle = CreateArticle(0)
//
//    val createArticleHandler = new Handler[CreateArticle, Article] {
//      override def handle(message: CreateArticle) = {
//        println("handler")
//        Article(message.id)
//      }
//    }
//
//    val middleware = new Middleware {
//
//      override def apply[T](value: Message[T], next: () => Either[String, T]) = {
//        println("before next")
//
//        i += 1
//        val res = next()
//
//        println("after next")
//
//        res
//      }
//
//    }
//
//    val commandBus = new CommandBus(List(middleware))
//
//    commandBus.bind(createArticleHandler)
//
//    val article: Either[String, Article] = commandBus.dispatch[CreateArticle, Article](createArticle)
//
//
//
//    article should === (Right(Article(0)))
//    i should === (1)
//
//  }
//
//}
//
//trait Context {
//  case class Article(id: Int)
//
//  case class CreateArticle(id: Int) extends Command[Article]
//
//
//  class CreateArticleHandler extends Handler[CreateArticle, Article] {
//    override def handle(message: CreateArticle): Article = {
//      Article(message.id)
//    }
//  }
//}
