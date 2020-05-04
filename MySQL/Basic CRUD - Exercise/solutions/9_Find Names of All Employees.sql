# USE soft_uni;

ALTER TABLE employees
    ADD COLUMN `Full Name` VARCHAR(150) AFTER middle_name;

UPDATE employees
SET `Full Name`=CONCAT(first_name, ' ', middle_name, ' ', last_name)
WHERE `Full Name` IS NULL;

SELECT `Full Name`
FROM employees
WHERE salary = 25000
   or salary = 14000
   or salary = 12500
   or salary = 23600;