package fr.damienraymond.cqrs.core.web.context.communication

import fr.damienraymond.cqrs.core.{Command, CommandHandler}
import fr.damienraymond.cqrs.core.event.{Event, EventCaptor, SynchronizedEventBus}
import fr.damienraymond.cqrs.core.infrastructure.event.SynchronizedEventBusImplementation
import fr.damienraymond.cqrs.core.infrastructure.unitofwork.UnitOfWorkImplementation
import fr.damienraymond.cqrs.core.module.{ClassScannerModule, CoreModule, FakeModule}
import fr.damienraymond.cqrs.core.persistence.UnitOfWork
import fr.damienraymond.cqrs.example.module.ExampleModule
import fr.damienraymond.cqrs.helpers.FutureAwaiter
import org.scalatest.{FlatSpec, Matchers}
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.ws.WSClient
import play.api.mvc._
import play.api.routing._
import play.api.routing.sird._

import scala.collection.immutable
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration._
import play.api.inject.bind
import play.api.libs.json.Json


private[this] case class Person(name: String, age: Int)

private[this] object Person {
  implicit val format = Json.format[Person]
}


class ServiceCallSpec extends PlaySpec with GuiceOneServerPerSuite with FutureAwaiter {

  override def fakeApplication(): Application =
    new GuiceApplicationBuilder()
      .disable[ClassScannerModule]
      .overrides(new FakeModule)
      .router(Router.from {
          case GET(p"/get_text") => Action { Results.Ok("ok") }
          case GET(p"/get_json") => Action { Results.Ok(Json.obj("name" -> "John", "age" -> 123)) }
          case POST(p"/post_text") => Action { Results.Ok("ok") }
          case POST(p"/post_json") => Action { Results.Ok(Json.obj("name" -> "John", "age" -> 123)) }
          case PUT(p"/put_text") => Action { Results.Ok("ok") }
          case PUT(p"/put_json") => Action { Results.Ok(Json.obj("name" -> "John", "age" -> 123)) }
          case DELETE(p"/delete_text") => Action { Results.Ok("ok") }
          case DELETE(p"/delete_json") => Action { Results.Ok(Json.obj("name" -> "John", "age" -> 123)) }
        }).build()


  "A Service Call" should {


    "GET a request to another service and return text" in {
      val serviceCallFactory = app.injector.instanceOf[ServiceCallFactory]
      implicit val ec = app.injector.instanceOf[ExecutionContext]

      val resp = await(serviceCallFactory.get(s"http://localhost:$port/get_text").text)

      resp mustBe "ok"
    }


    "GET a request to another service and parse and return json" in {
      val serviceCallFactory = app.injector.instanceOf[ServiceCallFactory]
      implicit val ec = app.injector.instanceOf[ExecutionContext]

      val resp = await(serviceCallFactory.get(s"http://localhost:$port/get_json").as[Person])

      resp mustBe Person("John", 123)
    }


    "POST a request to another service and return text" in {
      val serviceCallFactory = app.injector.instanceOf[ServiceCallFactory]
      implicit val ec = app.injector.instanceOf[ExecutionContext]

      val resp = await(serviceCallFactory.post(s"http://localhost:$port/post_text", Person("Alfred", 23)).text)

      resp mustBe "ok"
    }


    "POST a request to another service and parse and return json" in {
      val serviceCallFactory = app.injector.instanceOf[ServiceCallFactory]
      implicit val ec = app.injector.instanceOf[ExecutionContext]

      val resp = await(serviceCallFactory.post(s"http://localhost:$port/post_json", Person("Alfred", 23)).as[Person])

      resp mustBe Person("John", 123)
    }


    "PUT a request to another service and return text" in {
      val serviceCallFactory = app.injector.instanceOf[ServiceCallFactory]
      implicit val ec = app.injector.instanceOf[ExecutionContext]

      val resp = await(serviceCallFactory.put(s"http://localhost:$port/put_text", Person("Alfred", 23)).text)

      resp mustBe "ok"
    }


    "PUT a request to another service and parse and return json" in {
      val serviceCallFactory = app.injector.instanceOf[ServiceCallFactory]
      implicit val ec = app.injector.instanceOf[ExecutionContext]

      val resp = await(serviceCallFactory.put(s"http://localhost:$port/put_json", Person("Alfred", 23)).as[Person])

      resp mustBe Person("John", 123)
    }


    "DELETE a request to another service and return text" in {
      val serviceCallFactory = app.injector.instanceOf[ServiceCallFactory]
      implicit val ec = app.injector.instanceOf[ExecutionContext]

      val resp = await(serviceCallFactory.delete(s"http://localhost:$port/delete_text").text)

      resp mustBe "ok"
    }


    "DELETE a request to another service and parse and return json" in {
      val serviceCallFactory = app.injector.instanceOf[ServiceCallFactory]
      implicit val ec = app.injector.instanceOf[ExecutionContext]

      val resp = await(serviceCallFactory.delete(s"http://localhost:$port/delete_json").as[Person])

      resp mustBe Person("John", 123)
    }




    "return the appropriate error" in {
      val serviceCallFactory = app.injector.instanceOf[ServiceCallFactory]
      implicit val ec = app.injector.instanceOf[ExecutionContext]

      val resp = serviceCallFactory.get(s"http://localhost:$port/notFoundException").as[Person]

      await(resp.failed) mustBe ServiceCallException("GET", s"http://localhost:$port/notFoundException", 404, "Not Found")
    }
  }

}

