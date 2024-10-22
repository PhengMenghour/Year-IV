use classicmodels;

show tables;
describe customers;
describe employees;
describe productlines;
describe products;

select * from customers;
select * from productlines;

-- 1. Show first name and last name of customer from France.
SELECT contactFirstName, contactLastName
FROM customers
WHERE Country = "France";

-- 2. Show the first name and last name of employee who is a sale manager.
SELECT firstName, lastName
FROM employees
WHERE jobTitle like "%Manager%";

-- 3. Pair the name and number of employees along with the name of his/her advisor.
SELECT CONCAT (e1.firstName, " ", e1.lastName) AS employee, CONCAT (e2.firstName, " ", e2.lastName) AS advisor
FROM employees e1
LEFT JOIN employees e2
ON e1.employeeNumber = e2.reportsTo;

-- 4. Pair the name and number of customers along with the name of sale representative that work with them.
-- If the sale representative is null, replace it with N/A.
SELECT contactLastName, contactFirstName,
COALESCE(salesREpEmployeeNumber, "N/A")
FROM customers;

-- 5. Show information of customer, if the creditlimited is greater than 80 000, then the customer is vip. If not
-- he is normal.
SELECT
	contactLastName,
    creditLimit,
	CASE
		WHEN creditLimit > 80000 THEN "VIP"
        WHEN creditLimit < 80000 THEN "Normal"
	END AS status
FROM customers;

-- 6. Show all product which is in “classic car” product line.


