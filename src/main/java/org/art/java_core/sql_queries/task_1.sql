/* Basic SQL queries */

/* a.1 */
SHOW DATABASES;

CREATE DATABASE countries;

USE countries;

CREATE TABLE t_countries
(
  c_name       VARCHAR(20),
  population SMALLINT,
  square     FLOAT(6, 1),
  lang   VARCHAR(10),
  CONSTRAINT pk_name PRIMARY KEY (c_name)
);

DESC t_countries;

/* a.2 */
INSERT INTO t_countries (c_name, population, square, lang)
VALUES ('Brazil', 207.5, 8515.9, 'portuguese');

/* a.3 */
SELECT c_name
FROM t_countries
WHERE population > 50;

/* a.4 */
SELECT c_name
FROM t_countries
ORDER BY population;

/* a.5 */
SELECT sum(population)
FROM (SELECT *
      FROM t_countries
      ORDER BY population ASC LIMIT 5) e;

/* a.6 */
UPDATE t_countries
SET language = 'french'
WHERE c_name = 'Canada';

/* a.7 */
DELETE FROM t_countries
ORDER BY population DESC
LIMIT 1;








