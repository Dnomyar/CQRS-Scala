package fr.damienraymond.cqrs.example

import fr.damienraymond.cqrs.CommandBus

object Main {

  def main(args: Array[String]): Unit = {

    val createArticle = CreateArticle(0)
    val deleteArticle = DeleteArticle(0)

    val createArticleHandler = new CreateArticleHandler
    val deleteArticleHandler = new DeleteArticleHandler

    val commandBus = new CommandBus

    commandBus.bind[Unit, CreateArticle, Unit](new CreateArticleHandler)(createArticle.getClass)
    commandBus.bind[Unit, DeleteArticle, Unit](new DeleteArticleHandler)(deleteArticle.getClass)

    commandBus.dispatch[Unit, CreateArticle](createArticle)(createArticle.getClass)
    commandBus.dispatch[Unit, DeleteArticle](deleteArticle)(deleteArticle.getClass)


  }

}
