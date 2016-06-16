package dal

import sqlest._
import sqlest.ast._
import domain.{Artist, Album, AlbumType}
import dal.CdExtractors._
import dal.table._
import response._

trait AlbumDal extends SqlestDb {

  def getAlbums(searchTerm: String = "", artistId: Option[Int] = None): List[Album] = {

    val wildCardSearch = ("%" + searchTerm + "%").toUpperCase

    val baseQuery = 
      select
        .from(AlbumTable)
        .innerJoin(ArtistTable)
          .on(AlbumTable.artistId === ArtistTable.id)
        .leftJoin(ArtistLinkTable)
          .on(ArtistTable.id === ArtistLinkTable.relatedArtist)
        .leftJoin(SecondArtistTable)
          .on(ArtistLinkTable.parentArtist === SecondArtistTable.id)
        .innerJoin(AlbumTypeTable)
          .on(AlbumTable.albumType === AlbumTypeTable.albumType)
        .leftJoin(AlbumSideTable)
          .on(AlbumTable.id === AlbumSideTable.albumId)
        .where((upper(ArtistTable.displayName) like wildCardSearch) ||
               (upper(AlbumTable.name) like wildCardSearch) ||
               (upper(AlbumSideTable.sideName) like wildCardSearch)) 
        .orderBy(upper(AlbumTable.name), AlbumTable.id, AlbumSideTable.sideNumber)

    val finalQuery = if (artistId.isEmpty) baseQuery
                     else {
                        baseQuery.where(AlbumTable.artistId === artistId.get.constant)
                     }

    finalQuery.extractAll(albumExtractor)
   
  }

  def getAlbum(albumId: Int): Option[Album] = {

    select
      .from(AlbumTable)
      .innerJoin(ArtistTable)
        .on(AlbumTable.artistId === ArtistTable.id)
      .leftJoin(ArtistLinkTable)
        .on(ArtistTable.id === ArtistLinkTable.relatedArtist)
      .leftJoin(SecondArtistTable)
        .on(ArtistLinkTable.parentArtist === SecondArtistTable.id)
      .innerJoin(AlbumTypeTable)
        .on(AlbumTable.albumType === AlbumTypeTable.albumType)
      .leftJoin(AlbumSideTable)
        .on(AlbumTable.id === AlbumSideTable.albumId)
      .where(ArtistTable.id === albumId)
      .extractHeadOption(albumExtractor)
   
  }

  def createAlbum(album: Album): ActionResponse[Album] = {        

    database.withTransaction { implicit transaction =>

      val insertAlbumStatement = 
        insert
          .into(AlbumTable)
          .values(AlbumTable.name -> album.name,
                  AlbumTable.artistId -> album.artist.id.get,
                  AlbumTable.releaseYear -> album.releaseYear,
                  AlbumTable.albumType -> album.albumType.code,
                  AlbumTable.lastPlayed -> album.lastPlayed,
                  AlbumTable.purchased -> album.purchased,
                  AlbumTable.deleted -> album.deleted,
                  AlbumTable.holly -> album.holly)
          .execute

      val newAlbumId: Option[Int] = 
        select(max(AlbumTable.id))
          .from(AlbumTable)
          .fetchHead

      val insertSidesStatement = 
        if (insertAlbumStatement == 1 && !album.sides.isEmpty) insertAlbumSides(album)
        else 1
       
      (insertAlbumStatement, newAlbumId, insertSidesStatement) match {
        case (1, y, z) if (y.nonEmpty && y.get > 0 && z > 0) => ActionSuccess(album.copy(id = Some(y.get))) 
        case (x, _, _) if (x != 1) => Response.fail("Failed to create new album in database")
        case (_, y, _) if (y.isEmpty || y.get <= 0) => Response.fail("Album created in database but failed to retrieve new id")
        case _ => Response.fail("Album created in database but side records not created")
      }

    }  

  } 

  def updateAlbum(album: Album): ActionResponse[Album] = {

    database.withTransaction { implicit transaction =>

      val updateStatement = 
        update(AlbumTable)
          .set(AlbumTable.name -> album.name,
                  AlbumTable.artistId -> album.artist.id.get,
                  AlbumTable.releaseYear -> album.releaseYear,
                  AlbumTable.albumType -> album.albumType.code,
                  AlbumTable.lastPlayed -> album.lastPlayed,
                  AlbumTable.purchased -> album.purchased,
                  AlbumTable.deleted -> album.deleted,
                  AlbumTable.holly -> album.holly)
          .where(AlbumTable.id === album.id.get)
          .execute

      if (updateStatement == 1) deleteAlbumSides(album.id.get)
        
      val albumSidesInsert = 
        if (!album.sides.isEmpty) insertAlbumSides(album)
        else 1
      
      
      if (updateStatement == 1 && albumSidesInsert > 0) ActionSuccess(album)
      else Response.fail("Failed to update album")
          
    }
  }

  private def deleteAlbumSides(albumId: Int)(implicit t: Transaction): Int = {
    delete
      .from(AlbumSideTable)
      .where(AlbumTable.id === albumId)
      .execute
  }

  private def insertAlbumSides(album: Album)(implicit t: Transaction): Int = {
    val baseQuery = 
    insert
    .into(AlbumSideTable)
    .values(
      AlbumSideTable.albumId -> album.id.get,
      AlbumSideTable.sideNumber -> 1,
      AlbumSideTable.sideName -> album.sides(0))

    val fullQuery: InsertValues = 
    album.sides.tail.foldLeft(baseQuery)((q: InsertValues, v: String) => 
      q.values(
        AlbumSideTable.albumId -> album.id.get,
        AlbumSideTable.sideNumber -> (album.sides.indexOf(v) + 1),
        AlbumSideTable.sideName -> v))

    fullQuery.execute
  }

  def deleteAlbum(albumId: Int): ActionResponse[Int] = {

    database.withTransaction { implicit transaction =>

      deleteAlbumSides(albumId)

      val deleteStatement = 
        delete
          .from(AlbumTable)
          .where(AlbumTable.id === albumId)
          .execute

      if (deleteStatement == 1) ActionSuccess(albumId)
      else Response.fail("Failed to delete album")      
    }
  }

  def getAlbumTypes: List[AlbumType] = {
     select
       .from(AlbumTypeTable)
       .extractAll(albumTypeExtractor) 
  }
}

