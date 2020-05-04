--  Queries for SoftUni Database    --
-- 1. Employees with Salary Above 35000
# USE soft_uni;
CREATE PROCEDURE usp_get_employees_salary_above_35000()
BEGIN
    SELECT first_name,
           last_name
    FROM employees
    WHERE salary > 35000
    ORDER BY first_name, last_name, employee_id;
END;

CALL usp_get_employees_salary_above_35000();

-- 2. Employees with Salary Above Number
# USE soft_uni;
CREATE PROCEDURE usp_get_employees_salary_above(desire_value DECIMAL(19, 4))
BEGIN
    SELECT first_name, last_name
    FROM employees
    WHERE salary >= desire_value
    ORDER BY first_name, last_name, employee_id;
END;

CALL usp_get_employees_salary_above(100000);

-- 3. Town Names Starting With
# USE soft_uni;
CREATE PROCEDURE usp_get_towns_starting_with(city_name VARCHAR(50))
BEGIN
    SELECT name
    FROM towns
    WHERE name LIKE CONCAT(city_name, '%')
    ORDER BY name;
END;

CALL usp_get_towns_starting_with('b');

-- 4. Employees from Town
# USE soft_uni;
CREATE PROCEDURE usp_get_employees_from_town(town_name VARCHAR(50))
BEGIN

    SELECT first_name, last_name
    FROM employees
             INNER JOIN addresses AS ad ON employees.address_id = ad.address_id
             INNER JOIN towns AS t ON ad.town_id = t.town_id
    WHERE t.name = town_name
    ORDER BY first_name, last_name, employee_id;
END;

CALL usp_get_employees_from_town('Sofia');

-- 5. Salary Level Function

CREATE FUNCTION ufn_get_salary_level(employee_salary DECIMAL(19, 4))
    RETURNS VARCHAR(50)
    DETERMINISTIC
BEGIN
    DECLARE result VARCHAR(10);
    IF employee_salary < 30000 THEN
        SET result := 'Low';
    ELSEIF employee_salary BETWEEN 30000 AND 50000 THEN
        SET result := 'Average';
    ELSEIF employee_salary > 50000 THEN
        SET result := 'High';
    END IF;
    RETURN result;
END;

SELECT ufn_get_salary_level(30);
SELECT ufn_get_salary_level(40000);
SELECT ufn_get_salary_level(100000000);

-- 6. Employees by Salary Level
# USE soft_uni;
CREATE PROCEDURE usp_get_employees_by_salary_level(salary_lvl VARCHAR(10))
BEGIN
    SELECT first_name, last_name
    FROM employees AS e
    WHERE CASE
              WHEN salary_lvl LIKE 'low' THEN e.salary < 30000
              WHEN salary_lvl LIKE 'average' THEN e.salary BETWEEN 30000 AND 50000
              WHEN salary_lvl LIKE 'High' THEN e.salary > 50000
              END
    ORDER BY first_name DESC, last_name DESC;
END;

CALL usp_get_employees_by_salary_level('high');
CALL usp_get_employees_by_salary_level('average');
CALL usp_get_employees_by_salary_level('low');

-- 7. Define Function
CREATE FUNCTION ufn_is_word_comprised(set_of_letters varchar(50), word varchar(50))
    RETURNS BIT
    DETERMINISTIC
BEGIN
    RETURN word REGEXP (CONCAT('^[', set_of_letters, ']+$'));
END;

SELECT ufn_is_word_comprised('oistmiahf', 'Sofia');
SELECT ufn_is_word_comprised('oistmiahf', 'hales');
SELECT ufn_is_word_comprised('bobr', 'Rob');
SELECT ufn_is_word_comprised('pppp', 'Guy');

-- 8. Find Full Name
# USE bank_db;

CREATE PROCEDURE usp_get_holders_full_name()
BEGIN
    SELECT CONCAT(ac.first_name, ' ', ac.last_name) AS full_name
    FROM account_holders AS ac
    ORDER BY full_name, ac.id;
END;

CALL usp_get_holders_full_name();

-- 9. People with Balance Higher Than
# USE bank_db;
CREATE PROCEDURE usp_get_holders_with_balance_higher_than(money DECIMAL(19, 4))
BEGIN
    SELECT ah.first_name, ah.last_name
    FROM account_holders AS ah
             RIGHT JOIN accounts AS a on ah.id = a.account_holder_id
    GROUP BY ah.first_name, ah.last_name, a.account_holder_id
    HAVING SUM(a.balance) > money
    ORDER BY a.account_holder_id;
END;

CALL usp_get_holders_with_balance_higher_than(7000);
CALL usp_get_holders_with_balance_higher_than(1000000);

-- 10. Future Value Function
# USE bank_db;
DELIMITER $$
CREATE FUNCTION ufn_calculate_future_value(initial_sum DECIMAL(19, 4),
                                           interest_rate DECIMAL(19, 4),
                                           years INT)
    RETURNS DECIMAL(19, 4)
    DETERMINISTIC
BEGIN
    RETURN initial_sum * POW((1 + interest_rate), years);
END;
$$
DELIMITER ;

SELECT ufn_calculate_future_value(1000, 0.1, 5);

-- 11. Calculating Interest
# USE bank_db;
DELIMITER $$
CREATE PROCEDURE usp_calculate_future_value_for_account(account_id INT, interest_rate DECIMAL(19, 4))
BEGIN
    SELECT a.id,
           ah.first_name,
           ah.last_name,
           a.balance,
           ufn_calculate_future_value(balance, interest_rate, 5) AS balance_in_5_years
    FROM accounts AS a
             INNER JOIN account_holders AS ah on a.account_holder_id = ah.id
    WHERE a.id = account_id;
END;
$$
DELIMITER ;

-- 12. Deposit Money
# USE bank_db;
DELIMITER $$
CREATE PROCEDURE usp_deposit_money(account_id INT, money_amount DECIMAL(19, 4))
BEGIN
    IF money_amount > 0 THEN
        START TRANSACTION ;
        UPDATE accounts
        SET balance := balance + money_amount
        WHERE id = account_id;
        IF ((SELECT id
             FROM accounts
             WHERE id = account_id) < 1) THEN
            ROLLBACK;
        ELSE
            COMMIT ;
        END IF;
    END IF;
END;$$
DELIMITER ;

CALL usp_deposit_money(1, 3);
select *
from accounts
WHERE id = 1;

-- 13. Withdraw Money
# USE bank_db;
DELIMITER $$
CREATE PROCEDURE usp_withdraw_money(account_id INT, money_amount DECIMAL(19, 4))
BEGIN
    START TRANSACTION ;
    IF money_amount > 0 AND -- is money positive number
       (SELECT acc.balance
        FROM accounts AS acc
        WHERE acc.id = account_id) >= money_amount THEN
        UPDATE accounts
        SET balance := balance - money_amount;
        COMMIT;
    ELSE
        ROLLBACK ;
    END IF;
END;
$$
DELIMITER ;

CALL usp_withdraw_money(1, 200);
SELECT *
FROM accounts
WHERE id = 1;

-- 14. Money Transfer
# USE bank_db;
DELIMITER $$
CREATE PROCEDURE usp_transfer_money(from_account_id INT, to_account_id INT, amount DECIMAL(19, 4))
BEGIN
    IF amount > 0
        AND
       ((SELECT a.id
         FROM accounts AS a
         WHERE (a.id = from_account_id AND a.balance >= amount)
       ) IS NOT NULL)
        AND
       ((SELECT a.id
         FROM accounts AS a
         WHERE a.id = to_account_id
       ) IS NOT NULL)
    THEN

        START TRANSACTION ;
        UPDATE accounts
        SET balance := balance - amount
        WHERE id = from_account_id;

        UPDATE accounts
        SET balance := balance + amount
        WHERE id = to_account_id;
        COMMIT;
    ELSE
        ROLLBACK ;
    END IF;
END;
$$
DELIMITER ;

CALL usp_transfer_money(2, 1, 453);


