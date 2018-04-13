package fr.damienraymond.cqrs.core

import org.scalatest._
import scala.reflect.runtime.universe._

case class TestMessage() extends Message[String]

class MessageSpec extends FlatSpec with Matchers {

  "A Message type" should
    "be right" in {
      TestMessage().thisType should be (typeOf[TestMessage])
    }


}
