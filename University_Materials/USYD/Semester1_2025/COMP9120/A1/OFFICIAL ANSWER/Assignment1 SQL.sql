-- Drop tables if they already exist
DROP TABLE IF EXISTS Vehicle CASCADE;
DROP TABLE IF EXISTS VehicleImages CASCADE;
DROP TABLE IF EXISTS PreOwnedVehicle CASCADE;
DROP TABLE IF EXISTS NewVehicle CASCADE;
DROP TABLE IF EXISTS Payment CASCADE;
DROP TABLE IF EXISTS AfterMarketOptions CASCADE;
DROP TABLE IF EXISTS Customer CASCADE;
DROP TABLE IF EXISTS CarSale CASCADE;
DROP TABLE IF EXISTS Loan CASCADE;
DROP TABLE IF EXISTS TradeInVehicle CASCADE;
DROP TABLE IF EXISTS Salesperson CASCADE;
DROP TABLE IF EXISTS TestDrives CASCADE;

-- Create tables
CREATE TABLE Vehicle (
	VIN VARCHAR(15) PRIMARY KEY,
	Model VARCHAR(50),
	Make VARCHAR(50),
	BuiltYear INTEGER,
	Odometer INTEGER NOT NULL CHECK(Odometer >= 0), -- The odometer reading of a vehicle should always have value equal or greater than zero.
	Colour VARCHAR(20),
	TransmissionType VARCHAR(20),
	Price DECIMAL(10, 2) NOT NULL CHECK(Price > 0), -- All attributes in a tuple relating to details about a price should always have positive values.
	IsSold BOOLEAN NOT NULL,
	Description TEXT
);

CREATE TABLE VehicleImages (
	VIN VARCHAR(15) REFERENCES Vehicle,
	ImageURL VARCHAR(2083), -- 2083 is the max URL length in most browsers
	PRIMARY KEY(VIN, ImageURL)
);

CREATE TABLE PreOwnedVehicle (
	VIN VARCHAR(15) PRIMARY KEY REFERENCES Vehicle,
	LastOwner VARCHAR(100) NOT NULL
);

CREATE TABLE NewVehicle (
	VIN VARCHAR(15) PRIMARY KEY REFERENCES Vehicle	
);

CREATE TABLE Customer (
	CustomerID INTEGER PRIMARY KEY,
	FirstName VARCHAR(50) NOT NULL, -- All attributes in a tuple relating to details about a name should always have values.
	LastName VARCHAR(50) NOT NULL, -- All attributes in a tuple relating to details about a name should always have values.
	Email VARCHAR(50) UNIQUE NOT NULL, -- Email must be unique.
	Address VARCHAR(200) NOT NULL,
	Mobile INTEGER NOT NULL,
	LicenceNumber INTEGER UNIQUE NOT NULL -- unique driver’s licence number
);

CREATE TABLE Salesperson (
	StaffID INTEGER PRIMARY KEY, 
	FirstName VARCHAR(50) NOT NULL, -- All attributes in a tuple relating to details about a name should always have values.
	LastName VARCHAR(50) NOT NULL, -- All attributes in a tuple relating to details about a name should always have values.
	Email VARCHAR(50) UNIQUE NOT NULL, -- Email must be unique.
	Salary DECIMAL(12, 2) NOT NULL CHECK(Salary > 0), -- The salesperson’s salary should always be greater than zero
	Mobile INTEGER NOT NULL,
	CommissionRate DECIMAL(4, 2) NOT NULL CHECK (CommissionRate > 0 and CommissionRate < 10) -- The salesperson’s commission rate should always be greater than 0 but less than 10%. 
);

CREATE TABLE CarSale (
	CarSaleID INTEGER PRIMARY KEY,
	BasePrice DECIMAL(12, 2) NOT NULL CHECK(BasePrice > 0), -- All attributes in a tuple relating to details about a price should always have positive values.
	DiscountPrice DECIMAL(10, 2) NOT NULL CHECK(DiscountPrice > 0), -- All attributes in a tuple relating to details about a price should always have positive values.
	Status VARCHAR(10) NOT NULL CHECK(Status IN ('Pending', 'Completed')),
	SaleDate DATE NOT NULL, -- Fields in a tuple related to dates and/or times should always have values.
	VIN VARCHAR(15) NOT NULL UNIQUE REFERENCES Vehicle, -- Relationship with Key Constraints on both sides 
	CustomerID INTEGER NOT NULL REFERENCES Customer,
	SalespersonID INTEGER NOT NULL REFERENCES Salesperson,
	CONSTRAINT unique_customer_vehicle_per_day UNIQUE(CustomerID, SaleDate) -- A customer can only purchase one vehicle per sale per day.
);

CREATE TABLE TradeInVehicle (
	VIN VARCHAR(15) PRIMARY KEY REFERENCES PreOwnedVehicle,
	MechanicalConditionAssessment VARCHAR(10) NOT NULL CHECK (MechanicalConditionAssessment IN ('poor', 'fair', 'good', 'excellent')),
	BodyConditionAssessment	VARCHAR(10) NOT NULL CHECK (BodyConditionAssessment IN ('poor', 'fair', 'good', 'excellent')),
	CustomerID INTEGER NOT NULL REFERENCES Customer,
	CarSaleID INTEGER NOT NULL UNIQUE REFERENCES CarSale  -- Relationship with Key Constraints on both sides
);

CREATE TABLE Payment (
	PaymentID INTEGER, 
	PaymentType VARCHAR(20) NOT NULL CHECK(PaymentType IN ('cash', 'credit card', 'bank transfer', 'bank financing')),
	PaymentDate Date NOT NULL, -- Fields in a tuple related to dates and/or times should always have values.
	AmountPaid DECIMAL(10, 2) NOT NULL CHECK(AmountPaid > 0), 
	CarSaleID INTEGER REFERENCES CarSale ON DELETE CASCADE,
	PRIMARY KEY(PaymentID, CarSaleID)
);

CREATE TABLE Loan (
	LoanID INTEGER PRIMARY KEY, 
	ApplciationDate DATE NOT NULL, -- Fields in a tuple related to dates and/or times should always have values.
	LoanTerm INTEGER NOT NULL CHECK(LoanTerm between 12 and 50), -- a loan term which ranges from 12 to 50 months
	LoanAmount DECIMAL(10, 2) NOT NULL CHECK(LoanAmount > 0), 
	InterestRate DECIMAL(4, 2) NOT NULL CHECK(InterestRate > 0),
	BankName VARCHAR(50) NOT NULL, -- All attributes in a tuple relating to details about a name should always have values.
	BankProofOfApproval VARCHAR(50), 
	CarSaleID INTEGER UNIQUE NOT NULL REFERENCES CarSale, -- Relationship with Key Constraints on both sides
	PaymentID INTEGER NOT NULL,
	FOREIGN KEY (CarSaleID, PaymentID) REFERENCES Payment(CarSaleID, PaymentID)
);

CREATE TABLE TestDrives (
	CustomerID INTEGER REFERENCES Customer,
	VIN VARCHAR(15) REFERENCES Vehicle,
	TestDriveDateTime timestamp NOT NULL, -- Fields in a tuple related to dates and/or times should always have values.
	Feedback TEXT,
	PRIMARY KEY (CustomerID, VIN)
);

CREATE TABLE AfterMarketOptions (
	ID INTEGER, 
	VIN VARCHAR(15) REFERENCES NewVehicle ON DELETE CASCADE,
	Name VARCHAR(50) NOT NULL, -- All attributes in a tuple relating to details about a name should always have values.
	Price DECIMAL(10, 2) NOT NULL CHECK(Price > 0), -- All attributes in a tuple relating to details about a price should always have positive values.
	Description TEXT,
	PRIMARY KEY(ID, VIN)
);

-- Check constraint to enforce total and disjoint constraints on Vehicle IsA
CREATE OR REPLACE FUNCTION VehicleIsA() RETURNS BOOLEAN LANGUAGE plpgsql AS $$
BEGIN
    IF (
        (SELECT COUNT(*) FROM Vehicle) = 
        (SELECT COUNT(*) FROM (
            (SELECT VIN FROM NewVehicle)
            UNION ALL
            (SELECT VIN FROM PreOwnedVehicle)
        ) AS subquery)) 
	THEN
        RETURN TRUE;
    ELSE
        RETURN FALSE;
    END IF;
END;
$$;

-- Check constraint to enforce "new vehicle can have up to eight aftermarket car options."
CREATE OR REPLACE FUNCTION VehicleAftermarketOptions() RETURNS BOOLEAN LANGUAGE plpgsql AS $$
BEGIN
    IF NOT EXISTS (SELECT VIN FROM NewVehicle NATURAL JOIN AfterMarketOptions GROUP BY VIN HAVING (COUNT(*) > 8)) 
	THEN
	    RETURN TRUE;
    ELSE
        RETURN FALSE;
    END IF;
END;
$$;

-- Check constraint to enforce total participation between Customer and TestDrives
CREATE OR REPLACE FUNCTION CustomerTestDrives() RETURNS BOOLEAN LANGUAGE plpgsql AS $$
BEGIN
    IF NOT EXISTS (SELECT CustomerID from Customer WHERE CustomerID NOT IN (SELECT CustomerID FROM Customer NATURAL JOIN TestDrives)) THEN
		RETURN TRUE;
    ELSE
        RETURN FALSE;
    END IF;
END;
$$;

-- Check constraint to enforce total participation between Customer and CarSale
CREATE OR REPLACE FUNCTION CustomerMustPurchasesCar() RETURNS BOOLEAN LANGUAGE plpgsql AS $$
BEGIN
    IF NOT EXISTS (SELECT CustomerID from Customer WHERE CustomerID NOT IN (SELECT CustomerID FROM Customer NATURAL JOIN CarSale)) THEN
	    RETURN TRUE;
    ELSE
        RETURN FALSE;
    END IF;
END;
$$;

-- insert data
INSERT INTO Vehicle VALUES
('1TOYOTA12345A12', 'TOYOTA', 'LandCruiser Prado', 2025, 10, 'Black', 'Automatic', 85000.00, TRUE, 'Luxury off-road SUV with advanced 4WD capabilities.'),
('2BMWX1234561234', 'BMW', 'X3', 2020, 72000, 'White', 'Automatic', 53095.00, FALSE, 'Compact luxury SUV with a sporty design and high-tech features.'),
('3HONDA123451234', 'Honda', 'CRV', 2019, 120000, 'Silver', 'CVT', 10600.00, FALSE, 'Reliable and fuel-efficient SUV, great for families.'),
('4BENZG1234A1234', 'Mercedes-Benz', 'G-Wagon', 2022, 32000, 'Matte Black', 'Automatic', 180000.00, FALSE, 'High-end luxury SUV with superior off-road performance and iconic styling.'),
('5FORDE1234A1234', 'Ford', 'Explorer', 2025, 1, 'Blue', 'Automatic', 88000.00, TRUE, 'Spacious 7-seater SUV with a powerful engine and modern safety features.'),
('6NISSAN12341234', 'Nissan', 'GTR', 2024, 25, 'Red', 'Dual-Clutch', 155000.00, FALSE, 'High-performance sports car with a twin-turbo V6 and AWD system.');

INSERT INTO VehicleImages
VALUES 
    ('1TOYOTA12345A12', 'https://sag.com/images/vehicle1_front.jpg'),
    ('1TOYOTA12345A12', 'https://sag.com/images/vehicle1_side.jpg'),
    ('2BMWX1234561234', 'https://sag.com/images/vehicle2_front.jpg'),
    ('6NISSAN12341234', 'https://sag.com/images/GTR_rear.jpg');
	
INSERT INTO NewVehicle VALUES ('1TOYOTA12345A12');
INSERT INTO NewVehicle VALUES ('5FORDE1234A1234');
INSERT INTO NewVehicle VALUES ('6NISSAN12341234');
	
INSERT INTO PreOwnedVehicle VALUES ('3HONDA123451234', 'Anna Ace'); -- Commenting out this line will violate VehicleIsA total constraint 
INSERT INTO PreOwnedVehicle VALUES ('4BENZG1234A1234', 'Max Brown');
INSERT INTO PreOwnedVehicle VALUES ('2BMWX1234561234', 'Lily Green');
-- INSERT INTO PreOwnedVehicle VALUES ('6NISSAN12341234', 'Sam Blue'); -- Uncommenting out this line will violate VehicleIsA disjoint constraint 

INSERT INTO
	AfterMarketOptions
VALUES
	(1, '1TOYOTA12345A12', 'Dash Camera', 278, 'Capture every moment on the road.'), 
	(2, '1TOYOTA12345A12', 'Roof Racks', 418.00, 'Made with hgih-strength aluminium and tested beyong Australian Standard requirements.'),
	(3, '1TOYOTA12345A12', 'Bonnet Protector', 124.50 , 'Shield your vehicel from damage adn road debris.'),
	(4, '1TOYOTA12345A12', 'Alloy Bull Bar', 2134.95, 'The Alloy Bull Bar protexts your vehicle''s front, crafted from high-strength aluminum for durability and reduced weight.'),
	(5, '1TOYOTA12345A12', 'Towing Kit', 399.95, 'Tow with confidence with this practical and tough Towing Kit.'),
	(6, '1TOYOTA12345A12', 'Ceramic Window Tint', 600, 'Blocks 99% of UV rays and reduces heat without affecting signals.'),
	(7, '1TOYOTA12345A12', 'Cargo Mat', 96.42, 'Desgined to fit the rear compartment of your vehicle.'),
	(8, '1TOYOTA12345A12', 'Snorkel', 400,'Look after your engine in the dustiest conditions with LandCruiser Prado snorkel.');

-- Uncommenting out below lines will violate the constraint that a vehicle must have no more than 8 aftermarket options
-- INSERT INTO
-- 	AfterMarketOptions
-- VALUES
-- 	(9, '1TOYOTA12345A12', 'Ski Carrier', 821.95, 'These corrosion-resistant carries open at the push of a button, enabling easy loading and unloading.');

INSERT INTO Salesperson
VALUES 
    (1, 'Alice', 'Johnson', 'alice.j@sag.com', 60000.00, '0412345678', 5.50),
    (2, 'Bob', 'Smith', 'bob.s@sag.com', 75000.00, '0423456789', 2.75);

INSERT INTO Customer VALUES (1, 'Emma', 'Brown', 'emma.brown@email.com', '100 George St, Sydney, NSW, Australia', '0412345678', 123456);
-- INSERT INTO Customer VALUES (2, 'Liam', 'Wilson', 'liam.wilson@email.com', '200 Pitt St, Sydney, NSW, Australia', '0423456789', 234567); 
-- INSERT INTO Customer VALUES (3, 'Sophia', 'Davis', 'sophia.davis@email.com', '300 Elizabeth St, Sydney, NSW, Australia', '0434567890', 45678);

Insert into TestDrives values (1, '1TOYOTA12345A12', '12-02-2025 14:30:00', 'Great driving experience, smooth ride.');
-- Insert into TestDrives values (2, '1TOYOTA12345A12', '10-02-2025 10:30:00', 'Uncomfortable seats and too much road noise.'); -- Uncommenting out this line will violate the constraint that a customer must testdrives

INSERT INTO CarSale VALUES (1, 85000.00, 2000.00, 'Completed', '12-02-2025', '1TOYOTA12345A12', 1, 1);
-- INSERT INTO CarSale VALUES (2, 88000.00, 3600.00, 'Pending', '01-03-2025', '5FORDE1234A1234', 2, 1); -- Uncommenting out this line will violate the constraint that a customer must purchase a car

INSERT INTO TradeInVehicle (VIN, MechanicalConditionAssessment, BodyConditionAssessment, CustomerID, CarSaleID)
VALUES 
    ('2BMWX1234561234', 'fair', 'fair', 1, 1);
	
INSERT INTO Payment VALUES (1, 'credit card', '12-02-2025', 10000.00, 1);
INSERT INTO Payment VALUES (2, 'bank financing', '15-02-2025', 38000.00, 1);

-- Uncommenting out below lines will violate the constraint that a customer must purchase at least one car.
-- INSERT INTO Payment VALUES (2, 'cash', '15-02-2025', 38000.00, 1);
-- INSERT INTO Payment VALUES (3, 'bank financing', '15-02-2025', 38000.00, 1);
	
INSERT INTO Loan VALUES (1, '12-02-2025', 24, 38000.00, 5.5, 'Westpac', 'Approved_123456', 1, 2);

-- ENABLE CONSTRAINTS
ALTER TABLE Vehicle ADD CONSTRAINT VehicleIsA CHECK (VehicleIsA());
ALTER TABLE AfterMarketOptions ADD CONSTRAINT EightVehicleAftermarketOptions CHECK (VehicleAftermarketOptions());
ALTER TABLE Customer ADD CONSTRAINT CustomerTestDrives check(CustomerTestDrives());
ALTER TABLE Customer ADD CONSTRAINT CustomerMustPurchasesCar check(CustomerMustPurchasesCar());