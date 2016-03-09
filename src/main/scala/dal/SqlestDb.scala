package dal

import sqlest._
import com.typesafe.config._

trait SqlestDb {

  val conf: Config = ConfigFactory.parseResources("src/main/resources/application.conf")
  val url = conf.getString("cddb.url")
  val user = conf.getString("cddb.username")
  val password = conf.getString("cddb.password")

  val dataSource = {
    val dataSource = new org.h2.jdbcx.JdbcDataSource
    dataSource.setURL(url)
    dataSource.setUser(user)
    dataSource.setPassword(password)
    dataSource
  }

  val statementBuilder = sqlest.sql.H2StatementBuilder

  implicit val database = Database.withDataSource(dataSource, statementBuilder)

}
