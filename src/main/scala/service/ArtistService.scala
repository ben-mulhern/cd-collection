package service

import domain.Artist
import dal.ArtistDal
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

object ArtistService extends LazyLogging {

  implicit val formats = Serialization.formats(NoTypeHints)

  val artistDal = new ArtistDal {}

  val artistService = HttpService {

    case GET -> Root / "artists" / searchTerm =>
      logger.info(s"Received request artist / $searchTerm")
      val artistList: List[Artist] = artistDal.getArtists(searchTerm)
      val artistJson = write(artistList)
      Ok(artistJson).withContentType(Some(`Content-Type`(`application/json`)))
        .putHeaders(Header("Access-Control-Allow-Origin", "*"))

    case GET -> Root / "artists" =>
      logger.info(s"Received request for all artists")
      val artistList: List[Artist] = artistDal.getArtists()
      val artistJson = write(artistList)
      Ok(artistJson).withContentType(Some(`Content-Type`(`application/json`)))
        .putHeaders(Header("Access-Control-Allow-Origin", "*"))

    case req @ POST -> Root / "artists" =>
      req.decode[String] { data =>
        logger.info("Received artist create request for this: " + data)
        val a: Artist = read[Artist](data)
        val result = artistDal.createArtist(a)
        val jsonResult = write(result)
        logger.info(jsonResult)
        Ok(jsonResult)  
      }

    case req @ PUT -> Root / "artists" / artistId =>
      req.decode[String] { data =>
        logger.info("Received artist update request for this: " + data)
        // TODO - some validation to make sure artistId from URI matches that from the body
        val a: Artist = read[Artist](data)
        val result = artistDal.updateArtist(a)
        val jsonResult = write(result)
        logger.info(jsonResult)
        Ok(jsonResult)  
      }    

    case req @ DELETE -> Root / "artists" / artistId =>
      logger.info("Received artist delete request for this: " + artistId)
      // TODO - validation to ensure artistId is numeric
      val deleteResponse = artistDal.deleteArtist(artistId.toInt)
      val jsonResult = write(deleteResponse)
      logger.info(jsonResult)
      Ok(jsonResult)  
      

  }

}
