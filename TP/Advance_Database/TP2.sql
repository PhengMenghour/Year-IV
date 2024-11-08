USE classicmodels;

-- Create roles for each access level
CREATE ROLE "employee";
CREATE ROLE "manager";
CREATE ROLE "boss";

-- Employee role permission
GRANT SELECT, UPDATE ON classicmodels.customers TO "employee";
GRANT SELECT, UPDATE, DELETE, SELECT ON classicmodels.orders TO "employee";
GRANT SELECT ON classicmodels.orderdetails TO "employee";
GRANT SELECT, UPDATE, DELETE, INSERT ON classicmodels.products TO "employee";
GRANT SELECT, UPDATE, DELETE, INSERT ON classicmodels.productlines TO "employee";

-- Manager role permission
GRANT ALL PRIVILEGES ON classicmodels.* TO "manager";

-- Boss role permission
GRANT ALL PRIVILEGES ON *.* TO "boss";

-- Assign employee role to users sokty, kataro and ucchi
CREATE USER "sokty"@"%" IDENTIFIED BY "password";
CREATE USER "kataro"@"%" IDENTIFIED BY "password";
CREATE USER "uchhi"@"%" IDENTIFIED BY "password";

GRANT "employee" TO "sokty"@"%", "kataro"@"%", "uchhi"@"%";

-- Assign manager role to user madara
CREATE USER "madara"@"%" IDENTIFIED BY "password";

GRANT "manager" TO "madara"@"%";

-- Assign boss role to user makara
CREATE USER "makara"@"%" IDENTIFIED BY "password";

GRANT "boss" TO "makara"@"%";

-- Revoke manager role from madara
REVOKE "manager" FROM "madara"@"%";
-- Create and assign seller role
CREATE ROLE "seller";
GRANT SELECT, INSERT, UPDATE ON classicmodels.employees TO "seller";
GRANT "seller" TO "madara"@"%";

-- Revoke employee roles from kataro
REVOKE "employee" FROM "kataro"@"%";
-- Grant manager role to kataro
GRANT "manager" TO "kataro"@"%";

-- Verify access
SHOW GRANTS FOR "madara"@"%";
SHOW GRANTS FOR "kataro"@"%";
