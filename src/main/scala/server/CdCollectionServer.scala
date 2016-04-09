package server

import org.http4s.server.blaze.BlazeBuilder
import service.{ArtistService, StaticService}

object CdCollectionServer extends App {
  BlazeBuilder.bindHttp(Config.port, Config.host)
    .mountService(ArtistService.artist)
    .mountService(StaticService.staticService)
    .run
    .awaitShutdown()
}