USE soft_uni;

SELECT COUNT(salary) AS emp_no_manager
FROM employees
WHERE manager_id IS NULL;
