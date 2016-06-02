package dal.table

import sqlest._
import domain._
import org.joda.time.LocalDate

class AlbumTable(alias: Option[String]) extends Table("album", alias) {
  val id = column[Int]("album_id")
  val name = column[String]("album_name")
  val artistId = column[Int]("artist_id")
  val releaseYear = column[Int]("release_year")
  val albumType = column[String]("album_type")
  val lastPlayed = column[Option[LocalDate]]("last_played_date")
  val purchased = column[Option[LocalDate]]("purchased_date")
  val deleted = column[Boolean]("deleted")(MappedBooleanColumnType("Y", "N"))
  val holly = column[Boolean]("holly_collection")(MappedBooleanColumnType("Y", "N"))
}

object AlbumTable extends AlbumTable(None)