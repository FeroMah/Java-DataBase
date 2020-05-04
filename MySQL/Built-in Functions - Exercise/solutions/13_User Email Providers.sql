# USE diablo;

ALTER TABLE users
    ADD `Email Provider` VARCHAR(50);
UPDATE users
SET `Email Provider`= REVERSE(SUBSTRING_INDEX(REVERSE(email), '@', 1))
WHERE `Email Provider` IS NULL;

SELECT user_name, `Email Provider` FROM users
ORDER BY `Email Provider`, user_name;

