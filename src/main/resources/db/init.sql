CREATE TABLE users
(
  id			   BIGINT 		           NOT NULL AUTO_INCREMENT,
  login            VARCHAR(20)             NOT NULL UNIQUE,
  password         VARCHAR(200)             NOT NULL,
  role             VARCHAR(20)             NOT NULL,
  first_name       VARCHAR(50)             NOT NULL,
  last_name        VARCHAR(50)             NOT NULL,
  age			   INTEGER,
  gender           VARCHAR(6),
  interests        VARCHAR(1000),
  city 			   VARCHAR(50),
  PRIMARY KEY (id)
);

CREATE TABLE friends
(
  id1			   BIGINT 			    NOT NULL,
  id2			   BIGINT 			    NOT NULL,
  PRIMARY KEY (id1,id2),
  FOREIGN KEY (id1)  REFERENCES users (id),
  FOREIGN KEY (id2)  REFERENCES users (id)
);
