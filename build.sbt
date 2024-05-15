name := """bootsverleih"""
organization := "com.informaticon"

version := "1.0-SNAPSHOT"

lazy val myProject = (project in file("."))
  .enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.13.11"
libraryDependencies ++= Seq(guice, javaJdbc)
libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.33"

WebpackKeys.webpackConfigs := Seq(
  baseDirectory.value / "webpack.config.js"
)