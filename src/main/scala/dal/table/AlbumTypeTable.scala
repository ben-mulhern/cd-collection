package dal.table

import sqlest._

class AlbumTypeTable(alias: Option[String]) extends Table("album_type", alias) {
  val albumType = column[String]("album_type")
  val description = column[String]("description")
}

object AlbumTypeTable extends AlbumTypeTable(None)