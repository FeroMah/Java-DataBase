SELECT emp.employee_id, emp.first_name, emp.manager_id, m.first_name AS manager_name
FROM employees AS emp
JOIN employees AS m on emp.manager_id = m.employee_id
WHERE emp.manager_id IN (3,7)
ORDER BY emp.first_name;