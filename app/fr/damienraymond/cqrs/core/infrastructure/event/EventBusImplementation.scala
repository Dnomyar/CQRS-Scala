package fr.damienraymond.cqrs.core.infrastructure.event

import com.google.inject.Inject
import fr.damienraymond.cqrs.core.event.{Event, EventBus, EventCaptor}

import scala.concurrent.ExecutionContext

class EventBusImplementation @Inject()(captors: Set[EventCaptor[Event[Any]]])(implicit ec: ExecutionContext) extends EventBus {


  override def publish(events: List[Event[_]]): Unit =
    events.foreach(publishOne)

  private def publishOne(event: Event[_]): Unit = {
    captors
      .filter(_.eventType == event.messageType)
      .foreach { captor =>
        println(s"Applying captor : $captor")
        captor.asInstanceOf[EventCaptor[Event[_]]]
          .execute(event)
          .failed.foreach(handleFailure(captor, event))
      }
  }


  private def handleFailure(captor: EventCaptor[_], event: Event[_]): PartialFunction[Throwable, Unit] = {
    case e: Exception =>
      println(s"ERROR: got error on captor ${captor.getClass} with event $event. Error : $e")
  }



}