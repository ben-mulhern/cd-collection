package domain

import org.joda.time.LocalDate

case class Album(id: Option[Int], 
	             name: String, 
	             artist: Artist, 
	             releaseYear: Int, 
	             albumType: AlbumType, 
	             lastPlayed: Option[LocalDate],
	             purchased: Option[LocalDate],
	             sides: List[String])