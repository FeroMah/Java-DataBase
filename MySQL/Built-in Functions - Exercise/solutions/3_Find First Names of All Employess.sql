USE soft_uni;
SELECT first_name
FROM employees
WHERE department_id IN (3, 10)
  AND hire_date BETWEEN '1995-01-01' AND '2006-01-01'
ORDER BY employee_id;

# Or

SELECT first_name
FROM employees
WHERE ((department_id = 3 or department_id = 10)
    AND (YEAR(hire_date) BETWEEN 1995 AND 2005))
ORDER BY employee_id;

