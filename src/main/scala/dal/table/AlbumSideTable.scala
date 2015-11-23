package dal.table

import sqlest._
import domain._

class AlbumSideTable(alias: Option[String]) extends Table("album_side", alias) {
  val albumId = column[Int]("album_id")
  val sideNumber = column[Int]("side_number")
  val sideName = column[String]("side_name")
}

object AlbumSideTable extends AlbumSideTable(None)