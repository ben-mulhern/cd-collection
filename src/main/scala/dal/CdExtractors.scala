package dal

import sqlest._
import domain._
import dal.table._

object CdExtractors {

  lazy val artistExtractor = extract[Artist](
    id = ArtistTable.id.asOption,
    displayName = ArtistTable.displayName,
    sortName = ArtistTable.sortName,
    parent = secondArtistExtractor.asOption
  )

  val SecondArtistTable = new ArtistTable(Some("SecondArtistTable"))
  lazy val secondArtistExtractor = extract[Artist](
    id = SecondArtistTable.id.asOption,
    displayName = SecondArtistTable.displayName,
    sortName = SecondArtistTable.sortName,
    parent = extractConstant(None)
  )  

}