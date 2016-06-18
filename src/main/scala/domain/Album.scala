package domain

import org.joda.time.LocalDate

case class Album(id: Option[Int], 
	             name: String, 
	             artist: Artist, 
	             releaseYear: Int, 
	             albumType: AlbumType, 
	             lastPlayed: Option[LocalDate] = None,
	             purchased: Option[LocalDate] = None,
	             deleted: Boolean = false,
	             holly: Boolean = false,
	             sides: List[String] = Nil)