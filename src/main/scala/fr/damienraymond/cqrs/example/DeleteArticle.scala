package fr.damienraymond.cqrs.example

import fr.damienraymond.cqrs.{Command, Handler}


case class DeleteArticle(id: Int) extends Command[Unit]


class DeleteArticleHandler extends Handler[Unit, DeleteArticle, Unit] {
  override def handle(message: DeleteArticle): Unit = {
    println(s"Deleting of article ${message.id}")
  }
}
