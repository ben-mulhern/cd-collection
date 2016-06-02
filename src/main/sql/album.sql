CREATE TABLE album (
      
  album_id INT NOT NULL GENERATED ALWAYS AS IDENTITY,
  album_name CHAR(500) NOT NULL,
  artist_id INT NOT NULL,
  release_year INT NOT NULL,
  album_type CHAR(3) NOT NULL,
  last_played_date DATE,
  purchased_date DATE,
  deleted CHAR(1) NOT NULL,
  holly_collection CHAR(1) NOT NULL,
                            
  CONSTRAINT album_0001 PRIMARY KEY(album_id),     
  CONSTRAINT album_0002 CHECK(album_name <> ''),
  CONSTRAINT album_0003 FOREIGN KEY(artist_id) REFERENCES artist(artist_id)
    ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT album_0004 CHECK(release_year > 0),  
  CONSTRAINT album_0005 FOREIGN KEY(album_type) REFERENCES album_type(album_type)
    ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT album_0006 CHECK(deleted IN ('Y', 'N')),
  CONSTRAINT album_0007 CHECK(holly_collection IN ('Y', 'N'))

)