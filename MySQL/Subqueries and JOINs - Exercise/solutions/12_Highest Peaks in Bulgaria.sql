SELECT mc.country_code, m.mountain_range, p.peak_name, p.elevation
FROM mountains_countries AS mc
         INNER JOIN mountains AS m on mc.mountain_id = m.id
         INNER JOIN peaks AS p on m.id = p.mountain_id
WHERE mc.country_code = 'BG'
  AND p.elevation > 2835
ORDER BY p.elevation DESC;