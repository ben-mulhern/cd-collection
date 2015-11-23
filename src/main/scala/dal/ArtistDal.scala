package dal

import sqlest._
import domain.Artist
import dal.CdExtractors._
import dal.table._

trait ArtistDal extends SqlestDb {

  def getArtists(searchTerm: String): List[Artist] = {

    val wildCardSearch = ("%" + searchTerm + "%").toUpperCase

    select
      .from(ArtistTable)
      .leftJoin(ArtistLinkTable)
        .on(ArtistTable.id === ArtistLinkTable.relatedArtist)
      .leftJoin(SecondArtistTable)
        .on(ArtistLinkTable.parentArtist === SecondArtistTable.id)
      .where(upper(ArtistTable.displayName) like wildCardSearch) 
      .extractAll(artistExtractor)
   
  }

}

