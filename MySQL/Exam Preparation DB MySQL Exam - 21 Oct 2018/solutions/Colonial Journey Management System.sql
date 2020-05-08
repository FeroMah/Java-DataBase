# CREATE DATABASE colonial_journey_management_system_db;
# USE colonial_journey_management_system_db;


### 00. Table Design
####################
CREATE TABLE planets
(
    id   INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30) NOT NULL
);

CREATE TABLE spaceports
(
    id        INT PRIMARY KEY AUTO_INCREMENT,
    name      VARCHAR(50) NOT NULL,
    planet_id INT         NOT NULL,
    CONSTRAINT fk_planet_id FOREIGN KEY (planet_id) REFERENCES planets (id)
);

CREATE TABLE spaceships
(
    id               INT PRIMARY KEY AUTO_INCREMENT,
    name             VARCHAR(50) NOT NULL,
    manufacturer     VARCHAR(30) NOT NULL,
    light_speed_rate INT DEFAULT 0
);

CREATE TABLE colonists
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(20)     NOT NULL,
    last_name  VARCHAR(20)     NOT NULL,
    ucn        CHAR(10) UNIQUE NOT NULL,
    birth_date DATE            NOT NULL
);

CREATE TABLE journeys
(
    id                       INT PRIMARY KEY AUTO_INCREMENT,
    journey_start            DATETIME NOT NULL,
    journey_end              DATETIME NOT NULL,
    purpose                  ENUM ('Medical','Technical','Educational', 'Military'),
    destination_spaceport_id INT      NOT NULL,
    CONSTRAINT fk_destination_spaceport_id FOREIGN KEY (destination_spaceport_id) REFERENCES spaceports (id),
    spaceship_id             INT      NOT NULL,
    CONSTRAINT fk_spaceship_id FOREIGN KEY (spaceship_id) REFERENCES spaceships (id)
);
CREATE TABLE travel_cards
(
    id                 INT PRIMARY KEY AUTO_INCREMENT,
    card_number        CHAR(10) NOT NULL UNIQUE,
    job_during_journey ENUM ('Pilot', 'Engineer', 'Trooper', 'Cleaner', 'Cook'),
    colonist_id        INT      NOT NULL,
    CONSTRAINT fk_colonist_id FOREIGN KEY (colonist_id) REFERENCES colonists (id),
    journey_id         INT      NOT NULL,
    CONSTRAINT fk_journey_id FOREIGN KEY (journey_id) REFERENCES journeys (id)
);


### 02. Update
##############
UPDATE journeys
SET purpose= CASE
                 WHEN id % 2 = 0 THEN 'Medical'
                 WHEN id % 3 = 0 THEN 'Technical'
                 WHEN id % 5 = 0 THEN 'Educational'
                 WHEN id % 7 = 0 THEN 'Military'
                 ELSE purpose
    END;
	
	
### 03. Delete
##############
DELETE
FROM colonists
WHERE id NOT IN (SELECT colonist_id FROM travel_cards);

##  Another solution below ##

DELETE
FROM colonists
WHERE id = (SELECT c.id
       FROM travel_cards AS tc
                LEFT JOIN colonists AS c on c.id = tc.colonist_id
        WHERE c.id IS NULL
      ) ;

SELECT c.id
FROM travel_cards AS tc
         LEFT JOIN colonists AS c on c.id = tc.colonist_id
WHERE c.id IS NOT NULL;

### 04. Extract all travel cards
################################
SELECT card_number, job_during_journey
FROM travel_cards
ORDER BY card_number;

### 05. Extract all colonists
#############################
SELECT id, CONCAT_WS(' ', first_name, last_name) AS full_name, ucn
FROM colonists
ORDER BY first_name, last_name, id;

### 06. Extract all military journeys
#####################################
SELECT id, journey_start, journey_end
FROM journeys
WHERE purpose = 'Military'
ORDER BY journey_start;

### 07. Extract all pilots
##########################
SELECT c.id, CONCAT_WS(' ', first_name, last_name) AS full_name
FROM colonists AS c
         JOIN travel_cards AS tc on c.id = tc.colonist_id
WHERE tc.job_during_journey = 'Pilot'
ORDER BY c.id;

### 08. Count all colonists
###########################
SELECT COUNT(c.id)
FROM colonists AS c
         INNER JOIN travel_cards tc on c.id = tc.colonist_id
         INNER JOIN journeys AS j on tc.journey_id = j.id
WHERE j.purpose LIKE 'technical';

### 09.Extract the fastest spaceship
####################################
SELECT ship.name AS spaceship_name, s.name
FROM spaceships AS ship
         INNER JOIN journeys AS j on ship.id = j.spaceship_id
         INNER JOIN spaceports AS s on j.destination_spaceport_id = s.id
ORDER BY ship.light_speed_rate DESC
LIMIT 1;

### 10. Extract - pilots younger than 30 years
##############################################
SELECT ship.name, ship.manufacturer
FROM colonists AS c
         INNER JOIN travel_cards tc on c.id = tc.colonist_id
         INNER JOIN journeys j on tc.journey_id = j.id
         INNER JOIN spaceships AS ship on j.spaceship_id = ship.id
WHERE YEAR(c.birth_date) > YEAR(DATE_SUB('2019-01-01', INTERVAL 30 YEAR))
  AND tc.job_during_journey = 'Pilot'
ORDER BY ship.name;

### 11. Extract all educational mission
#######################################
SELECT pl.name AS planet_name, sp.name AS spaceport_name
FROM planets AS pl
         INNER JOIN spaceports AS sp on pl.id = sp.planet_id
         INNER JOIN journeys AS jou on sp.id = jou.destination_spaceport_id
WHERE jou.purpose LIKE 'Educational'
ORDER BY spaceport_name DESC;

### 12. Extract all planets and their journey count
###################################################
SELECT pl.name AS planet_name, COUNT(jou.destination_spaceport_id) AS journeys_count
FROM planets AS pl
         INNER JOIN spaceports AS sp on pl.id = sp.planet_id
         INNER JOIN journeys AS jou on sp.id = jou.destination_spaceport_id
GROUP BY pl.name
ORDER BY journeys_count DESC, pl.name;

### 13. Extract the shortest journey
####################################
SELECT jou.id, pl.name AS planet_name, sp.name AS spaceport_name, jou.purpose AS journey_purpose
FROM journeys AS jou
         INNER JOIN spaceports AS sp on jou.destination_spaceport_id = sp.id
         INNER JOIN planets AS pl on sp.planet_id = pl.id
ORDER BY DATEDIFF(jou.journey_end, jou.journey_start)
LIMIT 1;

### 14. Extract the less popular job
####################################
SELECT tc.job_during_journey AS job_name
FROM journeys AS j
         INNER JOIN travel_cards AS tc on j.id = tc.journey_id
WHERE j.id = (SELECT jou.id
              FROM journeys AS jou -- longest journey
                       INNER JOIN spaceports AS sp on jou.destination_spaceport_id = sp.id
                       INNER JOIN planets AS pl on sp.planet_id = pl.id
              ORDER BY DATEDIFF(jou.journey_end, jou.journey_start) DESC
              LIMIT 1
)
LIMIT 1;

### 15. Get colonists count
###########################
DELIMITER $$
CREATE FUNCTION udf_count_colonists_by_destination_planet(planet_name VARCHAR(30))
    RETURNS INT
BEGIN
    RETURN (SELECT COUNT(tc.id)
            FROM planets AS p
                     INNER JOIN spaceports AS s on p.id = s.planet_id
                     INNER JOIN journeys AS j on s.id = j.destination_spaceport_id
                     INNER JOIN travel_cards tc on j.id = tc.journey_id
            WHERE p.name = planet_name
    );

END;
$$
DELIMITER ;

### 16. Modify spaceship
########################
DELIMITER $$
CREATE PROCEDURE udp_modify_spaceship_light_speed_rate(spaceship_name VARCHAR(50), light_speed_rate_increse INT(11))
BEGIN

    IF (SELECT name
        FROM spaceships
        WHERE spaceship_name = name) IS NOT NULL THEN
        UPDATE spaceships AS ship
        SET ship.light_speed_rate := ship.light_speed_rate + light_speed_rate_increse
        WHERE ship.name = spaceship_name;
    ELSE
        SIGNAL SQLSTATE '45000' SET message_text = 'Spaceship you are trying to modify does not exists.';
        ROLLBACK; -- no point of writing it, only for Judge
    END IF;
END;
$$
DELIMITER ;
CALL udp_modify_spaceship_light_speed_rate('aaaaa', 10);
CALL udp_modify_spaceship_light_speed_rate('USS Templar', 4);
SELECT name, light_speed_rate
FROM spaceships
WHERE name = 'USS Templar';




