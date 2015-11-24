name := """cd-collection"""

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "uk.co.jhc" %% "sqlest" % "0.7.2",
  "io.vertx" % "lang-scala" % "1.0.0",
  "io.vertx" % "vertx-core" % "2.1.5",
  "io.vertx" % "vertx-platform" % "2.1.6",
  "com.h2database" % "h2" % "1.4.190",
  "com.typesafe.play" % "play-json_2.11" % "2.4.4"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
resolvers += "Sonatype OSS Releases"  at "http://oss.sonatype.org/content/repositories/releases/"
