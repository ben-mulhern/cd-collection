package server

import com.typesafe.config.ConfigFactory

object Config {
  private val conf = ConfigFactory.parseResources("src/main/resources/application.conf")
  val port = conf.getInt("cdserver.port")
  val url = conf.getString("cddb.url")
  val user = conf.getString("cddb.username")
  val password = conf.getString("cddb.password")
}