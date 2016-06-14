package service

import com.typesafe.scalalogging._
import org.http4s._
import org.http4s.dsl._
import org.http4s.headers._
import org.http4s.MediaType._
import org.http4s.server._
import org.http4s.server.blaze._
import scalaz.concurrent.Task

object StaticService extends LazyLogging {

  val staticService = HttpService {

    // Generic resources
    case req @ GET -> "public" /: path =>
      logger.info(s"Received static request $path")
      getStaticFile(req, "/public" + path.toString)

    // Nicer URIs for the pages
    case req @ GET -> Root =>
      logger.info("Received page request for index page (albums)")
      getStaticFile(req, "/public/html/albums.html")

    case req @ GET -> Root / "pages" / "artists" =>
      logger.info("Received page request for artist page")
      getStaticFile(req, "/public/html/artists.html")
 
     case req @ GET -> Root / "pages" / "albums" =>
      logger.info("Received page request for album page")
      getStaticFile(req, "/public/html/albums.html")

  }

  def getStaticFile(req: Request, path: String) = {
    StaticFile.fromResource(path, Some(req))
      .map(Task.now)
      .getOrElse(NotFound())     
  }

}
