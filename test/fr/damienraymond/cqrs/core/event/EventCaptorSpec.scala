package fr.damienraymond.cqrs.core.event

import org.scalatest._

import scala.concurrent.Future
import scala.reflect.runtime.universe._

case class TestEvent() extends Event[String]

class EventCaptorSpec extends FlatSpec with Matchers {

  "A EventCaptor message type" should "be right" in {

    class TestEventCaptor extends EventCaptor[TestEvent] {
      override def execute(event: TestEvent): Future[Unit] = ???
    }

    (new TestEventCaptor).eventType should be (typeOf[TestEvent])
  }


}
