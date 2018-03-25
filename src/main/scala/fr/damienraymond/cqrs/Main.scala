package fr.damienraymond.cqrs

import com.google.inject.Guice
import fr.damienraymond.cqrs.core.module.CoreModule

object Main {

  def main(args: Array[String]): Unit = {
    val injector = Guice.createInjector(new CoreModule)
    //injector.getInstance(classOf[WebServer]).run()

    injector.getInstance(classOf[Tests])

  }


}

