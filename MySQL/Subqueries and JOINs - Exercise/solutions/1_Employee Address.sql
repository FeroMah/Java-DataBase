SELECT emp.employee_id, emp.job_title, addres.address_id, addres.address_text
FROM employees AS emp,
     addresses AS addres
WHERE emp.address_id = addres.address_id
ORDER BY address_id
LIMIT 5;

###############################   OR  #################################
SELECT e.employee_id, e.job_title,a.address_id, a.address_text
FROM employees AS e
         INNER JOIN addresses AS a on e.address_id = a.address_id
ORDER BY e.address_id
LIMIT 5;