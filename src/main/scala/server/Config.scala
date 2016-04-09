package server

import com.typesafe.config.ConfigFactory

object Config {
  private val conf = ConfigFactory.load
  val host = conf.getString("cdserver.host")
  val port = conf.getInt("cdserver.port")
  val url = conf.getString("cddb.url")
  val user = conf.getString("cddb.username")
  val password = conf.getString("cddb.password")
}