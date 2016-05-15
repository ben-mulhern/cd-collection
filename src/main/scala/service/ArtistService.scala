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

  val artist = HttpService {

    case GET -> Root / "artist" / searchTerm =>
      logger.info(s"Received request artist / $searchTerm")
      val artistList: List[Artist] = artistDal.getArtists(searchTerm)
      val artistJson = write(artistList)
      Ok(artistJson).withContentType(Some(`Content-Type`(`application/json`)))
        .putHeaders(Header("Access-Control-Allow-Origin", "*"))


    case req @ POST -> Root / "artist" / "create" =>
      req.decode[String] { data =>
        logger.info("Received artist create request for this: " + data)
        val a: Artist = read[Artist](data)
        val result = artistDal.createArtist(a)
        val jsonResult = write(result)
        logger.info(jsonResult)
        Ok(jsonResult)  
      }

    case req @ PUT -> Root / "artist" / "update" =>
      req.decode[String] { data =>
        logger.info("Received artist update request for this: " + data)
        val a: Artist = read[Artist](data)
        val result = artistDal.updateArtist(a)
        val jsonResult = write(result)
        logger.info(jsonResult)
        Ok(jsonResult)  
      }    

  }

}
