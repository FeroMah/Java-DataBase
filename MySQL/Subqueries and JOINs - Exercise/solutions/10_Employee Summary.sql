SELECT e.employee_id,
       CONCAT(e.first_name, ' ', e.last_name) AS employee_name,
       CONCAT(m.first_name, ' ', m.last_name) AS manager_name,
       d.name                                 AS department_name
FROM employees AS e
         INNER JOIN departments AS d on e.department_id = d.department_id
         INNER JOIN employees AS m on m.employee_id = e.manager_id
WHERE e.manager_id IS NOT NULL
ORDER BY employee_id
LIMIT 5;