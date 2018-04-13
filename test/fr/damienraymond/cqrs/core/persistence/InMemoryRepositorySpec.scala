package fr.damienraymond.cqrs.core.persistence

import java.util.UUID

import fr.damienraymond.cqrs.core.entity.AggregateRoot
import org.scalatest.{FlatSpec, Matchers}

class InMemoryRepositorySpec extends FlatSpec with Matchers {

  "An in memory repository" should "allow to set and get things" in {
    case class Test(id: UUID, name: String) extends AggregateRoot[UUID]

    val test1 = Test(UUID.randomUUID(), "name1")
    val test2 = Test(UUID.randomUUID(), "name2")

    class TestIMR extends InMemoryRepository[UUID, Test]

    val imr = new TestIMR

    imr.save(test1)
    imr.save(test2)

    imr.getAll should have size 2
    imr.getAll should contain theSameElementsAs List(test1, test2)

  }

  "An in memory repository" should "allow to replace thinks with the same id" in {
    case class Test(id: UUID, name: String) extends AggregateRoot[UUID]

    val testId = UUID.randomUUID()
    val test1 = Test(testId, "name1")
    val test2 = Test(testId, "name2")

    class TestIMR extends InMemoryRepository[UUID, Test]

    val imr = new TestIMR

    imr.save(test1)
    imr.save(test2)

    imr.getAll should have size 1
    imr.getAll should contain theSameElementsAs List(test2)

  }

}
