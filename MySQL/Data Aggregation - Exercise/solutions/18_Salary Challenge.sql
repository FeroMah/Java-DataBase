USE soft_uni;

SELECT first_name, last_name, e.department_id
FROM employees AS e
WHERE e.salary > (SELECT AVG(salary)
                  FROM employees AS d
                  WHERE e.department_id = d.department_id
                  GROUP BY department_id)
ORDER BY department_id, employee_id
LIMIT 10;