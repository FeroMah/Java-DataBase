USE gringotts;
# not working
SELECT wiz.deposit_group, AVG(magic_wand_size) AS avg_size_wand
FROM wizzard_deposits AS wiz
GROUP BY wiz.deposit_group
ORDER BY avg_size_wand
LIMIT 1;

# working
SELECT deposit_group
FROM wizzard_deposits AS wiz
GROUP BY deposit_group
ORDER BY AVG(wiz.magic_wand_size)
LIMIT 1;
# or (SQL Aliases not required)
SELECT deposit_group
FROM wizzard_deposits
GROUP BY deposit_group
ORDER BY AVG(magic_wand_size)
LIMIT 1;




