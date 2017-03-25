-- INIT USER AND AUTHORITY TABLE --

CREATE TABLE faculty (
  id    INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(100) NOT NULL
);

CREATE TABLE user (
  id            INTEGER      NOT NULL AUTO_INCREMENT PRIMARY KEY,
  username      VARCHAR(100) NOT NULL,
  password_hash VARCHAR(60)  NOT NULL,
  first_name    VARCHAR(50)  NOT NULL,
  last_name     VARCHAR(50)  NOT NULL,
  email         VARCHAR(100) NOT NULL,
  faculty_id    INT(20),
  created_date  DATETIME     NOT NULL,
  FOREIGN KEY (faculty_id) REFERENCES `faculty` (id)
);

CREATE TABLE authority (
  name         VARCHAR(50) NOT NULL PRIMARY KEY,
  created_date DATETIME    NOT NULL
);

CREATE TABLE user_authority (
  id             INTEGER     NOT NULL AUTO_INCREMENT PRIMARY KEY,
  user_id        INTEGER     NOT NULL,
  authority_name VARCHAR(50) NOT NULL,
  FOREIGN KEY (user_id) REFERENCES user (id),
  FOREIGN KEY (authority_name) REFERENCES authority (name)
);
-- INIT CLAIM, COURSE, ASSESSMENT TABLE --
CREATE TABLE course (
  code       VARCHAR(50)  NOT NULL,
  title      VARCHAR(100) NOT NULL,
  PRIMARY KEY (code),
  faculty_id INT(20),
  FOREIGN KEY (faculty_id) REFERENCES `faculty` (id)
);
CREATE TABLE assessment (
  crn         VARCHAR(50)  NOT NULL,
  course_code VARCHAR(50),
  title       VARCHAR(100) NOT NULL,
  PRIMARY KEY (crn),
  FOREIGN KEY (course_code) REFERENCES course (code)
);


CREATE TABLE claim (
  id             INT NOT NULL AUTO_INCREMENT,
  user_id        INT,
  evidence       TEXT,
  content        TEXT,
  decision       TEXT,
  created_time   DATETIME,
  processed_time DATETIME,
  closed_date    DATETIME,
  status         INT,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES `user` (id)
);

CREATE TABLE circumstance (
  id    INT NOT NULL AUTO_INCREMENT,
  title TEXT,
  PRIMARY KEY (id)
);

CREATE TABLE assessment_claim (
  claim_id       INT         NOT NULL,
  assessment_crn VARCHAR(50) NOT NULL,
  solution1      INT,
  solution2      INT,
  PRIMARY KEY (claim_id, assessment_crn),
  FOREIGN KEY (claim_id) REFERENCES claim (id),
  FOREIGN KEY (assessment_crn) REFERENCES assessment (crn)
);

CREATE TABLE claim_circumstance (
  claim_id           INT NOT NULL,
  circumstance_id    INT NOT NULL,
  other_circumstance TEXT,
  PRIMARY KEY (claim_id, circumstance_id),
  FOREIGN KEY (claim_id) REFERENCES claim (id),
  FOREIGN KEY (circumstance_id) REFERENCES circumstance (id)
);

-- INSERT SAMPLE DATA FOR USER--

INSERT INTO authority (name, created_date) VALUES
  ('ROLE_USER', NOW()), ('ROLE_STUDENT', NOW()), ('ROLE_MANAGER', NOW()), ('ROLE_COORDINATOR', NOW()),
  ('ROLE_ADMIN', NOW());


INSERT INTO faculty (title) VALUES
  ('faculty1'),
  ('faculty2'),
  ('faculty3'),
  ('faculty4'),
  ('faculty5');


INSERT INTO user (username, password_hash, first_name, last_name, email, created_date) VALUES
  ('user', '$2a$06$7oA08ApI.X1xU0H5zkmpbutG4Uawv9mMH2qFqzpqGqr3EUJvPnKtu', 'fuser', 'luser', 'user@gmail.com', NOW()),
  ('student', '$2a$06$FBK.uNoEF.5H1W2.pE3MB.rrr1JsNDuH3fZJr1RS0esFKzYWAn/3K', 'fstudent', 'lstudent',
   'student@gmail.com', NOW()),
  ('manager', '$2a$06$g5UumdjnCb5vRLy6FAice.mzSfkrU2ZPvGIh63t0nUzGnT9e/nZuu', 'fmanager', 'lmanager',
   'anhndgc00893@fpt.edu.vn', NOW()),
  ('coordinator', '$2a$06$8iHCr9.rIYwzgkblpWoIiO7Bnu38QklQB6tleSlrwbLrvI5PEAojm', 'fcoordinator', 'lcoordinator',
   'anhndgc00893@fpt.edu.vn', NOW()),
  ('admin', '$2a$06$P4rfOGUCvzL.2OzecFrar.oWmuIjozP5oogg3CT4GGw3oRBenbEVa', 'fadmin', 'ladmin',
   'anhndgc00893@fpt.edu.vn',
   NOW()),
  ('student1', '$2a$06$FBK.uNoEF.5H1W2.pE3MB.rrr1JsNDuH3fZJr1RS0esFKzYWAn/3K', 'fstudent1', 'lstudent1',
   'sondcgc00681@fpt.edu.vn', NOW()),
  ('student2', '$2a$06$FBK.uNoEF.5H1W2.pE3MB.rrr1JsNDuH3fZJr1RS0esFKzYWAn/3K', 'fstudent2', 'lstudent2',
   'ainvgc00704@fpt.edu.vn', NOW()),
  ('student3', '$2a$06$FBK.uNoEF.5H1W2.pE3MB.rrr1JsNDuH3fZJr1RS0esFKzYWAn/3K', 'fstudent3', 'lstudent3',
   'namnhgc00683@fpt.edu.vn', NOW()),
  ('student4', '$2a$06$FBK.uNoEF.5H1W2.pE3MB.rrr1JsNDuH3fZJr1RS0esFKzYWAn/3K', 'fstuden4', 'lstudent4',
   'anhndgc00893@fpt.edu.vn', NOW()),
  ('student5', '$2a$06$FBK.uNoEF.5H1W2.pE3MB.rrr1JsNDuH3fZJr1RS0esFKzYWAn/3K', 'fstudent5', 'lstudent5',
   'anhndgc00893@fpt.edu.vn', NOW());
INSERT INTO user_authority (user_id, authority_name) VALUES
  (1, 'ROLE_USER'),
  (2, 'ROLE_STUDENT'), (2, 'ROLE_USER'),
  (3, 'ROLE_MANAGER'), (3, 'ROLE_USER'), (3, 'ROLE_STUDENT'),
  (4, 'ROLE_COORDINATOR'), (4, 'ROLE_USER'), (4, 'ROLE_STUDENT'), (4, 'ROLE_MANAGER'),
  (5, 'ROLE_ADMIN'), (5, 'ROLE_USER'), (5, 'ROLE_STUDENT'), (5, 'ROLE_MANAGER'), (5, 'ROLE_COORDINATOR');

-- INSERT SAMPLE DATA FOR CLAIMS --
INSERT INTO claim (evidence, content, created_time, processed_time, status, user_id, closed_date) VALUES
  ('Evidence1', 'Content1', NOW(), NOW(), 1, 2,NOW()),
  ('Evidence2', 'Content2', NOW(), NOW(), 1, 2,'2017-05-05'),
  ('Evidence3', 'Content3', NOW(), NOW(), 1, 2,NOW());


INSERT INTO circumstance (title)
VALUES ('Accident'), ('Bereavement'), ('Harassment or Assault'), ('Jury Service'), ('Medical'),
  ('Organisational maladministration'), ('Unexpected personal or family difficulties'),
  ('Work (part-time and placement studends only)'), ('Other');

INSERT INTO course (code, title,faculty_id) VALUES
  ('COMP-1108', 'Project',1), ('COMP-1639', 'Database Engineering',2),
  ('COMP-1640', 'Enterprise Web Software Dev',2), ('COMP-1661', 'Application Dev for Mobile Dev',1),
  ('COMP-1649', 'Interaction Design',3),
  ('COMP-1714', 'Software Engineeing Mgmnt',3), ('COMP-1648', 'Dev Framework & Methods',1);

INSERT INTO assessment (crn, course_code, title) VALUES ('23718', 'COMP-1108', 'COMP 1108 Demonstration'),
  ('23717', 'COMP-1108', 'COMP 1108 Final Report'), ('24761', 'COMP-1639', 'COMP 1639 Exam'),
  ('24760', 'COMP-1639', 'COMP 1639 Practical Coursework'), ('24767', 'COMP-1640', 'COMP 1640 Coureswork'),
  ('25042', 'COMP-1648', 'COMP 1648 Coursework'), ('25045', 'COMP-1649', 'COMP 1649 Coursework'),
  ('25066', 'COMP-1661', 'COMP 1661 Coursework'), ('25067', 'COMP-1661', 'COMP 1661 Logbook'),
  ('25391', 'COMP-1714', 'COMP 1714 Coursework'), ('25392', 'COMP-1714', 'COMP 1714 Exam');