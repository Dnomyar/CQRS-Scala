package fr.damienraymond.cqrs.core.event

import java.util.UUID

import fr.damienraymond.cqrs.core.infrastructure.event.EventBusImplementation
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec
import play.api.libs.json.{JsArray, JsValue}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class EventBusSpec extends PlaySpec with MockitoSugar {

  def provideEventBus(eventCaptors: Set[EventCaptor[Event[_]]]): EventBus =
    new EventBusImplementation(eventCaptors.asInstanceOf[Set[EventCaptor[Event[Any]]]])


  "An event bus" should {

    "publish events" in {


      class SomeEventEventCaptor extends EventCaptor[SomeEvent] {
        override def execute(event: SomeEvent): Future[Unit] = Future.successful(())
      }

      class SomeOtherEventEventCaptor extends EventCaptor[SomeOtherEvent] {
        override def execute(event: SomeOtherEvent): Future[Unit] = Future.successful(())
      }

      val spySomeEventEventCaptor = spy(new SomeEventEventCaptor)
      val spySomeOtherEventEventCaptor = spy(new SomeOtherEventEventCaptor)


      val bus = provideEventBus(Set(
        spySomeEventEventCaptor.asInstanceOf[EventCaptor[Event[_]]],
        spySomeOtherEventEventCaptor.asInstanceOf[EventCaptor[Event[_]]]
      ))


      val someEvent = SomeEvent(UUID.randomUUID(), "azdazd")
      val someOtherEvent = SomeOtherEvent(UUID.randomUUID(), JsArray())


      for{
        _ <- bus.publish(List(someEvent))
        _ <- bus.publish(List(someOtherEvent))
      } yield {
        verify(spySomeEventEventCaptor).execute(someEvent)
        verify(spySomeOtherEventEventCaptor).execute(someOtherEvent)
      }


    }

  }


}


case class SomeEvent(id: UUID, data: String) extends Event[String]
case class SomeOtherEvent(id: UUID, data: JsValue) extends Event[JsValue]

