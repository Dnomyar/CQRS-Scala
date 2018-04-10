package fr.damienraymond.cqrs.core.event

import com.sun.corba.se.impl.orbutil.closure.Future

trait EventBus {
  def publish(events: List[Event[_]]): Unit
}
