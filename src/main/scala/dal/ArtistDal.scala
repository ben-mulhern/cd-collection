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
      //.leftJoin(SecondArtistTable)
      //  .on(ArtistLinkTable.parentArtist === SecondArtistTable.id)
      .where(upper(ArtistTable.displayName) like wildCardSearch) 
      .orderBy(ArtistTable.sortName)
      .extractAll(artistExtractor)
   
  }

  def createArtist(artist: Artist): Int = {        

    database.withTransaction { implicit transaction =>

      val insertStatement = 
        insert
          .into(ArtistTable)
          .values(ArtistTable.displayName -> artist.displayName,
                  ArtistTable.sortName -> artist.sortName)    
          .execute

      val newArtistId = 
        select(max(ArtistTable.id))
          .from(ArtistTable)
          .fetchHead

      /*if (insertStatement == 1 && artist.parent.nonEmpty)
        insert
          .into(ArtistLinkTable)
          .values(ArtistLinkTable.parentArtist -> artist.parent.get.id,
            ArtistLinkTable.relatedArtist -> newArtistId)
          .execute*/

    }

    1   

  }

  /*def updateArtist(artist: Artist): Int = {

    update(ArtistTable)
      .set(ArtistTable.displayName -> person.emailAddress)
      .where(PersonTable.personCode === person.code)
      .execute

  }*/

}

