SELECT e.department_id,
       (Select emp.salary
        FROM employees as emp
        where e.department_id = emp.department_id
        group by emp.department_id, emp.salary
        ORDER BY  emp.salary DESC
        limit 2,1) As thirth_hiest_salary
From employees as e
group by e.department_id
having thirth_hiest_salary is not null
ORDER BY department_id asc ;