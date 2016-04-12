package domain

case class Artist(id: Option[Int], displayName: String, sortName: String, parent: Option[Artist] = None)