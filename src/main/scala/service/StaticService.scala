package service

import com.typesafe.scalalogging._
import org.http4s._
import org.http4s.dsl._
import org.http4s.headers._
import org.http4s.MediaType._
import org.http4s.server._
import org.http4s.server.blaze._
//import scalaz.concurrent.Task

object StaticService extends LazyLogging {

  val staticService = HttpService {

    case req @ GET -> "public" /: path =>
      logger.info(s"Received static request / $path")
      //val resourcePath = "/public" + path.toString
      val resourcePath = "../../../../public/html/index.html"
      logger.info(resourcePath)
      //val testVal = StaticFile.fromResource(resourcePath, Some(req))
      //logger.info(s"Here's the retrieved value $testVal.toString")
      val testVal3 = getClass
      logger.info(testVal3.toString)
      val testVal2 = getClass.getResource(resourcePath)
      try {
        logger.info(testVal2.toString)
      } catch {
      	case e: Exception => logger.info("File not found") 
      }
      Ok("Hello!").putHeaders(`Content-Type`(`text/plain`))
      //testVal.map(Task.now).getOrElse(NotFound())

  }

}
