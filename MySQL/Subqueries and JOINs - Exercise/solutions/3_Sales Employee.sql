SELECT e.employee_id,e.first_name,e.last_name,d.name FROM employees AS e
JOIN departments d on e.department_id = d.department_id
WHERE d.name='Sales'
ORDER BY  employee_id DESC ;