package fr.damienraymond.cqrs.core.infrastructure.event

import com.google.inject.Inject
import fr.damienraymond.cqrs.core.Message
import fr.damienraymond.cqrs.core.event.{Event, EventBus, EventBusMiddleware}
import fr.damienraymond.cqrs.core.middleware.{CommandMiddleware, Middleware}

import scala.concurrent.{ExecutionContext, Future}

class EventBusMiddlewareImplementation @Inject()(eventBus: EventBus)(implicit ec: ExecutionContext) extends EventBusMiddleware {

  override def apply[RETURN_T](value: Message[RETURN_T], next: () => Future[(RETURN_T, List[Event[_]])]) = {
    next().flatMap {
      case res @ (_, events) =>
        eventBus.publish(events).map(_ => res)
    }
  }

}
