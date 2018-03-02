import fr.damienraymond.cqrs.{Command, CommandBus, Handler}
import fr.damienraymond.cqrs.example.model.Article
import org.scalatest._

class CommandBusSpec extends FlatSpec with Matchers {

  "CommandBus" should
    "route a message" in new Context {

      val createArticle = CreateArticle(0)

      val createArticleHandler = new CreateArticleHandler

      val commandBus = new CommandBus

      commandBus.bind(createArticleHandler)

      val article: Option[Article] = commandBus.dispatch[CreateArticle, Article](createArticle)

      article should === (Some(Article(0)))
  }

}

trait Context {
  case class Article(id: Int)

  case class CreateArticle(id: Int) extends Command[Article]


  class CreateArticleHandler extends Handler[CreateArticle, Article] {
    override def handle(message: CreateArticle): Article = {
      Article(message.id)
    }
  }
}
