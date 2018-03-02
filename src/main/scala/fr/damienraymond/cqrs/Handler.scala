package fr.damienraymond.cqrs

trait Handler[COMMAND <: Command[TARGET_TYPE], TARGET_TYPE] {

  def handle(message: COMMAND): TARGET_TYPE

}
