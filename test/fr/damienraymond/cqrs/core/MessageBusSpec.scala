package fr.damienraymond.cqrs.core

import java.util.UUID

import fr.damienraymond.cqrs.core.event.{Event, EventCaptor}
import fr.damienraymond.cqrs.core.infrastructure.bus.{CommandBusImplementation, QueryBusImplementation}
import fr.damienraymond.cqrs.core.middleware.{CommandMiddleware, QueryMiddleware}
import org.scalatestplus.play.PlaySpec
import org.mockito.Mockito._
import org.mockito.Matchers._
import org.mockito.{InOrder, Mockito}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class MessageBusSpec extends PlaySpec {


  def provideCommandBus(handlers: Set[CommandHandler[Command[_],_]], middlewares: List[CommandMiddleware]): CommandBus =
    new CommandBusImplementation(handlers.asInstanceOf[Set[CommandHandler[Command[Any], Any]]], middlewares)

  def provideQueryBus(handlers: Set[QueryHandler[Query[_],_]], middlewares: List[QueryMiddleware]): QueryBus =
    new QueryBusImplementation(handlers.asInstanceOf[Set[QueryHandler[Query[Any], Any]]], middlewares)


  "A command bus" should {
    "dispatch commands and call middlewares in the right order" in {

      import CommandObjects._

      // COMMAND HANDLERS

      val createSomethingCommandHandlerSpy = spy(new CreateSomethingCommandHandler)
      val someBusinessCommandHandlerSpy = spy(new SomeBusinessCommandHandler)


      val commandHandlers: Set[CommandHandler[Command[_], _]] = Set(
        createSomethingCommandHandlerSpy.asInstanceOf[CommandHandler[Command[_], _]],
        someBusinessCommandHandlerSpy.asInstanceOf[CommandHandler[Command[_], _]]
      )


      // MIDDLEWARES

      val middleware1Spy = spy(new FakeCommandMiddleware)
      val middleware2Spy = spy(new FakeCommandMiddleware)


      // COMMAND

      val createSomethingCommand = CreateSomethingCommand("azd", 0f)
      val someBusinessCommand = SomeBusinessCommand("azd")


      val bus = provideCommandBus(commandHandlers, List(middleware1Spy, middleware2Spy))


      // DISPATCHING FIRST COMMAND

      bus.dispatch(createSomethingCommand)

      val orderedSpies1 = Mockito.inOrder(middleware1Spy, middleware2Spy)

      orderedSpies1.verify(middleware1Spy).apply(any(), any())
      orderedSpies1.verify(middleware2Spy).apply(any(), any())

      verify(createSomethingCommandHandlerSpy).handle(createSomethingCommand)
      verify(someBusinessCommandHandlerSpy, never()).handle(any[SomeBusinessCommand])

      reset(createSomethingCommandHandlerSpy, someBusinessCommandHandlerSpy)
      reset(middleware1Spy, middleware2Spy)


      // DISPATCHING SECOND COMMAND

      bus.dispatch(someBusinessCommand)

      val orderedSpies2 = Mockito.inOrder(middleware1Spy, middleware2Spy)

      orderedSpies2.verify(middleware1Spy).apply(any(), any())
      orderedSpies2.verify(middleware2Spy).apply(any(), any())

      verify(createSomethingCommandHandlerSpy, never()).handle(any[CreateSomethingCommand])
      verify(someBusinessCommandHandlerSpy).handle(someBusinessCommand)


    }
  }


  "A query bus" should {
    "dispatch queries and call middlewares in the right order" in {
      import QueryObjects._


      // QUERY HANDLERS

      val someQueryHandlerSpy = spy(new SomeQueryHandler)
      val someOtherQueryHandlerSpy = spy(new SomeOtherQueryHandler)


      val queryHandlers: Set[QueryHandler[Query[_], _]] = Set(
        someQueryHandlerSpy.asInstanceOf[QueryHandler[Query[_], _]],
        someOtherQueryHandlerSpy.asInstanceOf[QueryHandler[Query[_], _]],
      )


      // MIDDLEWARES
      val middleware1Spy = spy(new FakeQueryMiddleware)
      val middleware2Spy = spy(new FakeQueryMiddleware)
      val middleware3Spy = spy(new FakeQueryMiddleware)



      // QUERIES
      val someQuery = SomeQuery("dazd")
      val someOtherQuery = SomeOtherQuery("dazd")

      val bus = provideQueryBus(queryHandlers, List(middleware1Spy, middleware2Spy, middleware3Spy))

      // DISPATCHING FIRST QUERY

      bus.dispatch(someQuery)

      val orderedSpies1 = Mockito.inOrder(middleware1Spy, middleware2Spy, middleware3Spy)

      orderedSpies1.verify(middleware1Spy).apply(any(), any())
      orderedSpies1.verify(middleware2Spy).apply(any(), any())
      orderedSpies1.verify(middleware3Spy).apply(any(), any())

      verify(someQueryHandlerSpy).handle(someQuery)
      verify(someOtherQueryHandlerSpy, never()).handle(any[SomeOtherQuery])

      reset(someQueryHandlerSpy, someOtherQueryHandlerSpy)
      reset(middleware1Spy, middleware2Spy, middleware3Spy)


      // DISPATCHING SECOND QUERY

      bus.dispatch(someOtherQuery)

      val orderedSpies2 = Mockito.inOrder(middleware1Spy, middleware2Spy, middleware3Spy)

      orderedSpies2.verify(middleware1Spy).apply(any(), any())
      orderedSpies2.verify(middleware2Spy).apply(any(), any())
      orderedSpies2.verify(middleware3Spy).apply(any(), any())

      verify(someQueryHandlerSpy, never()).handle(any[SomeQuery])
      verify(someOtherQueryHandlerSpy).handle(someOtherQuery)


    }
  }

}

private object CommandObjects {

  case class CreateSomethingCommand(name: String, price: Float) extends Command[UUID]


  class CreateSomethingCommandHandler extends CommandHandler[CreateSomethingCommand, UUID] {
    override def handle(cmd: CreateSomethingCommand): Future[(UUID, List[Event[_]])] =
      Future.successful((UUID.randomUUID(), List.empty))
  }



  case class SomeBusinessCommand(name: String) extends Command[Unit]


  class SomeBusinessCommandHandler extends CommandHandler[SomeBusinessCommand, Unit] {
    override def handle(cmd: SomeBusinessCommand): Future[(Unit, List[Event[_]])] =
      Future.successful((UUID.randomUUID(), List.empty))
  }


  class FakeCommandMiddleware extends CommandMiddleware {
    override def apply[RETURN_T](value: Message[RETURN_T], next: () => Future[(RETURN_T, List[Event[_]])]): Future[(RETURN_T, List[Event[_]])] = next()
  }

}


private object QueryObjects {

  case class SomeQuery(param: String) extends Query[String]

  class SomeQueryHandler extends QueryHandler[SomeQuery, String]{
    override def handle(query: SomeQuery): Future[String] = Future.successful("azdazdazd")
  }

  case class SomeOtherQuery(param: String) extends Query[List[Int]]

  class SomeOtherQueryHandler extends QueryHandler[SomeOtherQuery, List[Int]] {
    override def handle(query: SomeOtherQuery): Future[List[Int]] = Future.successful(List.empty)
  }


  class FakeQueryMiddleware extends QueryMiddleware {
    override def apply[RETURN_T](value: Message[RETURN_T], next: () => Future[RETURN_T]): Future[RETURN_T] = next()
  }

}