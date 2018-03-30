package fr.damienraymond.cqrs.core.infrastructure.event

import com.google.inject.Inject
import fr.damienraymond.cqrs.core.Message
import fr.damienraymond.cqrs.core.event.{Event, EventBus}
import fr.damienraymond.cqrs.core.middleware.{CommandMiddleware, Middleware}

import scala.concurrent.{ExecutionContext, Future}

class EventBusMiddleware @Inject()(eventBus: EventBus)(implicit ec: ExecutionContext) extends CommandMiddleware {


  override def apply[RETURN_T](value: Message[RETURN_T], next: () => Future[(RETURN_T, List[Event[_]])]) = {
    val res = next()
    res.foreach {
      case (_, events) => eventBus.publish(events)
    }
    res
  }


}
