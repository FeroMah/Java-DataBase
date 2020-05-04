USE soft_uni;
SELECT e.first_name, e.last_name, t.name, a.address_text
FROM employees AS e,
     towns AS t,
     addresses AS a
WHERE e.address_id = a.address_id
  AND a.town_id = t.town_id
ORDER BY e.first_name, e.last_name
LIMIT 5;

############################    OR  ############################

SELECT e.first_name, e.last_name, t.name, a.address_text
FROM employees AS e
         JOIN addresses AS a ON e.address_id = a.address_id
         JOIN towns AS t on a.town_id = t.town_id
ORDER BY e.first_name, e.last_name
LIMIT 5;
