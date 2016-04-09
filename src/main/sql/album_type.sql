CREATE TABLE album_type (
      
  album_type CHAR(3) NOT NULL,
  description CHAR(100) NOT NULL,
                            
  CONSTRAINT album_type_0001 PRIMARY KEY(album_type),     
  CONSTRAINT album_type_0002 CHECK(album_type <> ''),
  CONSTRAINT album_type_0003 CHECK(description <> '')

)