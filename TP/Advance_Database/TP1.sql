use classicmodels;

show tables;
describe customers;
describe employees;
describe offices;
describe orderdetails;
describe orders;
describe payments;
describe productlines;
describe products;

select * from customers;
select * from employees;
select * from offices;
select * from orderdetails;
select * from orders;
select * from payments;
select * from productlines;
select * from products;


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

-- 14. Show all products ordered by “La Rochelle Gifts”.
SELECT p.productCode ,p.productName, od.quantityOrdered, od.priceEach
FROM customers c
JOIN orders o ON c.customerNumber = o.customerNumber
JOIN orderdetails od ON o.orderNumber = od.orderNumber
JOIN products p ON od.productCode = p.productCode
WHERE c.customerName = "La Rochelle Gifts";

-- 15. Show customer’s name who has ordered more than 15 products in once.
SELECT c.customerName, o.orderNumber, SUM(od.quantityOrdered) AS totalProductsOrdered
FROM customers c
JOIN orders o ON c.customerNumber = o.customerNumber
JOIN orderdetails od on o.orderNumber = od.orderNumber
GROUP BY c.customerName, o.orderNumber
HAVING SUM(od.quantityOrdered > 15);

-- 16. Show the best-selling product in “2004-02”.
SELECT p.productCode, p.productName, SUM(od.quantityOrdered) AS totalQuantity
FROM orders o
JOIN orderDetails od ON o.orderNumber = od.orderNumber
JOIN products p ON od.productCode = p.productCode
WHERE o.orderDate BETWEEN "2004-02-01" AND "2004-02-29"
GROUP BY p.productCode, p.productName
ORDER BY totalQuantity DESC
LIMIT 1;


-- 17. Find the lowest sale price of each product.
SELECT p.productCode, p.productName, MIN(od.priceEach) AS lowestPrice
FROM products p
JOIN orderDetails od on p.productCode = od.productCode
GROUP BY p.productCode, p.productName;

-- 18. Find the biggest gape of sale price of each product.
SELECT p.productCode, p.productName, MAX(od.priceEach)-MIN(od.priceEach) as priceGap
FROM products p
JOIN orderDetails od ON p.productCode = od.productCode
GROUP BY p.productCode, p.productName
ORDER BY priceGap DESC;

-- 19. Find the product with the biggest gape of sale price.
SELECT p.productCode, p.productName, MAX(od.priceEach)-MIN(od.priceEach) AS priceGap
FROM products p
JOIN orderDetails od ON p.productCode = od.productCode
GROUP BY p.productCode, p.productName
ORDER BY priceGap DESC
LIMIT 1;

-- 20. Find the total cost of all orders of each customer.
SELECT c.customerName, SUM(od.quantityOrdered * od.priceEach) AS totalCost
FROM customers c
JOIN orders o ON c.customerNumber = o.customerNumber
JOIN orderDetails od ON o.orderNumber = od.orderNumber
GROUP BY c.customerName
ORDER BY totalCost DESC;

-- 21. Find the top customer (with the highest total sale cost) of each city.
WITH CustomerTotal AS (
	SELECT c.customerNumber, c.customerName, c.city, SUM(od.quantityOrdered * od.priceEach) AS totalCost
    FROM customers c
    JOIN orders o ON c.customerNumber =  o.customerNumber
    JOIN orderDetails od ON o.orderNumber = od.orderNumber
    GROUP BY c.customerNumber, c.customerName, c.city
)

SELECT city, customerName, totalCost
FROM customerTotal ct1
WHERE totalCost = (
	SELECT MAX(ct2.totalCost)
    FROM CustomerTotal ct2
    WHERE ct2.city = ct1.city
)
ORDER BY city;

-- 22. Show monthly income, purchase expense, and profit in a specific year. An order that is placed in which
-- month would be considered as part of income of that month; the state of shipment and payment are taken into account.
SELECT 
  YEAR(o.orderDate) AS year,
  MONTH(o.orderDate) AS month,
    ROUND(SUM(pay.amount),2) AS income,
    ROUND(SUM(od.quantityOrdered * od.priceEach),2) AS purchaseExpenses,
	ROUND(SUM(pay.amount - (od.quantityOrdered * od.priceEach)),2) AS profit
FROM orders o
JOIN orderdetails od ON o.orderNumber = od.orderNumber
JOIN products p ON od.productCode = p.productCode
LEFT JOIN payments pay ON o.customerNumber = pay.customerNumber
WHERE YEAR(o.orderDate) = 2004 AND o.STATUS IN ('Shipped', 'Completed')
GROUP BY month(o.orderDate), year(o.orderDate)
ORDER BY month(o.orderDate), year(o.orderDate);

-- 23. The same as number 14, but we want months as columns and incomes, purcase_expense, and profit as rows.
SELECT 
    'Income' AS type,
    SUM(CASE WHEN MONTH(o.orderDate) = 1 THEN od.quantityOrdered * od.priceEach ELSE 0 END) AS January,
    SUM(CASE WHEN MONTH(o.orderDate) = 2 THEN od.quantityOrdered * od.priceEach ELSE 0 END) AS February,
    SUM(CASE WHEN MONTH(o.orderDate) = 3 THEN od.quantityOrdered * od.priceEach ELSE 0 END) AS March,
    SUM(CASE WHEN MONTH(o.orderDate) = 4 THEN od.quantityOrdered * od.priceEach ELSE 0 END) AS April,
    SUM(CASE WHEN MONTH(o.orderDate) = 5 THEN od.quantityOrdered * od.priceEach ELSE 0 END) AS May,
    SUM(CASE WHEN MONTH(o.orderDate) = 6 THEN od.quantityOrdered * od.priceEach ELSE 0 END) AS June,
    SUM(CASE WHEN MONTH(o.orderDate) = 7 THEN od.quantityOrdered * od.priceEach ELSE 0 END) AS July,
    SUM(CASE WHEN MONTH(o.orderDate) = 8 THEN od.quantityOrdered * od.priceEach ELSE 0 END) AS August,
    SUM(CASE WHEN MONTH(o.orderDate) = 9 THEN od.quantityOrdered * od.priceEach ELSE 0 END) AS September,
    SUM(CASE WHEN MONTH(o.orderDate) = 10 THEN od.quantityOrdered * od.priceEach ELSE 0 END) AS October,
    SUM(CASE WHEN MONTH(o.orderDate) = 11 THEN od.quantityOrdered * od.priceEach ELSE 0 END) AS November,
    SUM(CASE WHEN MONTH(o.orderDate) = 12 THEN od.quantityOrdered * od.priceEach ELSE 0 END) AS December
FROM customers c
JOIN orders o ON c.customerNumber = o.customerNumber
JOIN orderdetails od ON o.orderNumber = od.orderNumber
JOIN products p ON od.productCode = p.productCode
WHERE c.customerName = "La Rochelle Gifts"

UNION ALL

SELECT 
    'Purchase Expense' AS type,
    SUM(CASE WHEN MONTH(o.orderDate) = 1 THEN od.quantityOrdered * p.buyPrice ELSE 0 END) AS January,
    SUM(CASE WHEN MONTH(o.orderDate) = 2 THEN od.quantityOrdered * p.buyPrice ELSE 0 END) AS February,
    SUM(CASE WHEN MONTH(o.orderDate) = 3 THEN od.quantityOrdered * p.buyPrice ELSE 0 END) AS March,
    SUM(CASE WHEN MONTH(o.orderDate) = 4 THEN od.quantityOrdered * p.buyPrice ELSE 0 END) AS April,
    SUM(CASE WHEN MONTH(o.orderDate) = 5 THEN od.quantityOrdered * p.buyPrice ELSE 0 END) AS May,
    SUM(CASE WHEN MONTH(o.orderDate) = 6 THEN od.quantityOrdered * p.buyPrice ELSE 0 END) AS June,
    SUM(CASE WHEN MONTH(o.orderDate) = 7 THEN od.quantityOrdered * p.buyPrice ELSE 0 END) AS July,
    SUM(CASE WHEN MONTH(o.orderDate) = 8 THEN od.quantityOrdered * p.buyPrice ELSE 0 END) AS August,
    SUM(CASE WHEN MONTH(o.orderDate) = 9 THEN od.quantityOrdered * p.buyPrice ELSE 0 END) AS September,
    SUM(CASE WHEN MONTH(o.orderDate) = 10 THEN od.quantityOrdered * p.buyPrice ELSE 0 END) AS October,
    SUM(CASE WHEN MONTH(o.orderDate) = 11 THEN od.quantityOrdered * p.buyPrice ELSE 0 END) AS November,
    SUM(CASE WHEN MONTH(o.orderDate) = 12 THEN od.quantityOrdered * p.buyPrice ELSE 0 END) AS December
FROM customers c
JOIN orders o ON c.customerNumber = o.customerNumber
JOIN orderdetails od ON o.orderNumber = od.orderNumber
JOIN products p ON od.productCode = p.productCode
WHERE c.customerName = "La Rochelle Gifts"

UNION ALL

SELECT 
    'Profit' AS type,
    SUM(CASE WHEN MONTH(o.orderDate) = 1 THEN (od.quantityOrdered * od.priceEach) - (od.quantityOrdered * p.buyPrice) ELSE 0 END) AS January,
    SUM(CASE WHEN MONTH(o.orderDate) = 2 THEN (oxd.quantityOrdered * od.priceEach) - (od.quantityOrdered * p.buyPrice) ELSE 0 END) AS February,
    SUM(CASE WHEN MONTH(o.orderDate) = 3 THEN (od.quantityOrdered * od.priceEach) - (od.quantityOrdered * p.buyPrice) ELSE 0 END) AS March,
    SUM(CASE WHEN MONTH(o.orderDate) = 4 THEN (od.quantityOrdered * od.priceEach) - (od.quantityOrdered * p.buyPrice) ELSE 0 END) AS April,
    SUM(CASE WHEN MONTH(o.orderDate) = 5 THEN (od.quantityOrdered * od.priceEach) - (od.quantityOrdered * p.buyPrice) ELSE 0 END) AS May,
    SUM(CASE WHEN MONTH(o.orderDate) = 6 THEN (od.quantityOrdered * od.priceEach) - (od.quantityOrdered * p.buyPrice) ELSE 0 END) AS June,
    SUM(CASE WHEN MONTH(o.orderDate) = 7 THEN (od.quantityOrdered * od.priceEach) - (od.quantityOrdered * p.buyPrice) ELSE 0 END) AS July,
    SUM(CASE WHEN MONTH(o.orderDate) = 8 THEN (od.quantityOrdered * od.priceEach) - (od.quantityOrdered * p.buyPrice) ELSE 0 END) AS August,
    SUM(CASE WHEN MONTH(o.orderDate) = 9 THEN (od.quantityOrdered * od.priceEach) - (od.quantityOrdered * p.buyPrice) ELSE 0 END) AS September,
    SUM(CASE WHEN MONTH(o.orderDate) = 10 THEN (od.quantityOrdered * od.priceEach) - (od.quantityOrdered * p.buyPrice) ELSE 0 END) AS October,
    SUM(CASE WHEN MONTH(o.orderDate) = 11 THEN (od.quantityOrdered * od.priceEach) - (od.quantityOrdered * p.buyPrice) ELSE 0 END) AS November,
    SUM(CASE WHEN MONTH(o.orderDate) = 12 THEN (od.quantityOrdered * od.priceEach) - (od.quantityOrdered * p.buyPrice) ELSE 0 END) AS December
FROM customers c
JOIN orders o ON c.customerNumber = o.customerNumber
JOIN orderdetails od ON o.orderNumber = od.orderNumber
JOIN products p ON od.productCode = p.productCode
WHERE c.customerName = "La Rochelle Gifts";






