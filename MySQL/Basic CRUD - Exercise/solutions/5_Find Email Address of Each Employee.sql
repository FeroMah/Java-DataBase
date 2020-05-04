# USE soft_uni;
ALTER TABLE employees
    ADD COLUMN full_email_address VARCHAR(100);

UPDATE employees
SET full_email_address= CONCAT(first_name, '.', last_name, '@softuni.bg')
WHERE full_email_address IS NULL;

SELECT full_email_address FROM employees;
