package service

import domain.Artist
import dal.ArtistDal

class ArtistService {

  val artistDal = new ArtistDal {}

  def getArtists(searchTerm: String): List[Artist] = {
  	artistDal.getArtists(searchTerm)
  }

}
