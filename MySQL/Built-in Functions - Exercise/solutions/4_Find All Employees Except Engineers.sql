# USE soft_uni;
SELECT first_name,last_name FROM employees
WHERE (job_title) NOT REGEXP 'Engineer'
ORDER BY employee_id;