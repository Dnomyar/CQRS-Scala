package fr.damienraymond.cqrs.helpers

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

trait FutureAwaiter {

  def await[A](f: Future[A]): A = Await.result(f, 30.seconds)

}
