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
import org.vertx.scala.core.http.{HttpServerRequest, RouteMatcher}
import org.vertx.scala.platform.Verticle
import service.ArtistService
import org.vertx.scala.core.json._

class CdCollectionServer extends Verticle {

  val rm = RouteMatcher()
  val artistService = new ArtistService

  rm.get("/searchArtists/:searchTerm", { req: HttpServerRequest =>
    val searchTerm = req.params().get("searchTerm").getOrElse(Nil).head

    // Got problems here - need to convert to JSON successfully - use Play?
    val artistList = Json.fromObjectString(artistService.getArtists(searchTerm).toString)
    req.response().putHeader("content-type", "application/json").end(artistList.encodePrettily())
  })

  rm.get("/artists", { req: HttpServerRequest =>
    req.response().sendFile("public/html/artists.html")
  })

  // Static resources
  rm.getWithRegEx("/public/.*", { req: HttpServerRequest =>
    req.response().sendFile(req.path().tail)
  })

  // Catch all - serve the index page
  rm.getWithRegEx(".*", { req: HttpServerRequest =>
    req.response().sendFile("public/html/index.html")
  })

  override def start() {
    vertx.createHttpServer().requestHandler(rm).listen(8080)
  }  

}