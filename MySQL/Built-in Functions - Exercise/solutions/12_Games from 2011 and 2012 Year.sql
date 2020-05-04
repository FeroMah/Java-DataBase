USE diablo;

# DROP schema diablo;

SELECT name, DATE (start) AS start
FROM games
WHERE YEAR(start) BETWEEN 2011 AND 2012
ORDER BY start, name
LIMIT 50;