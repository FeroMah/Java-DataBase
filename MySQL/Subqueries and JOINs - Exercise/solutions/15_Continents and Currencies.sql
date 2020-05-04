SELECT  con.continent_code,c.currency_code, COUNT(c.currency_code) AS currency_usage  FROM continents AS con
LEFT JOIN countries AS c on con.continent_code = c.continent_code
GROUP BY  c.currency_code,c.continent_code
HAVING currency_usage  MAX()
ORDER BY c.continent_code ;


SELECT c.continent_code,c.currency_code, COUNT(*) AS currency_usage FROM countries AS c
GROUP BY c.currency_code,c.continent_code
ORDER BY c.continent_code, currency_usage DESC
;