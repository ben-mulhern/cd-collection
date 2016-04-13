package dal

import sqlest._
import domain.Artist
import dal.CdExtractors._
import dal.table._
import response._

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
      .orderBy(ArtistTable.sortName)
      .extractAll(artistExtractor)
   
  }

  def createArtist(artist: Artist): ActionResponse[Artist] = {        

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

      if (insertStatement == 1 && artist.parent.nonEmpty)
        insert
          .into(ArtistLinkTable)
          .values(ArtistLinkTable.parentArtist -> artist.parent.get.id,
            ArtistLinkTable.relatedArtist -> newArtistId)
          .execute

      ActionSuccess(artist.copy(id = Some(newArtistId.getOrElse(0)))) 

    }  

  }

  def updateArtist(artist: Artist) = {

    database.withTransaction { implicit transaction =>

      update(ArtistTable)
        .set(ArtistTable.displayName -> artist.displayName)
        .where(ArtistTable.id === artist.id.get)
        .execute
    }
  }

  def deleteArtist(artist: Artist) = {

    database.withTransaction { implicit transaction =>

      delete
        .from(ArtistTable)
        .where(ArtistTable.id === artist.id.get)
        .execute
    }
  }

}

