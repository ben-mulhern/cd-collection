/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package server

import org.vertx.scala.core._
import org.vertx.scala.core.http._
import org.vertx.scala.platform.Verticle
import service.JsonConversions._
import service.ArtistService
import play.api.libs.json._
import org.vertx.scala.core.buffer._
import domain.Artist
import com.typesafe.config._


class CdCollectionServer extends Verticle {

  val rm = RouteMatcher()
  val artistService = new ArtistService
  val conf: Config = ConfigFactory.parseResources("src/main/resources/application.conf")
  val port = conf.getInt("cdserver.port")

  // ===============================================================
  // Pages
  // ===============================================================

  // Home page
  rm.getWithRegEx("[/]{0,1}", { req: HttpServerRequest =>
    req.response().sendFile("public/html/index.html")
  })

  rm.get("/artists", { req: HttpServerRequest =>
    req.response().sendFile("public/html/artists.html")
  })

  // ===============================================================
  // Services
  // ===============================================================  

  rm.getWithRegEx("/searchArtists/.*", { req: HttpServerRequest =>
    val searchTerm = req.path.stripPrefix("/searchArtists/")
    val artistList = artistService.getArtists(searchTerm)
    sendJsonResponse(req, artistList)
  }) 

  rm.post("/createArtist/", { req: HttpServerRequest =>
    req.bodyHandler { data: Buffer => 
      val raw = data.getString(0, data.length())
      println(raw)
      val rawJson = Json.parse(raw)
      println(Json.prettyPrint(rawJson))
      rawJson.validate[Artist] match {
        case s: JsSuccess[Artist] =>  {
          val artist = s.get
          val res = artistService.createArtist(artist)
          req.response.end("Artist created?: " + res)
        }
        case e => req.response.end("JSON error" + e.toString)
      }
    }  
    
  })

  // ===============================================================
  // Static resources
  // ===============================================================

  rm.getWithRegEx("/public/.*", { req: HttpServerRequest =>
    req.response().sendFile(req.path().tail)
  })

  // ===============================================================
  // Anything else - 404
  // ===============================================================

  rm.getWithRegEx(".*", { req: HttpServerRequest =>
    req.response().setStatusCode(404).sendFile("public/html/error.html")
  })

  override def start() {

    vertx.createHttpServer().requestHandler(rm).listen(port)
  }  

  def sendJsonResponse(req: HttpServerRequest, responseData: JsValue) = {
    req.response().putHeader("content-type", "application/json").end(responseData.toString)
  } 

}