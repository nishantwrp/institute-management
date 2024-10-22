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

CREATE TABLE IF NOT EXISTS student
(
  rollNo VARCHAR(50) UNIQUE NOT NULL,
  name VARCHAR(255) NOT NULL,
  phone VARCHAR(50) NOT NULL,
  dob Date NOT NULL,
  address VARCHAR(511) NOT NULL,
  email VARCHAR(255) NOT NULL,
  bio VARCHAR(511) NULL,
  sessionId INT NOT NULL,
  username VARCHAR(255) NOT NULL,
  majorId INT NOT NULL,
  applicationId INT NOT NULL,
  PRIMARY KEY (rollNo),
  FOREIGN KEY (sessionId) REFERENCES session(id) ON DELETE CASCADE,
  FOREIGN KEY (username) REFERENCES user(username),
  FOREIGN KEY (majorId) REFERENCES major(id) ON DELETE CASCADE,
  FOREIGN KEY (applicationId) REFERENCES registration_application(id)
);

CREATE TABLE IF NOT EXISTS semester
(
  id INT NOT NULL AUTO_INCREMENT,
  fee INT NOT NULL,
  name VARCHAR(100) NOT NULL,
  sessionId INT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (sessionId) REFERENCES session(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS course_structure
(
  id INT NOT NULL AUTO_INCREMENT,
  majorId INT NOT NULL,
  semesterId INT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (majorId) REFERENCES major(id) ON DELETE CASCADE,
  FOREIGN KEY (semesterId) REFERENCES semester(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS subject_structure_relation
(
  id INT NOT NULL AUTO_INCREMENT,
  subjectId INT NOT NULL,
  courseStructureId INT NOT NULL,
  optional BOOLEAN NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (subjectId) REFERENCES subject(id) ON DELETE CASCADE,
  FOREIGN KEY (courseStructureId) REFERENCES course_structure(id)  ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS semester_registration
(
  id INT NOT NULL AUTO_INCREMENT,
  studentRollNo VARCHAR(50) NOT NULL,
  semesterId INT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (studentRollNo) REFERENCES student(rollNo) ON DELETE CASCADE,
  FOREIGN KEY (semesterId) REFERENCES semester(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS registration_subject_relation
(
  id INT NOT NULL AUTO_INCREMENT,
  registrationId INT NOT NULL,
  subjectId INT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (registrationId) REFERENCES semester_registration(id) ON DELETE CASCADE,
  FOREIGN KEY (subjectId) REFERENCES subject(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS fee_transaction
(
  id INT NOT NULL AUTO_INCREMENT,
  amount INT NOT NULL,
  date DATE NOT NULL,
  transactionId VARCHAR(100) NOT NULL,
  studentRollNo VARCHAR(50) NOT NULL,
  semesterId INT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (studentRollNo) REFERENCES student(rollNo) ON DELETE CASCADE,
  FOREIGN KEY (semesterId) REFERENCES semester(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS result
(
  id INT NOT NULL AUTO_INCREMENT,
  grade ENUM('A', 'A-', 'B', 'B-', 'C', 'C-', 'F'),
  subjectRegistrationId INT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (subjectRegistrationId) REFERENCES registration_subject_relation(id) ON DELETE CASCADE
);
