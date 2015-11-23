CREATE TABLE artist (
      
  artist_id INT NOT NULL GENERATED ALWAYS AS IDENTITY,
  display_name CHAR(500) NOT NULL UNIQUE,
	sort_name CHAR(500) NOT NULL,
                            
  CONSTRAINT artist_0001 PRIMARY KEY(artist_id),     
  CONSTRAINT artist_0002 CHECK(artist_id > 0),
  CONSTRAINT artist_0003 CHECK(display_name <> ''),
	CONSTRAINT artist_0004 CHECK(sort_name <> '')

)