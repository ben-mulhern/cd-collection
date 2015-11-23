package dal

import sqlest._

trait SqlestDb {

  val dataSource = {
    val dataSource = new org.h2.jdbcx.JdbcDataSource
    dataSource.setURL("jdbc:h2:file:./data/cd-collection;IFEXISTS=TRUE;FILE_LOCK=NO")
    dataSource.setUser("bmo")
    dataSource.setPassword("bmo")
    dataSource
  }

  val statementBuilder = sqlest.sql.H2StatementBuilder

  implicit val database = Database.withDataSource(dataSource, statementBuilder)

}
