USE gringotts;

SELECT (SELECT id,deposit_amount FROM wizzard_deposits
                              GROUP BY id
                              HAVING id+1=id

                             ) AS sum_difference
                      FROM wizzard_deposits;

SELECT ( SUM(deposit_amount)
       ) AS sum_difference
FROM wizzard_deposits
ORDER BY id DESC  ;