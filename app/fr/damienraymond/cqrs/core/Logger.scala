package fr.damienraymond.cqrs.core

import play.api

trait Logger {
  lazy val logger = api.Logger(this.getClass)
}
