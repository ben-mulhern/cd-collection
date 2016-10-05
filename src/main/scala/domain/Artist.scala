package domain

case class Artist(id: Option[Int], displayName: String, sortName: String, parent: Option[Artist] = None)

object Artist {

  def fixSortName(a: Artist) = a.copy(sortName = a.sortName.toUpperCase())

}