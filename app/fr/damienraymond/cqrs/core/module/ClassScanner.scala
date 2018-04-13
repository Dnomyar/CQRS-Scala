package fr.damienraymond.cqrs.core.module

import java.lang.reflect.Modifier

import org.reflections.Reflections

import scala.collection.JavaConverters._


object ClassScanner {

  def scan[T](packageScope: String, motherClass: Class[T])(subClassHandler: Class[_ <: T] => Unit): Unit = {
    new Reflections(packageScope).getSubTypesOf(motherClass).asScala
      .filterNot(c => Modifier.isAbstract(c.getModifiers))
      .foreach(subClassHandler)
  }

}
