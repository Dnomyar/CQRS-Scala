package fr.damienraymond.cqrs.example.model.product

import play.api.libs.json.Json

case class Price(value: Double) extends AnyVal {

  private[product] def decreaseBy(percentage: Percentage): Price =
    Price(value * (1 - percentage.rate))

}


object Price {
  implicit val format = Json.format[Price]
}


