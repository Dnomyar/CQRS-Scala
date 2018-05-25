package fr.damienraymond.cqrs.core.entity

import java.util.UUID

import org.scalatestplus.play.PlaySpec


private[this] case class EntityTest(id: UUID, name: String) extends Entity[UUID]

class EntitySpec extends PlaySpec  {

  "Two entities with the same id" should {

    "be equals" in {

      val id = UUID.randomUUID()

      val e1 = EntityTest(id, "name")
      val e2 = EntityTest(id, "name")

      e1 mustBe e2
    }


    "be equals even if it content differs" in {

      val id = UUID.randomUUID()

      val e1 = EntityTest(id, "name 1")
      val e2 = EntityTest(id, "name 2")

      e1 mustBe e2
    }
  }


  "Two entities with different id" should {
    "be different" in {

      val e1 = EntityTest(UUID.randomUUID(), "name 1")
      val e2 = EntityTest(UUID.randomUUID(), "name 2")

      e1 mustNot be (e2)
    }
  }

}
