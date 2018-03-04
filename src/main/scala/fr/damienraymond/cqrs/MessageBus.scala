package fr.damienraymond.cqrs

import scala.language.higherKinds


/**
  * WIP
  * Contains a lot of casts and mutations
  *
  * TODO : A Query bus can have command
  */
trait MessageBus {

  private var commandMap = Map.empty[Class[_], Handler[_, _]]

  def bind[MESSAGE <: Message[RETURN_TYPE], RETURN_TYPE](handler: Handler[MESSAGE, RETURN_TYPE])(implicit commandClass: Manifest[MESSAGE]): Unit = {
    commandMap = commandMap +
      (commandClass.runtimeClass -> handler.asInstanceOf[Handler[Command[Any], Any]])
  }


  def dispatch[MESSAGE <: Message[RETURN_TYPE], RETURN_TYPE](command: MESSAGE)(implicit commandClass: Manifest[MESSAGE]): Option[RETURN_TYPE] = {

    commandMap
      .get(commandClass.runtimeClass)
      .map{ handler =>
        handler
          .asInstanceOf[Handler[Command[_], _]]
          .handle(command.asInstanceOf[Command[_]])
          .asInstanceOf[RETURN_TYPE]
      }

  }

}


class QueryBus extends MessageBus
class CommandBus extends MessageBus

