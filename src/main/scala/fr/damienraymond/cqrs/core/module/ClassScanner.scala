package fr.damienraymond.cqrs.core.module

import com.google.inject.Binder
import com.google.inject.multibindings.Multibinder
import fr.damienraymond.cqrs.core.Command
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner
import io.github.lukehutch.fastclasspathscanner.matchprocessor.ImplementingClassMatchProcessor

object ClassScanner {

  def scan[T](packageScope: String, motherClass: Class[T])(subClassHandler: Class[_ <: T] => Unit): Unit = {
    new FastClasspathScanner(packageScope)
      .matchClassesImplementing(motherClass, new ImplementingClassMatchProcessor[T] {
        override def processMatch(implementingClass: Class[_ <: T]) = subClassHandler(implementingClass)
      }).scan()
  }

}
