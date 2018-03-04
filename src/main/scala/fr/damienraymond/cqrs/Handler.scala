package fr.damienraymond.cqrs

trait Handler[MESSAGE <: Message[TARGET_TYPE], TARGET_TYPE] {

  def handle(message: MESSAGE): TARGET_TYPE

}


trait CommandHandler[COMMAND <: Command[TARGET_TYPE], TARGET_TYPE] extends Handler[COMMAND, TARGET_TYPE]

trait QueryHandler[COMMAND <: Query[TARGET_TYPE], TARGET_TYPE] extends Handler[COMMAND, TARGET_TYPE]
