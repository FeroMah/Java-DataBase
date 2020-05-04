# CREATE SCHEMA university;
# DROP SCHEMA university;
CREATE TABLE majors
(
    major_id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(50)
);

CREATE TABLE students
(
    student_id     INT(11)     NOT NULL AUTO_INCREMENT PRIMARY KEY,
    student_number VARCHAR(12) NOT NULL,
    student_name   VARCHAR(50) NOT NULL,
    major_id       INT(11),
    CONSTRAINT fk_major_id FOREIGN KEY (major_id) REFERENCES majors (major_id)
);

CREATE TABLE payments
(
    payment_id     INT(11)       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    payment_date   DATE          NOT NULL,
    payment_amount DECIMAL(8, 2) NOT NULL,
    student_id     INT(11),
    CONSTRAINT fk_student_id FOREIGN KEY (student_id) REFERENCES students (student_id)
);

CREATE TABLE subjects
(
    subject_id   INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    subject_name VARCHAR(50)
);
CREATE TABLE agenda
(
    student_id INT(11) NOT NULL,
    subject_id INT(11),
    CONSTRAINT pk_student PRIMARY KEY (student_id,subject_id),
    CONSTRAINT fk_agenda_student_id FOREIGN KEY (student_id) REFERENCES students (student_id),
    CONSTRAINT fk_agenda_subject_id FOREIGN KEY (subject_id) REFERENCES subjects (subject_id)
);