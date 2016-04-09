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

    case req @ GET -> "public" /: path =>
      logger.info(s"Received static request $path")
      val resourcePath = "/public" + path.toString
      logger.info(resourcePath)
      val x = StaticFile.fromResource(resourcePath, Some(req))
      logger.info(x.toString)
      x.map(Task.now)
        .getOrElse(NotFound())

  }

}
