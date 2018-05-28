package fr.damienraymond.cqrs.core

import play.api

trait Logger {
  protected lazy val logger = api.Logger(this.getClass)
}
