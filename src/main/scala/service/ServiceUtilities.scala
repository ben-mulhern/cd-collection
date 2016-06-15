package service

import org.http4s._
import org.http4s.dsl._
import org.http4s.headers._
import org.http4s.MediaType._
import org.json4s._
import org.json4s.native.Serialization
import org.json4s.native.Serialization.write
import org.json4s.ext._
import com.typesafe.scalalogging._

object ServiceUtilities extends LazyLogging {

  implicit val formats = Serialization.formats(NoTypeHints) ++ JodaTimeSerializers.all

  def httpJsonResponse(input: AnyRef) = {
  	val jsonResult = write(input)
    logger.info(jsonResult)
    Ok(jsonResult).withContentType(Some(`Content-Type`(`application/json`)))
        .putHeaders(Header("Access-Control-Allow-Origin", "*"))
  }

  object SearchTerm extends QueryParamDecoderMatcher[String]("search-term")

}