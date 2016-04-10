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

object ArtistService extends LazyLogging {

  val artistDal = new ArtistDal {}

  val artist = HttpService {

    case GET -> Root / "artist" / searchTerm =>
      logger.info(s"Received request artist / $searchTerm")
      val artistList: List[Artist] = artistDal.getArtists(searchTerm)
      val artistJson = upickle.default.write(artistList)
      Ok(artistJson).withContentType(Some(`Content-Type`(`application/json`)))
        .putHeaders(Header("Access-Control-Allow-Origin", "*"))


    case req @ POST -> Root / "artist" / "create" =>
      req.decode[String] { data =>
        logger.info("Received artist create request for this: " + data)
        val a: Artist = upickle.default.read[Artist](data)
        val res: Int = artistDal.createArtist(a)
        Ok("That worked!")  
      }

  }

}
