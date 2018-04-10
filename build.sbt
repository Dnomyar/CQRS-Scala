name := """cqrs-scala"""
organization := "fr.damienraymond.cqrs"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test,
  "org.http4s" %% "http4s-dsl" % "0.18.1",
  guice,
  "net.codingwell" %% "scala-guice" % "4.1.1",
  "io.github.lukehutch" % "fast-classpath-scanner" % "2.18.1"
)


// Adds additional packages into Twirl
//TwirlKeys.templateImports += "fr.damienraymond.cqrs.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "fr.damienraymond.cqrs.binders._"
