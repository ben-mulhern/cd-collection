CREATE TABLE album_side (
      
  album_id INT NOT NULL,
  side_number INT NOT NULL,
  side_name CHAR(500) NOT NULL, 

  CONSTRAINT album_side_0001 PRIMARY KEY(album_id, side_number),
  CONSTRAINT album_side_0002 FOREIGN KEY(album_id) REFERENCES album(album_id)
    ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT album_side_0003 CHECK(side_number > 0),
  CONSTRAINT album_side_0004 CHECK(side_name <> '')  

)