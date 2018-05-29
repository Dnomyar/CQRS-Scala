package fr.damienraymond.cqrs.core.infrastructure.event

import com.google.inject.Inject
import fr.damienraymond.cqrs.core.Logger
import fr.damienraymond.cqrs.core.event.{Event, EventBus, EventCaptor}

import scala.concurrent.{ExecutionContext, Future}
import scala.reflect.runtime.universe._

class EventBusImplementation @Inject()(captors: Set[EventCaptor[Event[Any]]])(implicit ec: ExecutionContext) extends EventBus with Logger {
  override def publish(events: List[Event[_]]): Future[Unit] =
    Future.sequence(events.map(publishOne)).map(_ => ())

  private def publishOne(event: Event[_]): Future[Unit] = Future.sequence{
    captors
      .filter(_.eventType == event.thisType)
      .map { captor =>
        logger.trace(s"Applying captor : $captor")
        captor.asInstanceOf[EventCaptor[Event[_]]]
          .execute(event)
      }
  }.map(_ => ())



}
