package service

import domain.Album
import dal.AlbumDal
import com.typesafe.scalalogging._
import org.http4s._
import org.http4s.dsl._
import org.http4s.headers._
import org.http4s.MediaType._
import org.http4s.server._
import org.http4s.server.blaze._
import org.json4s._
import org.json4s.native.Serialization
import org.json4s.native.Serialization.{read, write}

object AlbumService extends LazyLogging {

  implicit val formats = Serialization.formats(NoTypeHints)

  val albumDal = new AlbumDal {}

  val albumService = HttpService {


    // TODO - Need to factor in optional artist Id
    case GET -> Root / "albums" / searchTerm =>
      logger.info(s"Received request album / $searchTerm")
      val albumList: List[Album] = albumDal.getAlbums(searchTerm)
      val albumJson = write(albumList)
      Ok(albumJson).withContentType(Some(`Content-Type`(`application/json`)))
        .putHeaders(Header("Access-Control-Allow-Origin", "*"))

    case GET -> Root / "albums" =>
      logger.info(s"Received request for all albums")
      val albumList: List[Album] = albumDal.getAlbums()
      val albumJson = write(albumList)
      Ok(albumJson).withContentType(Some(`Content-Type`(`application/json`)))
        .putHeaders(Header("Access-Control-Allow-Origin", "*"))

    case req @ POST -> Root / "albums" =>
      req.decode[String] { data =>
        logger.info("Received album create request for this: " + data)
        val a: Album = read[Album](data)
        val result = albumDal.createAlbum(a)
        val jsonResult = write(result)
        logger.info(jsonResult)
        Ok(jsonResult)  
      }

    case req @ PUT -> Root / "albums" / albumId =>
      req.decode[String] { data =>
        logger.info("Received album update request for this: " + data)
        // TODO - some validation to make sure albumId from URI matches that from the body
        val a: Album = read[Album](data)
        val result = albumDal.updateAlbum(a)
        val jsonResult = write(result)
        logger.info(jsonResult)
        Ok(jsonResult)  
      }    

    case req @ DELETE -> Root / "albums" / albumId =>
      logger.info("Received album delete request for this: " + albumId)
      // TODO - validation to ensure albumId is numeric
      val deleteResponse = albumDal.deleteAlbum(albumId.toInt)
      val jsonResult = write(deleteResponse)
      logger.info(jsonResult)
      Ok(jsonResult)  
      

  }

}
