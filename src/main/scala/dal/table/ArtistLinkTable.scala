package dal.table

import sqlest._
import domain._

class ArtistLinkTable(alias: Option[String]) extends Table("artist_link", alias) {
  val parentArtist = column[Int]("parent_artist")
  val relatedArtist = column[Int]("related_artist")
}

object ArtistLinkTable extends ArtistLinkTable(None)