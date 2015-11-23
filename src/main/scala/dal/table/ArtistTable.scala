package dal.table

import sqlest._
import domain._

class ArtistTable(alias: Option[String]) extends Table("artist", alias) {
  val id = column[Int]("artist_id")
  val displayName = column[String]("display_name")
  val sortName = column[String]("sort_name")
}

object ArtistTable extends ArtistTable(None)