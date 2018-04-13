package fr.damienraymond.cqrs.core.infrastructure.event

import com.google.inject.Inject
import fr.damienraymond.cqrs.core.Message
import fr.damienraymond.cqrs.core.event.{Event, SynchronizedEventBus}
import fr.damienraymond.cqrs.core.middleware.{CommandMiddleware, Middleware}

import scala.concurrent.{ExecutionContext, Future}

class SynchronizedEventBusMiddleware @Inject()(eventBus: SynchronizedEventBus)(implicit ec: ExecutionContext) extends CommandMiddleware {


  override def apply[RETURN_T](value: Message[RETURN_T], next: () => Future[(RETURN_T, List[Event[_]])]) = {
    next().flatMap {
      case res @ (_, events) =>
        eventBus.publish(events).map(_ => res)
    }
  }


}
