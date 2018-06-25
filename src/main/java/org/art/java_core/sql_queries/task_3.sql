/* Advanced SQL queries */

CREATE DATABASE book_catalog;

USE book_catalog;

CREATE TABLE t_books
(
  book_name VARCHAR(20),
  author_id INT,
  pages     SMALLINT,
  genre     VARCHAR(15),
  CONSTRAINT pk_books PRIMARY KEY (book_name, author_id),
  CONSTRAINT fk_books FOREIGN KEY (author_id)
  REFERENCES authors (author_id)
);

CREATE TABLE authors
(
  author_id   INT PRIMARY KEY AUTO_INCREMENT,
  fname       VARCHAR(15),
  lname       VARCHAR(15),
  birth_date  DATE,
  nationality VARCHAR(15)
);

INSERT INTO authors (fname, lname, birth_date, nationality)
VALUES ('Will', 'Smith', '1957-12-23', 'american');

INSERT INTO authors (fname, lname, birth_date, nationality)
VALUES ('Tom', 'Hopkins', '1967-09-15', 'english');

INSERT INTO authors (fname, lname, birth_date, nationality)
VALUES ('Tony', 'Williams', '1971-05-12', 'italian');

INSERT INTO authors (fname, lname, birth_date, nationality)
VALUES ('Looney', 'Robins', '1985-07-02', 'french');

INSERT INTO authors (fname, lname, birth_date, nationality)
VALUES ('Ron', 'Malins', '1975-02-09', 'german');

INSERT INTO authors (fname, lname, birth_date, nationality)
VALUES ('Harry', 'Balish', '1964-09-05', 'american');

INSERT INTO authors (fname, lname, birth_date, nationality)
VALUES ('Enriko', 'Govani', '1899-04-11', 'spanish');

INSERT INTO t_books (book_name, author_id, pages, genre)
VALUES ('Sunlight at night', 4, 150, 'fiction');

INSERT INTO t_books (book_name, author_id, pages, genre)
VALUES ('Sunlight at night', 5, 90, 'fiction');

INSERT INTO t_books (book_name, author_id, pages, genre)
VALUES ('For happiness', 7, 250, 'fiction');

INSERT INTO t_books (book_name, author_id, pages, genre)
VALUES ('Black holes', 2, 110, 'science');

INSERT INTO t_books (book_name, author_id, pages, genre)
VALUES ('Black holes', 6, 200, 'science');

INSERT INTO t_books (book_name, author_id, pages, genre)
VALUES ('State of silence', 1, 190, 'science-fiction');

INSERT INTO t_books (book_name, author_id, pages, genre)
VALUES ('Losing mind', 3, 220, 'novel');

INSERT INTO t_books (book_name, author_id, pages, genre)
VALUES ('Over the stars', 5, 150, 'novel');

INSERT INTO t_books (book_name, author_id, pages, genre)
VALUES ('Universe', 6, 180, 'science');

INSERT INTO t_books (book_name, author_id, pages, genre)
VALUES ('Universe', 2, 160, 'science');

INSERT INTO t_books (book_name, author_id, pages, genre)
VALUES ('Lily finds sweets', 3, 100, 'novel');

INSERT INTO t_books (book_name, author_id, pages, genre)
VALUES ('Beautiful day', 3, 150, 'novel');

/* 1 */
SELECT
  concat(a.fname, ' ', a.lname) AS book_author,
  b.book_name                   AS book_name
FROM authors a INNER JOIN t_books b
    ON a.author_id = b.author_id;

/* 2 */
SELECT
  b.book_name,
  concat(a.fname, ' ', a.lname) AS book_author,
  a.nationality
FROM t_books b INNER JOIN authors a
    ON b.author_id = a.author_id
WHERE a.nationality = 'american';

/* 3 */
SELECT
  b.book_name,
  concat(a.fname, ' ', a.lname) AS book_author,
  a.birth_date
FROM t_books b INNER JOIN authors a
    ON b.author_id = a.author_id
WHERE a.birth_date > '1900-00-00';

/* 4 */
DELETE FROM t_books
WHERE book_name = 'Over the stars';

/* 5 */
SELECT
  concat(a.fname, ' ', a.lname)       AS author_name,
  a.nationality,
  cast(avg(b.pages) AS DECIMAL(4, 0)) AS avg_pages_number,
  a.birth_date
FROM t_books b INNER JOIN authors a
    ON b.author_id = a.author_id
GROUP BY a.author_id
ORDER BY a.birth_date;

/* 6 */
SELECT
  concat(a.fname, ' ', a.lname)       AS author_name,
  a.nationality,
  cast(avg(b.pages) AS DECIMAL(4, 0)) AS avg_pages_number
FROM t_books b INNER JOIN authors a
    ON b.author_id = a.author_id
GROUP BY a.author_id
HAVING avg(b.pages) > 300;

/* 7 */
SELECT
  concat(a.fname, ' ', a.lname)       AS author_name,
  a.nationality,
  cast(avg(b.pages) AS DECIMAL(4, 0)) AS avg_pages_number
FROM t_books b INNER JOIN authors a
    ON b.author_id = a.author_id
GROUP BY a.author_id
HAVING avg(b.pages) > (SELECT avg(pages)
                       FROM t_books);

