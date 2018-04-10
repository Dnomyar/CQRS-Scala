package fr.damienraymond.cqrs.core

trait Logger {
  lazy val logger = new {
    def info(message: => String): Unit = println(s"[INFO] $message")
  }
}
