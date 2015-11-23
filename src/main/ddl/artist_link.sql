CREATE TABLE artist_link (
      
  parent_artist INT NOT NULL,
  related_artist INT NOT NULL, 

  CONSTRAINT artist_link_0001 PRIMARY KEY(parent_artist, related_artist),
  CONSTRAINT artist_link_0002 FOREIGN KEY(parent_artist) REFERENCES artist(artist_id)
    ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT artist_link_0003 FOREIGN KEY(related_artist) REFERENCES artist(artist_id)
    ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT artist_link_0004 CHECK(parent_artist <> related_artist)

)