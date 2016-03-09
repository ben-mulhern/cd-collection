package service

import domain._
import play.api.libs.json._
import play.api.libs.functional.syntax._

object JsonConversions {

  implicit val artistFormat = Json.format[Artist]
  implicit def artistJson(a: Artist) = Json.toJson(a)
  implicit def artistListJson(al: List[Artist]) = Json.toJson(al)

  implicit val artistReads: Reads[Artist] = (
    (JsPath \ "id").readNullable[Int] and
    (JsPath \ "displayName").read[String] and
    (JsPath \ "sortName").read[String] and
    (JsPath \ "parent").readNullable[Artist]
  )(Artist.apply _)

}