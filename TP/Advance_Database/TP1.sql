use classicmodels;

show tables;
describe customers;
describe employees;
describe productlines;
describe products;

select * from customers;
select * from productlines;
select * from products;
select * from orders;
select * from orderdetails;

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
	END AS customerStatus
FROM customers;

-- 6. Show all product which is in “classic car” product line.
SELECT *
FROM products
WHERE productLine = "Classic cars";

-- 7. Show all products containing “Toyota” in its name.
SELECT *
FROM products
WHERE productName LIKE "%Toyota%";

-- 8. Show all products with the scale smaller than 1:24.
SELECT *
FROM products
WHERE CAST(SUBSTRING_INDEX(productScale, ':', -1) AS UNSIGNED) < 24;

-- 9. Show all products from the vendor “Autoart Studio Design”.
SELECT *
FROM products
WHERE productVendor = "Autoart Studio Design";

-- 10. Show all products with MSRP (the manufacturer’s suggested retail price) higher than twice the cost of
-- buyPrice.
SELECT *
FROM products
WHERE MSRP > (buyPrice * 2);

-- 11. Show information of sale representative of the customer “Land of toys Inc.”
SELECT e.employeeNumber, e.firstName, e.lastName, e.extension, e.email, e.officeCode, e.jobTitle
FROM customers c
JOIN employees e ON c.salesRepEmployeeNumber = e.employeeNumber
WHERE c.customerName = 'Land of Toys Inc.';

-- 12. Show information about order that is shipped later than the required date.
SELECT orderNumber, orderDate, requiredDate, shippedDate, status
FROM orders
WHERE shippedDate > requiredDate;

-- 13. Show all orders made by “La Rochelle Gifts”.
SELECT o.orderNumber, o.orderDate, o.requiredDate, o.shippedDate, o.status, o.comments
FROM orders o
JOIN customers c ON o.customerNumber = c.customerNumber
WHERE c.customerName = 'La Rochelle Gifts';



