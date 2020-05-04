CREATE TABLE teachers
(
    teacher_id INT         NOT NULL  UNIQUE,
    name       VARCHAR(50) NOT NULL,
    manager_id INT
);

INSERT INTO teachers
VALUES (101, 'John', null),
       (102, 'Maya', 106),
       (103, 'Silvia', 106),
       (104, 'Ted', 105),
       (105, 'Mark', 101),
       (106, 'Greta', 101);

ALTER TABLE teachers
    ADD CONSTRAINT pk_teachers PRIMARY KEY (teacher_id),
    ADD CONSTRAINT fk_manager FOREIGN KEY (manager_id) REFERENCES teachers (teacher_id);