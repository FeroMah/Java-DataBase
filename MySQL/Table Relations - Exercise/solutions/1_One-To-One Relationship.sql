CREATE SCHEMA Relationships;
USE Relationships;
CREATE TABLE passports
(
    passport_id     INT AUTO_INCREMENT PRIMARY KEY UNIQUE,
    passport_number VARCHAR(8) UNIQUE
) AUTO_INCREMENT = 101;

INSERT INTO passports
VALUES (101,'N34FG21B'),
       (102,'K65LO4R7'),
       (103,'ZE657QP2');


CREATE TABLE persons
(
    person_id   INT AUTO_INCREMENT UNIQUE,
    first_name  VARCHAR(50),
    salary      DECIMAL(10, 2),
    passport_id INT
);
# DROP TABLE persons;

ALTER TABLE persons
    ADD CONSTRAINT pk_person_id PRIMARY KEY (person_id),
    ADD CONSTRAINT fk_passport_id
        FOREIGN KEY (passport_id) REFERENCES passports (passport_id);

INSERT INTO persons
VALUES (1,'Roberto', 43300.00, 102),
       (2,'Tom', 56100.00, 103),
       (3,'Yana', 60200.00, 101);
#
# SELECT per.first_name, pass.passport_number
# FROM persons AS per,
#      passports AS pass
# WHERE per.passport_id = pass.passport_id;

#
# SELECT per.first_name, pass.passport_number
# FROM persons AS per,
#      passports AS pass
# WHERE per.passport_id = pass.passport_id;