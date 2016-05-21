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
      val resourcePath = "/public" + path.toString
      StaticFile.fromResource(resourcePath, Some(req))
        .map(Task.now)
        .getOrElse(NotFound())

    // Nicer URIs for the pages
    case req @ GET -> Root =>
      logger.info("Received page request for index page")
      val resourcePath = "/public/html/artists.html"
      StaticFile.fromResource(resourcePath, Some(req))
        .map(Task.now)
        .getOrElse(NotFound())  

    case req @ GET -> Root / "pages" / "artists" =>
      logger.info("Received page request for artist page")
      val resourcePath = "/public/html/artists.html"
      StaticFile.fromResource(resourcePath, Some(req))
        .map(Task.now)
        .getOrElse(NotFound())
 
     case req @ GET -> Root / "pages" / "albums" =>
      logger.info("Received page request for album page")
      val resourcePath = "/public/html/albums.html"
      StaticFile.fromResource(resourcePath, Some(req))
        .map(Task.now)
        .getOrElse(NotFound())     

  }

}
