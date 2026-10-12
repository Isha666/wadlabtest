CREATE DATABASE IF NOT EXISTS test;
USE test;

CREATE TABLE IF NOT EXISTS Employees (
    id INT PRIMARY KEY,
    name VARCHAR(50),
    age INT
);

INSERT IGNORE INTO Employees (id, name, age) VALUES
    (101, 'Ravi', 20),
    (102, 'Priya', 21);

SELECT * FROM Employees;
