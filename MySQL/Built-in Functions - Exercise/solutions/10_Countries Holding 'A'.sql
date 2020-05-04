# USE geography;

SELECT country_name, iso_code
FROM countries
WHERE country_name REGEXP '(.*a.*){3,}' # take from regex101 code generator for JAVA!
ORDER BY iso_code;