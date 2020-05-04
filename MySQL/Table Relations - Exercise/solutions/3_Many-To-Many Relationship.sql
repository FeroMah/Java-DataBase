USE `students relationship`;
CREATE TABLE exams
(
    exam_id INT         NOT NULL AUTO_INCREMENT UNIQUE,
    name    VARCHAR(50) NOT NULL
) AUTO_INCREMENT = 101;
CREATE TABLE students
(
    student_id INT         NOT NULL AUTO_INCREMENT UNIQUE,
    name       VARCHAR(50) NOT NULL
);
CREATE TABLE students_exams
(
    student_id INT NOT NULL,
    exam_id    INT NOT NULL
);


ALTER TABLE exams
    ADD CONSTRAINT pk_exams PRIMARY KEY (exam_id);
ALTER TABLE students
    ADD CONSTRAINT pk_students PRIMARY KEY (student_id);
ALTER TABLE students_exams
    ADD CONSTRAINT pk_students_exams PRIMARY KEY (student_id, exam_id),
    ADD CONSTRAINT fk_student FOREIGN KEY (student_id) REFERENCES students (student_id),
    ADD CONSTRAINT fk_exam FOREIGN KEY (exam_id) REFERENCES exams (exam_id);

INSERT INTO exams
VALUES (101, 'Spring MVC'),
       (102, 'Neo4j'),
       (103, 'Oracle 11g');
INSERT INTO students
VALUES (1, 'Mila'),
       (2, 'Toni'),
       (3, 'Ron');
INSERT INTO students_exams
VALUES (1, 101),
       (1, 102),
       (2, 101),
       (3, 103),
       (2, 102),
       (2, 103);

SELECT s.name AS studnet_name, e.name AS exam_name
FROM students_exams AS se,
     students AS s,
     exams AS e
WHERE se.student_id = s.student_id
  AND se.exam_id = e.exam_id;
