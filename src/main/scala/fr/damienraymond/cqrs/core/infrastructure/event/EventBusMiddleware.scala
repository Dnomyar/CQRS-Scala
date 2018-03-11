package fr.damienraymond.cqrs.core.infrastructure.event

import com.google.inject.Inject
import fr.damienraymond.cqrs.core.Message
import fr.damienraymond.cqrs.core.event.{Event, EventBus}
import fr.damienraymond.cqrs.core.middleware.{CommandMiddleware, Middleware}

class EventBusMiddleware @Inject()(eventBus: EventBus) extends CommandMiddleware {

  override def apply[T](value: Message[T], next: () => Either[String, (T, List[Event[_]])]): Either[String, (T, List[Event[_]])] = {

    val res: Either[String, (T, List[Event[_]])] = next()


    if(res.isRight){
      val events: List[Event[_]] = res.right.get._2

      eventBus.publish(events)
    }


    res

  }

}
