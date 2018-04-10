package fr.damienraymond.cqrs.example.model.product

case class Price(value: Double) extends AnyVal {

  def decreaseBy(percentage: Percentage): Price =
    Price(value * (1 - percentage.rate))

}
