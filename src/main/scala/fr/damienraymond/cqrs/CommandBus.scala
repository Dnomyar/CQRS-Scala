package fr.damienraymond.cqrs

import fr.damienraymond.cqrs.example.model.Article
import fr.damienraymond.cqrs.example.{CreateArticle, CreateArticleHandler}


/**
  * WIP
  * Contains a lot of casts and mutations
  */
class CommandBus {

  private var commandMap = Map.empty[Class[_], Handler[_, _]]


  def bind[COMMAND <: Command[RETURN_TYPE], RETURN_TYPE](handler: Handler[COMMAND, RETURN_TYPE])(implicit commandClass: Manifest[COMMAND]): Unit = {
    commandMap = commandMap +
      (commandClass.runtimeClass -> handler.asInstanceOf[Handler[Command[Any], Any]])
  }


  def dispatch[COMMAND <: Command[RETURN_TYPE], RETURN_TYPE](command: COMMAND)(implicit commandClass: Manifest[COMMAND]): Option[RETURN_TYPE] = {

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
