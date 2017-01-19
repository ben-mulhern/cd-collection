//enablePlugins(ScalaJSPlugin)

name := """cd-collection"""

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "com.typesafe" % "config" % "1.3.0",
  "uk.co.jhc" % "sqlest_2.11" % "0.8.5",
  "org.json4s" %% "json4s-native" % "3.5.0",
  "org.json4s" %% "json4s-ext" % "3.5.0",  
  //"com.typesafe.scala-logging" % "scala-logging_2.11" % "3.5.0",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "com.h2database" % "h2" % "1.4.191",
  "ch.qos.logback" % "logback-classic" % "1.1.3",
  //"org.scala-js" %%% "scalajs-dom" % "0.9.0",
  "org.http4s" %% "http4s-dsl" % "0.15.3a",
  "org.http4s" %% "http4s-blaze-server" % "0.15.3a",
  "org.webjars" % "angularjs" % "1.5.3"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
resolvers += "Sonatype OSS Releases"  at "http://oss.sonatype.org/content/repositories/releases/"

mainClass in reStart := Some("server.CdCollectionServer")
mainClass in Compile := Some("server.CdCollectionServer")
mainClass in assembly := Some("server.CdCollectionServer")