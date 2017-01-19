package server

import org.http4s.server.blaze.BlazeBuilder
import service.{ArtistService, AlbumService, StaticService}

object CdCollectionServer  {
  def main(args: Array[String]) {
    BlazeBuilder.bindHttp(Config.port, Config.host)
      .mountService(ArtistService.artistService)
      .mountService(AlbumService.albumService)
      .mountService(StaticService.staticService)
      .run
      .awaitShutdown()
  }
}