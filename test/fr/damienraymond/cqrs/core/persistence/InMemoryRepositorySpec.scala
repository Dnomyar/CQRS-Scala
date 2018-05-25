package fr.damienraymond.cqrs.core.persistence

import java.util.UUID

import fr.damienraymond.cqrs.core.entity.AggregateRoot
import fr.damienraymond.cqrs.helpers.FutureAwaiter
import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.{Await, Future}

class InMemoryRepositorySpec extends FlatSpec with Matchers with FutureAwaiter {

  "An in memory repository" should "allow to set and get things" in {
    case class Test(id: UUID, name: String) extends AggregateRoot[UUID]

    val test1 = Test(UUID.randomUUID(), "name1")
    val test2 = Test(UUID.randomUUID(), "name2")

    class TestIMR extends InMemoryRepository[UUID, Test]

    val imr = new TestIMR

    await(imr.save(test1))
    await(imr.save(test2))

    await(imr.getAll) should have size 2
    await(imr.getAll) should contain theSameElementsAs List(test1, test2)

  }

  "An in memory repository" should "allow to replace thinks with the same id" in {
    case class Test(id: UUID, name: String) extends AggregateRoot[UUID]

    val testId = UUID.randomUUID()
    val test1 = Test(testId, "name1")
    val test2 = Test(testId, "name2")

    class TestIMR extends InMemoryRepository[UUID, Test]

    val imr = new TestIMR

    await(imr.save(test1))
    await(imr.save(test2))

    await(imr.getAll) should have size 1
    await(imr.getAll) should contain theSameElementsAs List(test2)

  }





}
