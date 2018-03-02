package fr.damienraymond.cqrs.example

import fr.damienraymond.cqrs.{Command, Handler}

case class CreateArticle(id: Int) extends Command[Unit]


class CreateArticleHandler extends Handler[Unit, CreateArticle, Unit] {
  override def handle(message: CreateArticle): Unit = {
    println(s"Creation of article ${message.id}")
  }
}
