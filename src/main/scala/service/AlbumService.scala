package service

import domain.Album
import dal.AlbumDal
import com.typesafe.scalalogging._
import org.http4s._
import org.http4s.dsl._
import org.http4s.server._
import org.http4s.server.blaze._
import org.json4s.native.Serialization.read
import ServiceUtilities._

object AlbumService extends LazyLogging {

  val albumDal = new AlbumDal {}

  val albumService = HttpService {

    case GET -> Root / "albums" :? SearchTerm(searchTerm) =>
      logger.info(s"Received request for albums with search term $searchTerm")
      httpJsonResponse(albumDal.getAlbums(searchTerm))

    case GET -> Root / "albums" / albumId =>
      logger.info(s"Received request for album with id $albumId")
      httpJsonResponse(albumDal.getAlbum(albumId.toInt))

    case GET -> Root / "artists" / artistId / "albums" :? SearchTerm(searchTerm) =>
      logger.info(s"Received request for albums by artist $artistId with search term $searchTerm")
      httpJsonResponse(albumDal.getAlbums(searchTerm, Some(artistId.toInt))) 

    // Todo: protect against non-integer artist id
    case GET -> Root / "artists" / artistId / "albums" =>
      logger.info(s"Received request for all albums by artist $artistId")
      httpJsonResponse(albumDal.getAlbums(artistId = Some(artistId.toInt)))      

    case GET -> Root / "albums" =>
      logger.info(s"Received request for all albums")
      httpJsonResponse(albumDal.getAlbums())

    case req @ POST -> Root / "albums" =>
      req.decode[String] { data =>
        logger.info("Received album create request for this: " + data)
        val a: Album = read[Album](data)
        httpJsonResponse(albumDal.createAlbum(a))
      }

    case req @ PUT -> Root / "albums" / albumId =>
      req.decode[String] { data =>
        logger.info("Received album update request for this: " + data)
        // TODO - some validation to make sure albumId from URI matches that from the body
        val a: Album = read[Album](data)
        httpJsonResponse(albumDal.updateAlbum(a))
      }    

    case req @ DELETE -> Root / "albums" / albumId =>
      logger.info("Received album delete request for this: " + albumId)
      // TODO - validation to ensure albumId is numeric
      httpJsonResponse(albumDal.deleteAlbum(albumId.toInt))
      
    case req @ GET -> Root / "album-types" =>
      logger.info("Received request for list of album types")
      httpJsonResponse(albumDal.getAlbumTypes)   

    case req @ GET -> Root / "albums" / "random" / c =>
      logger.info(s"Received request for $c random unplayed albums")
      httpJsonResponse(albumDal.getRandomUnplayedAlbums(c.toInt))

  }

}
