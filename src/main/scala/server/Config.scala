package server

import com.typesafe.config.ConfigFactory
import scala.util.Properties.envOrElse

object Config {
  private val conf = ConfigFactory.load

  val host = envOrElse("HOST", "localhost")
  val port = envOrElse("PORT", "8080").toInt
  val url = conf.getString("cddb.url")
  val user = conf.getString("cddb.username")
  val password = conf.getString("cddb.password")
}