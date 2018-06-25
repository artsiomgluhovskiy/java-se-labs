SHOW DATABASES;
CREATE DATABASE testDB1;
USE testDB1;
CREATE TABLE employee
(
  emp_id      SMALLINT(5) UNSIGNED AUTO_INCREMENT,
  fname       VARCHAR(20),
  lname       VARCHAR(20),
  start_date  DATE,
  age         SMALLINT,
  position_id SMALLINT,
  CONSTRAINT pk_emloyee PRIMARY KEY (emp_id),
  CONSTRAINT fk_emloyee FOREIGN KEY (position_id)
  REFERENCES position (position_id)
);

DROP TABLE employee;

CREATE TABLE position
(
  position_id SMALLINT PRIMARY KEY AUTO_INCREMENT,
  pos_name    VARCHAR(4)
);

DROP TABLE position;

INSERT INTO position (pos_name)
VALUES ('CEO');

INSERT INTO employee (fname, lname, start_date, age, position_id)
VALUES ('Ron', 'Whisly', '2008-12-08', 25, 2);

/* Deleting column */
ALTER TABLE employee
  DROP COLUMN dept_id;

/* INNER JOIN */
SELECT
  e.fname,
  e.lname,
  p.pos_name
FROM position p
  INNER JOIN employee e
    ON e.position_id = p.position_id;

/* INNER JOIN with USING */
SELECT
  e.fname,
  e.lname,
  p.pos_name
FROM position p
  INNER JOIN employee e
  USING (position_id);

/* The same operation using WHERE */
SELECT
  e.fname,
  e.lname,
  p.pos_name
FROM position p, employee e
WHERE p.position_id = e.position_id;

/* Grouping and amount with condition */
SELECT
  e.fname,
  e.lname,
  p.pos_name,
  count(*) amount
FROM employee e INNER JOIN position p
    ON e.position_id = p.position_id
GROUP BY p.position_id
HAVING count(*) > 1;

/* Distinct positions */
SELECT count(DISTINCT position_id)
FROM employee;

/* Grouping by year (with statements) */
SELECT
  e.fname,
  e.lname,
  EXTRACT(YEAR FROM start_date)
FROM employee e
GROUP BY extract(YEAR FROM start_date);

/* Left outer join */
SELECT
  e.fname,
  e.lname,
  p.pos_name
FROM employee e
  LEFT JOIN position p ON e.position_id = p.position_id;

/* Right outer join */
SELECT
  e.fname,
  e.lname,
  p.pos_name
FROM employee e
  RIGHT JOIN position p ON e.position_id = p.position_id;

/* Full outer join */
SELECT
  e.fname,
  e.lname,
  p.pos_name
FROM employee e
  LEFT JOIN position p ON e.position_id = p.position_id
UNION
SELECT
  e.fname,
  e.lname,
  p.pos_name
FROM employee e
  RIGHT JOIN position p ON e.position_id = p.position_id;

/* Grouping and having */
SELECT p.pos_name, cast(avg(e.age) as DECIMAL(3, 1)) as 'Average Age',
  cast(MAX(e.age) as DECIMAL(3, 1)) as 'Max Age'
FROM position p INNER JOIN employee e ON e.position_id = p.position_id
GROUP BY p.pos_name
HAVING avg(e.age) > 21;

/* Limit offset */
SELECT *
FROM employee
ORDER BY age DESC
LIMIT 2 OFFSET 1;





