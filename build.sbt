ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.3"

lazy val root = (project in file("."))
  .settings(
    name := "poker",
    libraryDependencies += "org.typelevel" %% "cats-effect" % "3.6-0142603",
    libraryDependencies += "com.typesafe.slick" %% "slick" % "3.5.0",
    libraryDependencies += "org.postgresql" % "postgresql" % "42.7.3",
    libraryDependencies += "com.typesafe.slick" %% "slick-hikaricp" % "3.5.1",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.18" % Test
  )
