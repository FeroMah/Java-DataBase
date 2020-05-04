SELECT mc.country_code, COUNT(m.mountain_range) AS mountain_range
FROM mountains_countries AS mc
         INNER JOIN mountains m on mc.mountain_id = m.id
WHERE mc.country_code IN('BG','US','RU')
GROUP BY mc.country_code
ORDER BY mountain_range DESC ;