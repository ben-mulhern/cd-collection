package dal

import sqlest._
import com.typesafe.config._
import server.Config._

trait SqlestDb {

  val dataSource = {
    val dataSource = new org.h2.jdbcx.JdbcDataSource
    dataSource.setURL(url)
    dataSource.setUser(user)
    dataSource.setPassword(password)
    dataSource
  }

  val statementBuilder = sqlest.sql.H2StatementBuilder

  implicit val database = Database.withDataSource(dataSource, statementBuilder)

  // Useful SQL BIFs
  val upper = ScalarFunction1[String, String]("upper")

}
