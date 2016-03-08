package service

import domain._
import play.api.libs.json._

object JsonConversions {

  implicit val artistFormat = Json.format[Artist]
  implicit def artistJson(a: Artist) = Json.toJson(a)
  implicit def artistListJson(al: List[Artist]) = Json.toJson(al)

}