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

CREATE TABLE dialogs
(
  id			      BIGINT 		       NOT NULL AUTO_INCREMENT,
  name                VARCHAR(200)         NOT NULL UNIQUE,
  creator_id	      BIGINT 			   NOT NULL,
  creation_timestamp  DATETIME             DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  FOREIGN KEY (creator_id)  REFERENCES users (id)
);

CREATE TABLE users_dialogs
(
  user_id			   BIGINT 		       NOT NULL,
  dialog_id			   BIGINT 		       NOT NULL,
  PRIMARY KEY (user_id, dialog_id),
  FOREIGN KEY (user_id)  REFERENCES users (id),
  FOREIGN KEY (dialog_id)  REFERENCES dialogs (id)
);

CREATE TABLE messages
(
  id			      BIGINT 		       NOT NULL AUTO_INCREMENT,
  content             TEXT                 NOT NULL,
  user_id	          BIGINT 			   NOT NULL,
  creation_timestamp  DATETIME             DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id)  REFERENCES users (id)
);