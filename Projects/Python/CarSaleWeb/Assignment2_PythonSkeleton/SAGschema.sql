DROP TABLE IF EXISTS Make CASCADE;
DROP TABLE IF EXISTS Model CASCADE;
DROP TABLE IF EXISTS Salesperson CASCADE;
DROP TABLE IF EXISTS Customer CASCADE;
DROP TABLE IF EXISTS CarSales CASCADE;
SET datestyle = 'ISO, DMY';

CREATE TABLE Salesperson (
    UserName VARCHAR(10) PRIMARY KEY,
    Password VARCHAR(20) NOT NULL,
    FirstName VARCHAR(50) NOT NULL,
    LastName VARCHAR(50) NOT NULL,
	UNIQUE(FirstName, LastName)
);

INSERT INTO Salesperson VALUES 
('jdoe', 'Pass1234', 'John', 'Doe'),
('brown', 'Passwxyz', 'Bob', 'Brown'),
('ksmith1', 'Pass5566', 'Karen', 'Smith');

CREATE TABLE Customer (
    CustomerID VARCHAR(10) PRIMARY KEY,
    FirstName VARCHAR(50) NOT NULL,
    LastName VARCHAR(50) NOT NULL,
    Mobile VARCHAR(20) NOT NULL
);

INSERT INTO Customer VALUES 
('c001', 'David', 'Wilson', '4455667788'),
('c899', 'Eva', 'Taylor', '5566778899'),
('c199',  'Frank', 'Anderson', '6677889900'),
('c910', 'Grace', 'Thomas', '7788990011'),
('c002',  'Stan', 'Martinez', '8899001122'),
('c233', 'Laura', 'Roberts', '9900112233'),
('c123', 'Charlie', 'Davis', '7712340011'),
('c321', 'Jane', 'Smith', '9988990011'),
('c211', 'Alice', 'Johnson', '7712222221');

CREATE TABLE Make (
    MakeCode VARCHAR(5) PRIMARY KEY,
    MakeName VARCHAR(20) UNIQUE NOT NULL
);

INSERT INTO Make VALUES ('MB', 'Mercedes Benz');
INSERT INTO Make VALUES ('TOY', 'Toyota');
INSERT INTO Make VALUES ('VW', 'Volkswagen');
INSERT INTO Make VALUES ('LEX', 'Lexus');
INSERT INTO Make VALUES ('LR', 'Land Rover');

CREATE TABLE Model (
    ModelCode VARCHAR(10) PRIMARY KEY,
    ModelName VARCHAR(20) UNIQUE NOT NULL,
    MakeCode VARCHAR(10) NOT NULL,  
    FOREIGN KEY (MakeCode) REFERENCES Make(MakeCode)
);

INSERT INTO Model (ModelCode, ModelName, MakeCode) VALUES
('aclass', 'A Class', 'MB'),
('cclass', 'C Class', 'MB'),
('eclass', 'E Class', 'MB'),
('camry', 'Camry', 'TOY'),
('corolla', 'Corolla', 'TOY'),
('rav4', 'RAV4', 'TOY'),
('defender', 'Defender', 'LR'),
('rangerover', 'Range Rover', 'LR'),
('discosport', 'Discovery Sport', 'LR'),
('golf', 'Golf', 'VW'),
('passat', 'Passat', 'VW'),
('troc', 'T Roc', 'VW'),
('ux', 'UX', 'LEX'),
('gx', 'GX', 'LEX'),
('nx', 'NX', 'LEX');

CREATE TABLE CarSales (
  CarSaleID SERIAL primary key,
  MakeCode VARCHAR(10) NOT NULL REFERENCES Make(MakeCode),
  ModelCode VARCHAR(10) NOT NULL REFERENCES Model(ModelCode),
  BuiltYear INTEGER NOT NULL CHECK (BuiltYear BETWEEN 1950 AND EXTRACT(YEAR FROM CURRENT_DATE)),
  Odometer INTEGER NOT NULL,
  Price Decimal(10,2) NOT NULL,
  IsSold Boolean NOT NULL,
  BuyerID VARCHAR(10) REFERENCES Customer,
  SalespersonID VARCHAR(10) REFERENCES Salesperson,
  SaleDate Date
);

----- login check in
CREATE OR REPLACE FUNCTION check_login(login VARCHAR(10), password_param VARCHAR(20))
RETURNS TABLE(username VARCHAR(10), firstname VARCHAR(50), lastname VARCHAR(50)) AS $$
BEGIN
    RETURN QUERY
    SELECT s.username, s.firstname, s.lastname
    FROM salesperson s
    WHERE LOWER(s.username) = LOWER(login) AND s.password = password_param;
END;
$$ LANGUAGE plpgsql;

-------- get_car_sales_summary
CREATE OR REPLACE FUNCTION get_car_sales_summary()
RETURNS TABLE(
    make VARCHAR(20),
    model VARCHAR(20),
    "availableUnits" BIGINT,
    "soldUnits" BIGINT,
    "soldTotalPrices" TEXT,
    "lastPurchaseAt" TEXT
) AS $$

BEGIN
    RETURN QUERY
    SELECT
        COALESCE(a.Make, s.Make) AS make,
        COALESCE(a.Model, s.Model) AS model,
        COALESCE(a.AvailableUnits, 0) AS "availableUnits",
        COALESCE(s.SoldUnits, 0) AS "soldUnits",
        CASE
            WHEN COALESCE(s.TotalSales, 0) = 0 THEN '0'
            ELSE TO_CHAR(COALESCE(s.TotalSales, 0), 'FM999999999.00') -- format the total sales
        END AS "soldTotalPrices",
        COALESCE(TO_CHAR(s.LastPurchasedAt, 'DD-MM-YYYY'), '') AS "lastPurchaseAt"
    FROM
      (
        SELECT
          ma.makename AS Make,
          mo.modelname AS Model,
          COUNT(*) AS AvailableUnits
        FROM carsales ca
        NATURAL JOIN model mo
        NATURAL JOIN make ma
        WHERE ca.issold = FALSE
        GROUP BY ma.makename, mo.modelname
      ) a
    FULL OUTER JOIN
      (
        SELECT
          ma.makename AS Make,
          mo.modelname AS Model,
          COUNT(*) AS SoldUnits,
          SUM(ca.price) AS TotalSales,
          MAX(ca.saledate) AS LastPurchasedAt
        FROM carsales ca
        NATURAL JOIN model mo
        NATURAL JOIN make ma
        WHERE ca.issold = TRUE
        GROUP BY ma.makename, mo.modelname
      ) s
    ON a.Make = s.Make AND a.Model = s.Model
    ORDER BY make, model;
END;
$$ LANGUAGE plpgsql;



INSERT INTO CarSales (MakeCode, ModelCode, BuiltYear, Odometer, Price, IsSold, BuyerID, SalespersonID, SaleDate) VALUES
('MB', 'cclass', 2020, 64210, 72000.00, TRUE, 'c001', 'jdoe', '01/03/2024'),
('MB', 'eclass', 2019, 31210, 89000.00, FALSE, NULL, NULL, NULL),
('TOY', 'camry', 2021, 98200, 37200.00, TRUE, 'c123', 'brown', '07/12/2023'),
('TOY', 'corolla', 2022, 65000, 35000.00, TRUE, 'c910', 'jdoe', '21/09/2024'),
('LR', 'defender', 2018, 115000, 97000.00, FALSE, NULL, NULL, NULL),
('VW', 'golf', 2023, 22000, 33000.00, TRUE, 'c233', 'jdoe', '06/11/2023'),
('LEX', 'nx', 2020, 67000, 79000.00, TRUE, 'c321', 'brown', '01/01/2025'),
('LR', 'discosport', 2021, 43080, 85000.00, TRUE, 'c211', 'ksmith1', '27/01/2021'),
('TOY', 'rav4', 2019, 92900, 48000.00, FALSE, NULL, NULL, NULL),
('MB', 'aclass', 2022, 47000, 57000.00, TRUE, 'c199', 'jdoe', '01/03/2025'),
('LEX', 'ux', 2023, 23000, 70000.00, TRUE, 'c899', 'brown', '01/01/2023'),
('VW', 'passat', 2020, 63720, 42000.00, FALSE, NULL, NULL, NULL),
('MB', 'eclass', 2021, 12000, 155000.00, TRUE, 'c002', 'ksmith1', '01/10/2024'),
('LR', 'rangerover', 2017, 60000, 128000.00, FALSE, NULL, NULL, NULL),
('TOY', 'camry', 2025, 10, 49995.00, FALSE, NULL, NULL, NULL),
('LR', 'discosport', 2022, 53000, 89900.00, FALSE, NULL, NULL, NULL),
('MB', 'cclass', 2023, 55000, 82100.00, FALSE, NULL, NULL, NULL),
('MB', 'aclass', 2025, 5, 78000.00, FALSE, NULL, NULL, NULL),
('MB', 'aclass', 2015, 8912, 12000.00, TRUE, 'c199', 'jdoe', '11/03/2020'),
('TOY', 'camry', 2024, 21000, 42000.00, FALSE, NULL, NULL, NULL),
('LEX', 'gx', 2025, 6, 128085.00, FALSE, NULL, NULL, NULL),
('MB', 'eclass', 2019, 99220, 105000.00, FALSE, NULL, NULL, NULL),
('VW', 'golf', 2023, 53849, 43000.00, FALSE, NULL, NULL, NULL),
('MB', 'cclass', 2022, 89200, 62000.00, FALSE, NULL, NULL, NULL);



