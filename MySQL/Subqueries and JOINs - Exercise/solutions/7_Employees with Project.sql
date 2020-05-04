USE soft_uni;
SELECT e.employee_id, e.first_name, p.name AS project_name
FROM employees AS e
         INNER JOIN employees_projects AS e_p ON e.employee_id = e_p.employee_id
         INNER JOIN projects AS p ON e_p.project_id = p.project_id
WHERE DATE (p.start_date) >'2002-08-13' AND p.end_date IS NULL
ORDER BY e.first_name,p.name
LIMIT 5;