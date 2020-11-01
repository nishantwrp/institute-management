CREATE TABLE IF NOT EXISTS user
(
  username VARCHAR(255) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  isAdmin BOOLEAN,
  PRIMARY KEY (username)
);

CREATE TABLE IF NOT EXISTS session
(
  id INT NOT NULL AUTO_INCREMENT,
  startYear INT NOT NULL,
  isComplete BOOLEAN,
  registrationsOpen BOOLEAN,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS faculty
(
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  bio VARCHAR(511) NOT NULL,
  address VARCHAR(511) NOT NULL,
  phone VARCHAR(40) NOT NULL,
  dob DATE NOT NULL,
  username VARCHAR(255) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (username) REFERENCES user(username)
);

CREATE TABLE IF NOT EXISTS subject
(
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  code VARCHAR(30) NOT NULL,
  facultyId INT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (facultyId) REFERENCES faculty(id)
);

CREATE TABLE IF NOT EXISTS major
(
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS payout
(
  id INT NOT NULL AUTO_INCREMENT,
  notes VARCHAR(511) NOT NULL,
  amount INT NOT NULL,
  date DATE NOT NULL,
  transactionId VARCHAR(100) NOT NULL,
  facultyId INT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (facultyId) REFERENCES faculty(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS registration_application
(
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  phone VARCHAR(50) NOT NULL,
  date Date NOT NULL,
  dob Date NOT NULL,
  about TEXT NOT NULL,
  address VARCHAR(511) NOT NULL,
  email VARCHAR(255) NOT NULL,
  interestedMajors VARCHAR(511) NOT NULL,
  sessionId INT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (sessionId) REFERENCES session(id) ON DELETE CASCADE
);
