# USE geography;

SELECT m.mountain_range, p.peak_name, p.elevation AS peak_elevation
FROM mountains AS m,
     peaks AS p
WHERE m.mountain_range = 'Rila'
  AND p.mountain_id = m.id
ORDER BY peak_elevation DESC;

#####   OR  #####
SELECT mountain_range AS mountain ,peak_name AS peak,elevation FROM mountains
INNER JOIN peaks p on mountains.id = p.mountain_id
WHERE mountain_range= 'Rila'
ORDER BY elevation DESC ;