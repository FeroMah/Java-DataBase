# USE geography;
ALTER TABLE countries
    ADD COLUMN currency VARCHAR(8) AFTER currency_code;

UPDATE countries
SET currency='Not Euro'
WHERE currency_code <> 'EUR';

Update countries
SET currency='Euro'
WHERE currency_code = 'EUR';

SELECT country_name, country_code, currency
FROM countries
ORDER BY country_name;