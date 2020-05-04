# USE soft_uni;
SELECT town_id, name
FROM towns
WHERE LOWER(name) REGEXP '^m|^k|^b|^e'
ORDER BY name;
