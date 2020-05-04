# USE soft_uni;
ALTER TABLE employees
    ADD COLUMN full_name VARCHAR(150);

UPDATE employees
SET full_name=CONCAT(first_name, ' ', ' ',last_name)
WHERE middle_name IS NULL;

UPDATE employees
SET full_name=CONCAT(first_name, ' ', middle_name, ' ', last_name)
WHERE middle_name IS NOT NULL;

CREATE VIEW v_employees_job_titles AS
SELECT full_name, job_title
FROM employees;

# DROP VIEW v_employees_job_titles;