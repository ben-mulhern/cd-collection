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

      val insertParentRelationship = 
        if (insertStatement == 1 && artist.parent.nonEmpty)
          insert
            .into(ArtistLinkTable)
            .values(ArtistLinkTable.parentArtist -> artist.parent.get.id,
              ArtistLinkTable.relatedArtist -> newArtistId)
            .execute
        else 1
        
      (insertStatement, newArtistId, insertParentRelationship) match {
        case (1, y, 1) if (y.nonEmpty && y.get > 0) => ActionSuccess(artist.copy(id = Some(y.get))) 
        case (x, _, _) if (x != 1) => Response.fail("Failed to create new artist in database")
        case (_, y, _) if (y.isEmpty || y.get <= 0) => Response.fail("Artist created in database but failed to retrieve new id")
        case _ => Response.fail("Artist created in database but parent relationship not created")
      }

    }  

  }

  def updateArtist(artist: Artist): ActionResponse[Artist] = {

    database.withTransaction { implicit transaction =>

      val updateStatement = 
        update(ArtistTable)
          .set(ArtistTable.displayName -> artist.displayName)
          .where(ArtistTable.id === artist.id.get)
          .execute

      if (updateStatement == 1) ActionSuccess(artist)
      else Response.fail("Failed to update artist")
          
    }
  }

  def deleteArtist(artist: Artist): ActionResponse[Artist] = {

    database.withTransaction { implicit transaction =>

      val deleteStatement = 
        delete
          .from(ArtistTable)
          .where(ArtistTable.id === artist.id.get)
          .execute

      if (deleteStatement == 1) ActionSuccess(artist)
      else Response.fail("Failed to delete artist")      
    }
  }

}

