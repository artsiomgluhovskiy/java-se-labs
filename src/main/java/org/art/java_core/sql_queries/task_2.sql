/* Basic SQL queries */

/* a.1 */
SHOW DATABASES;

CREATE DATABASE books;

USE books;

CREATE TABLE t_books
(
  book_id   INT PRIMARY KEY AUTO_INCREMENT,
  name      VARCHAR(20),
  genre     VARCHAR(15),
  year      YEAR,
  language  VARCHAR(10),
  author_id INT,
  CONSTRAINT fk_book_id FOREIGN KEY (author_id)
  REFERENCES author_data (author_id)
);

ALTER TABLE t_books MODIFY id INT AUTO_INCREMENT;

CREATE TABLE author_data
(
  author_id        INT PRIMARY KEY AUTO_INCREMENT,
  author_f_name VARCHAR(20),
  author_l_name  VARCHAR(20),
  country          VARCHAR(15)
);

/* a.2 */
INSERT INTO author_data (author_f_name, author_l_name, country)
VALUES ('Perry', 'Ravins', 'Australia');

/* a.3 */
INSERT INTO t_books (name, genre, year, language, author_id)
VALUES ('Mother', 'fiction', '1993', 'english', 1);

/* a.4 */
SELECT
  b.name,
  a.author_f_name,
  a.author_l_name
FROM t_books b, author_data a
WHERE b.author_id = a.author_id
ORDER BY b.year ASC;

SELECT
  b.name,
  a.author_f_name,
  a.author_l_name
FROM t_books b, author_data a
WHERE b.author_id = a.author_id
ORDER BY b.year DESC;

/* a.5 */
SELECT count(b.author_id) AS books_number
FROM t_books b
GROUP BY b.author_id
HAVING b.author_id = 1;

/* a.5' */
SELECT count(a.author_f_name) as books_number, a.author_f_name
      FROM author_data a INNER JOIN t_books b
          ON a.author_id = b.author_id
      WHERE a.author_f_name = 'Will';







