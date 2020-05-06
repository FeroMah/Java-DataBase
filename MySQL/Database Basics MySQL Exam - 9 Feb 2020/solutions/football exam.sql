	# 01. Table Design

# CREATE DATABASE football;
# USE football;
# DROP SCHEMA football;

CREATE TABLE coaches
(
    id          INT(11) PRIMARY KEY AUTO_INCREMENT,
    first_name  VARCHAR(10)              NOT NULL,
    last_name   VARCHAR(20)              NOT NULL,
    salary      DECIMAL(10, 2) DEFAULT 0 NOT NULL,
    coach_level INT            DEFAULT 0 NOT NULL
);

CREATE TABLE countries
(
    id   INT(11) PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(45) NOT NULL
);

CREATE TABLE towns
(
    id         INT(11) PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(45) NOT NULL,
    country_id INT(11)     NOT NULL,
    CONSTRAINT fk_country_id FOREIGN KEY (country_id) REFERENCES countries (id)
);

CREATE TABLE stadiums
(
    id       INT(11) PRIMARY KEY AUTO_INCREMENT,
    name     VARCHAR(45) NOT NULL,
    capacity INT(11)     NOT NULL,
    town_id  INT(11)     NOT NULL,
    CONSTRAINT fk_town_id FOREIGN KEY (town_id) REFERENCES towns (id)
);

CREATE TABLE teams
(
    id          INT(11) PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(45) NOT NULL,
    established DATE        NOT NULL,
    fan_base    BIGINT(20) DEFAULT 0 NOT NULL ,
    stadium_id  INT(11)     NOT NULL,
    CONSTRAINT fk_stadium_id FOREIGN KEY (stadium_id) REFERENCES stadiums (id)
);

CREATE TABLE skills_data
(
    id        INT(11) PRIMARY KEY AUTO_INCREMENT,
    dribbling INT(11) DEFAULT 0,
    pace      INT(11) DEFAULT 0,
    passing   INT(11) DEFAULT 0,
    shooting  INT(11) DEFAULT 0,
    speed     INT(11) DEFAULT 0,
    strength  INT(11) DEFAULT 0
);

CREATE TABLE players
(
    id             INT(11) PRIMARY KEY AUTO_INCREMENT,
    first_name     VARCHAR(10) NOT NULL,
    last_name      VARCHAR(20) NOT NULL,
    age            INT(11)     NOT NULL DEFAULT 0,
    position       CHAR(1)     NOT NULL,
    salary         DECIMAL(10, 2)       DEFAULT 0 NOT NULL,
    hire_date      DATETIME,
    skills_data_id INT(11)     NOT NULL,
    CONSTRAINT fk_skills_data_id FOREIGN KEY (skills_data_id) REFERENCES skills_data (id),
    team_id        INT(11),
    CONSTRAINT fk_team_id FOREIGN KEY (team_id) REFERENCES teams (id)
);

CREATE TABLE players_coaches
(
    player_id INT(11),
    coach_id  INT(11),
    CONSTRAINT fk_player_id FOREIGN KEY (player_id) REFERENCES players (id),
    CONSTRAINT fk_coach_id FOREIGN KEY (coach_id) REFERENCES coaches (id),
    PRIMARY KEY (player_id, coach_id)
);


	# 02. Insert
	
INSERT INTO coaches(first_name, last_name,salary,coach_level)
   (SELECT p.first_name,p.last_name, p.salary*2 , CHAR_LENGTH(p.first_name) FROM players AS p
    WHERE p.age>=45);
	
	
	# 03. Update
	
UPDATE coaches AS co
SET co.coach_level := co.coach_level + 1
WHERE co.id  IN( SELECT coach_id FROM players_coaches)
 AND first_name LIKE 'a%';
 
 
	# 04. Delete
	
DELETE FROM players AS p
WHERE p.age >=45;


	# 05. Players
	
SELECT first_name, age, salary
FROM players
ORDER BY salary DESC;


	# 06. Young offense players without contract

SELECT p.id, CONCAT_WS(' ',p.first_name,p.last_name) AS full_name , p.age, p.position, p.hire_date FROM players AS p
INNER JOIN skills_data AS sd on p.skills_data_id = sd.id
WHERE p.position= 'A' AND p.age <23 AND hire_date IS NULL AND sd.strength>50
ORDER BY p.salary,age;


	# 07. Detail info for all teams

SELECT t.name, t.established, t.fan_base, COUNT(p.id) AS count_of_players
FROM teams AS t
         LEFT JOIN players AS p on t.id = p.team_id
GROUP BY t.name, t.established, t.fan_base
ORDER BY count_of_players DESC, t.fan_base DESC;

	# 8. The fastest player by towns
-------------------------------------------------

	
	# 09. Total salaries and players by country
	
SELECT DISTINCT cou.name,
                COUNT(p.id)                              AS count_of_players,
                IF(SUM(salary) = 0, NULL, SUM(salary)) AS total_sum_of_salaries
FROM countries AS cou
         LEFT JOIN towns t on cou.id = t.country_id
         LEFT JOIN stadiums s on t.id = s.town_id
         LEFT JOIN teams te ON s.id = te.stadium_id
         LEFT JOIN players p on te.id = p.team_id
GROUP BY cou.name
ORDER BY count_of_players DESC, cou.name;


	# 10. Find all players that play on stadium
	
DELIMITER $$
CREATE FUNCTION udf_stadium_players_count(stadium_name VARCHAR(30))
    RETURNS INT
    DETERMINISTIC
BEGIN
    RETURN (SELECT COUNT(p.id) AS count
            FROM stadiums AS st
                     LEFT JOIN teams AS te on st.id = te.stadium_id
                     INNER JOIN players p on te.id = p.team_id
            WHERE st.name = stadium_name
    );
END;
$$
DELIMITER ;
SELECT udf_stadium_players_count('Jaxworks') as `count`;
SELECT udf_stadium_players_count('Linklinks') as `count`;


# only for test
SELECT COUNT(p.id)
FROM stadiums AS st
         LEFT JOIN teams AS te on st.id = te.stadium_id
         INNER JOIN players p on te.id = p.team_id
WHERE st.name = 'Jaxworks';


	# 11. Find good playmaker by teams
	
DELIMITER $$
CREATE PROCEDURE udp_find_playmaker(min_dribble_points INT, team_name VARCHAR(45))
BEGIN
    SELECT CONCAT_WS(' ', p.first_name, p.last_name) AS full_name, p.age, p.salary, sd.dribbling, sd.speed, te.name
    FROM teams AS te
             INNER JOIN players p on te.id = p.team_id
             INNER JOIN skills_data sd on p.skills_data_id = sd.id
    WHERE te.name = team_name
      AND sd.dribbling > min_dribble_points
      AND sd.speed > (SELECT AVG(skill.speed)
                      FROM teams AS tea
                               INNER JOIN players AS p2 on tea.id = p2.team_id
                               INNER JOIN skills_data AS skill on p2.skills_data_id = skill.id
                      WHERE tea.name = team_name
    )
    ORDER BY sd.speed DESC
    LIMIT 1;
END;
$$
DELIMITER ;
# SELECT CONCAT_WS(' ', p.first_name,p.last_name) AS full_name, p.age,p.salary,sd.dribbling,sd.speed, te.name
# FROM teams AS te
#          INNER JOIN players p on te.id = p.team_id
#          INNER JOIN skills_data sd on p.skills_data_id = sd.id
# WHERE te.name = 'Skyble'
#   AND sd.speed > (SELECT AVG(skill.speed) FROM teams AS tea
#       INNER JOIN players AS p2 on tea.id = p2.team_id
#       INNER JOIN skills_data AS skill on p2.skills_data_id = skill.id
#       WHERE tea.name= 'Skyble'
#       )
# ORDER BY sd.speed DESC
# LIMIT 1;

CALL udp_find_playmaker(20, 'Skyble');