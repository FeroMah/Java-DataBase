# USE car_relationship;
CREATE TABLE manufacturers
(
    manufacturer_id INT         NOT NULL AUTO_INCREMENT UNIQUE,
    name            VARCHAR(50) NOT NULL UNIQUE,
    established_on  DATE        NOT NULL
) AUTO_INCREMENT = 1;
CREATE TABLE models
(
    model_id        INT         NOT NULL AUTO_INCREMENT UNIQUE,
    name            VARCHAR(50) NOT NULL UNIQUE,
    manufacturer_id INT         NOT NULL
) AUTO_INCREMENT = 101;

ALTER TABLE manufacturers
    ADD CONSTRAINT pk_manufacturer_id PRIMARY KEY (manufacturer_id);

ALTER TABLE models
    ADD CONSTRAINT pk_model_id PRIMARY KEY (model_id),
    ADD CONSTRAINT fk_manufacturer_id FOREIGN KEY (manufacturer_id) REFERENCES manufacturers (manufacturer_id);

INSERT INTO manufacturers
VALUES (1, 'BMW', '1916-03-01'),
       (2, 'Tesla', '2003-01-01'),
       (3, 'Lada', '1966-05-01');
INSERT INTO models
VALUES (101, 'X1', 1),
       (102, 'i6', 1),
       (103, 'Model S', 2),
       (104, 'Model X', 2),
       (105, 'Model 3', 2),
       (106, 'Nova', 3);

#
# SELECT
#     man.manufacturer_id,
#     man.name,
#     DATE(man.established_on),
#     m.model_id,
#     m.name
# FROM
#     manufacturers man
#         INNER JOIN
#     models m ON man.manufacturer_id = m.manufacturer_id
# ORDER BY man.manufacturer_id;
#
# SELECT f.name,f.established_on,m.name FROM manufacturers AS f, models AS m
# WHERE f.manufacturer_id=m.manufacturer_id;

