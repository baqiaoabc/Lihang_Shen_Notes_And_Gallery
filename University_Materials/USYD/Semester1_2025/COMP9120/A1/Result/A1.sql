SET client_min_messages TO WARNING;
DROP TABLE IF EXISTS Customer, Vehicle, NewCar, PreOwnedCar, TradeInCar, ScheduleBy, TestDriveRecord, Image, VehicleListing, SalesPerson, SAGCustomer, People, Aftermarket, BankFinancing, Payment, TradeIn, Sale CASCADE;

CREATE TABLE Vehicle(
	VIN INT,
    Status VARCHAR(20) NOT NULL,
	Make VARCHAR(100) NOT NULL,
	Model VARCHAR(100) NOT NULL,
	BuildYear INT NOT NULL,
	OdometerReading BIGINT NOT NULL,
	Color VARCHAR(50) NOT NULL,
	TransmissionType VARCHAR(10) NOT NULL,
	ListedPrice DECIMAL(10, 2) NOT NULL,

	PRIMARY KEY (VIN),
    CHECK (BuildYear BETWEEN 1950 AND 2025),
    CHECK (OdometerReading >= 0),
    CHECK (Status IN ('has been sold','for sale'))
);

CREATE TABLE NewCar(
	VIN INT,

    PRIMARY KEY(VIN),
    -- a
	FOREIGN KEY (VIN) 
        REFERENCES Vehicle(VIN) 
        ON DELETE CASCADE
        ON UPDATE CASCADE
        DEFERRABLE INITIALLY DEFERRED 
);

CREATE TABLE PreOwnedCar(
	VIN INT,
	PreOwnerName VARCHAR(20) NOT NULL,

    PRIMARY KEY(VIN),
    -- b
	FOREIGN KEY (VIN) 
        REFERENCES Vehicle(VIN) 
        ON DELETE CASCADE
        ON UPDATE CASCADE
        DEFERRABLE INITIALLY DEFERRED
);

-- 1
CREATE OR REPLACE FUNCTION check_vin_not_in_preowned()
RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS (SELECT 1 FROM PreOwnedCar WHERE VIN = NEW.VIN) THEN
        RAISE EXCEPTION 'VIN % already exists in PreOwnedCar', NEW.VIN;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trig_newcar_no_dup
BEFORE INSERT OR UPDATE ON NewCar
FOR EACH ROW
EXECUTE FUNCTION check_vin_not_in_preowned();

-- 2
CREATE OR REPLACE FUNCTION check_vin_not_in_newcar()
RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS (SELECT 1 FROM NewCar WHERE VIN = NEW.VIN) THEN
        RAISE EXCEPTION 'VIN % already exists in NewCar', NEW.VIN;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trig_preownedcar_no_dup
BEFORE INSERT OR UPDATE ON PreOwnedCar
FOR EACH ROW
EXECUTE FUNCTION check_vin_not_in_newcar();

-- 17.1
CREATE OR REPLACE FUNCTION trg_check_vehicle_classification_fn()
RETURNS TRIGGER AS $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM NewCar WHERE VIN = NEW.VIN
    ) AND NOT EXISTS (
        SELECT 1 FROM PreOwnedCar WHERE VIN = NEW.VIN
    ) THEN
        RAISE EXCEPTION 'Vehicle (VIN=%) must appear in either NewCar or PreOwnedCar', NEW.VIN;
    END IF;

    RETURN NULL; 
END;
$$ LANGUAGE plpgsql;

CREATE CONSTRAINT TRIGGER trg_check_vehicle_classification
AFTER INSERT OR UPDATE ON Vehicle
FOR EACH ROW
EXECUTE FUNCTION trg_check_vehicle_classification_fn();
---------------------------------------------------
CREATE TABLE VehicleListing (
	VID INTEGER PRIMARY KEY,
	-- d
    VIN INTEGER NOT NULL UNIQUE,
    Description VARCHAR(255) NOT NULL,
	
	-- d
    FOREIGN KEY (VIN) 
        REFERENCES Vehicle(VIN) 
        ON DELETE SET DEFAULT
        ON UPDATE SET DEFAULT
        DEFERRABLE INITIALLY DEFERRED
);

CREATE TABLE Image (
    VID INTEGER,
    Image BYTEA NOT NULL,

	-- f
	CHECK(octet_length(Image) > 0),
    PRIMARY KEY (VID,Image),
	-- e
    FOREIGN KEY (VID) 
        REFERENCES VehicleListing(VID) 
        ON DELETE SET DEFAULT
        ON UPDATE SET DEFAULT
        DEFERRABLE INITIALLY DEFERRED
);

-- 3
CREATE OR REPLACE FUNCTION check_image_exists()
RETURNS TRIGGER AS $$
BEGIN
    IF NOT EXISTS(SELECT 1 FROM Image WHERE VID = NEW.VID) THEN
        RAISE EXCEPTION 'vehicle listing % must have at least one image.', NEW.VID;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE CONSTRAINT TRIGGER trg_check_image
AFTER INSERT ON VehicleListing
FOR EACH ROW
EXECUTE FUNCTION check_image_exists();

-- 22 
CREATE OR REPLACE FUNCTION on_sale_must_have_listing()
RETURNS TRIGGER AS $$
BEGIN
    IF(
        NEW.Status = 'for sale'
    )
    THEN 
        IF NOT EXISTS(
            SELECT 1 FROM VehicleListing WHERE VIN = NEW.VIN
        )
        THEN
            RAISE EXCEPTION 'Car % does no have vehicle listing while it is curretly for sale!', NEW.VIN;
        END IF;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE CONSTRAINT TRIGGER trg_on_sale_must_have_listing
AFTER INSERT ON Vehicle
FOR EACH ROW
EXECUTE FUNCTION on_sale_must_have_listing();

-------------------------------------------------
CREATE TABLE People (
    -- j
	PID INTEGER,
	FirstName VARCHAR(50) NOT NULL,
	LastName VARCHAR(50) NOT NULL,
	PhoneNum INTEGER NOT NULL,
    -- h
	Email VARCHAR(255) UNIQUE NOT NULL,
    
    PRIMARY KEY(PID)
);


CREATE TABLE Customer (
    CustomerID INT,

    PRIMARY KEY(CustomerID)
);

CREATE TABLE SAGCustomer (
	CID INTEGER,
	-- k
    CustomerID INT NOT NULL UNIQUE,
    -- i
	DriverID INT UNIQUE NOT NULL,
    State VARCHAR(50) NOT NULL,
    Street VARCHAR(100) NOT NULL,
    Zipcode INTEGER NOT NULL,
    City VARCHAR(50) NOT NULL,

    PRIMARY KEY (CID),
    FOREIGN KEY (CID) 
        REFERENCES People(PID) 
        ON DELETE CASCADE
        ON UPDATE CASCADE
        DEFERRABLE INITIALLY DEFERRED,
    -- K
    FOREIGN KEY (CustomerID) 
        REFERENCES Customer(CustomerID) 
        ON DELETE CASCADE
        ON UPDATE CASCADE
        DEFERRABLE INITIALLY DEFERRED
);

CREATE TABLE SalesPerson (
    SPID INTEGER ,
    AnnualGrossSalary DECIMAL(10, 2) NOT NULL,
    CommissionRate DECIMAL(5, 2) NOT NULL,

    PRIMARY KEY (SPID),
    -- 18
    CHECK(AnnualGrossSalary > 0),
    CHECK (CommissionRate > 0 AND CommissionRate < 0.1),
    FOREIGN KEY (SPID) 
        REFERENCES People(PID) 
        ON DELETE CASCADE
        ON UPDATE CASCADE
        DEFERRABLE INITIALLY DEFERRED
);

-- 17.3
CREATE OR REPLACE FUNCTION check_people_must_be_sag_or_sales()
RETURNS TRIGGER AS $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM SAGCustomer WHERE CID = NEW.PID
    )
    AND NOT EXISTS (
        SELECT 1 FROM SalesPerson WHERE SPID = NEW.PID
    )
    THEN
        RAISE EXCEPTION 'Person % must be a SAG or SalesPerson (or both)', NEW.PID;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_check_people_must_be_sag_or_sales
AFTER INSERT OR UPDATE ON People
FOR EACH ROW
EXECUTE FUNCTION check_people_must_be_sag_or_sales();
------------------------------------------------------------------------
CREATE TABLE TestDriveRecord (
    TID INTEGER,
    VIN INTEGER,
    -- L2
    CustomerID INT NOT NULL,
    Feedback VARCHAR(255) NOT NULL,
    Date DATE DEFAULT CURRENT_DATE NOT NULL,
    Time TIME DEFAULT CURRENT_TIME NOT NULL,
    

    -- L1
    PRIMARY KEY (TID,VIN),
    FOREIGN KEY (VIN) 
        REFERENCES Vehicle(VIN) 
        ON DELETE CASCADE
        ON UPDATE CASCADE
        DEFERRABLE INITIALLY DEFERRED, 
    FOREIGN KEY (CustomerID) 
        REFERENCES Customer(CustomerID) 
        ON DELETE CASCADE
        ON UPDATE CASCADE
        DEFERRABLE INITIALLY DEFERRED
);

-- 21
CREATE OR REPLACE FUNCTION test_drive_valid_car()
RETURNS TRIGGER AS $$
BEGIN
    IF (
        (SELECT Status FROM Vehicle WHERE VIN = NEW.VIN) = 'has been sold'
    )
    THEN
        RAISE EXCEPTION 'Car % has been sold, cannot be test drive', NEW.VIN;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER trg_test_drive_valid_car
BEFORE UPDATE OR INSERT ON TestDriveRecord
FOR EACH ROW
EXECUTE FUNCTION test_drive_valid_car();

------------------------------------
CREATE TABLE ScheduleBy(
    TID INT,
    VIN INT,
    SPID INT,

	-- M
    PRIMARY KEY (TID,VIN,SPID),
	-- M1, M3
    FOREIGN KEY (TID,VIN) 
        REFERENCES TestDriveRecord(TID,VIN) 
        ON DELETE CASCADE
        ON UPDATE CASCADE
        DEFERRABLE INITIALLY DEFERRED,
	-- M2
    FOREIGN KEY (SPID) 
        REFERENCES SalesPerson(SPID) 
        ON DELETE CASCADE
        ON UPDATE CASCADE
        DEFERRABLE INITIALLY DEFERRED
);

-- 17.2 
CREATE OR REPLACE FUNCTION at_least_one_testdrive_record()
RETURNS TRIGGER AS $$
BEGIN
    IF NOT EXISTS(
        SELECT 1 FROM TestDriveRecord WHERE NEW.CustomerID = CustomerID
    )
    THEN
        RAISE EXCEPTION 'Customer % does not have test drive record, illegal.', NEW.CustomerID;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER trg_at_least_one_testdrive_record
BEFORE UPDATE OR INSERT ON Customer
FOR EACH ROW
EXECUTE FUNCTION at_least_one_testdrive_record();
------------------------------------------------------------------------------
CREATE TABLE Sale(
    SID INT,
	-- O1
    CID INT NOT NULL,
	-- O2
    VIN INT NOT NULL UNIQUE,
	-- O3
    SPID INT NOT NULL,
    Date DATE DEFAULT CURRENT_DATE NOT NULL,
    Status Varchar(10) DEFAULT 'pending' NOT NULL,
    BasePrice DECIMAL(12,2) NOT NULL,
    Discount DECIMAL(12,2) DEFAULT 0 NOT NULL,
    TradeInPrice DECIMAL(12,2) DEFAULT 0 NOT NULL,

    PRIMARY KEY (SID),
	-- O
    FOREIGN KEY (CID) 
        REFERENCES SAGCustomer(CID) 
        ON DELETE CASCADE
        ON UPDATE CASCADE
        DEFERRABLE INITIALLY DEFERRED,
    FOREIGN KEY (VIN) 
        REFERENCES Vehicle(VIN) 
        ON DELETE CASCADE
        ON UPDATE CASCADE
        DEFERRABLE INITIALLY DEFERRED,
    FOREIGN KEY (SPID) 
        REFERENCES SalesPerson(SPID) 
        ON DELETE CASCADE
        ON UPDATE CASCADE
        DEFERRABLE INITIALLY DEFERRED,
    
    -- N
    CONSTRAINT Unique_Customer_Per_Day UNIQUE (CID, Date),
    CHECK (BasePrice > 0 AND Discount >= 0 AND TradeInPrice >= 0),
    CHECK(Status IN ('pending', 'completed'))
);

-- 7 & 9
CREATE OR REPLACE FUNCTION check_car_on_sale()
RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM Vehicle 
        WHERE VIN = NEW.VIN AND Status = 'for sale'
    ) THEN
		-- 9
		UPDATE Vehicle
		SET Status = 'has been sold'
		WHERE VIN = NEW.VIN;
        RETURN NEW;
    ELSE
        RAISE EXCEPTION 'Vehicle with VIN % is not for sale or not inside table', NEW.VIN;
    END IF;
END;

$$ LANGUAGE plpgsql;
CREATE TRIGGER trg_check_car_on_sale
BEFORE INSERT ON Sale
FOR EACH ROW
EXECUTE FUNCTION check_car_on_sale();

-- 17.4
CREATE OR REPLACE FUNCTION check_customer_has_sale()
RETURNS TRIGGER AS $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM Sale WHERE CID = NEW.CID
    )
    THEN
        RAISE EXCEPTION 'SAGCustomer % must have at least one Sale record.', NEW.CID;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_check_customer_has_sale
AFTER INSERT OR UPDATE ON SAGCustomer
FOR EACH ROW
EXECUTE FUNCTION check_customer_has_sale();

------------------------------------------------------------------------
CREATE TABLE TradeInCar(
	VIN INT,
    -- Q
	SID INT NOT NULL UNIQUE,

	BodyCondition VARCHAR(10) NOT NULL,

	MechanicalCondition VARCHAR(10) NOT NULL,

    PRIMARY KEY(VIN),
    -- c
	FOREIGN KEY (VIN) 
        REFERENCES PreOwnedCar(VIN) 
        ON DELETE CASCADE
        ON UPDATE CASCADE
        DEFERRABLE INITIALLY DEFERRED,
	FOREIGN KEY (SID) 
        REFERENCES Sale(SID) 
        ON DELETE CASCADE
        ON UPDATE CASCADE
        DEFERRABLE INITIALLY DEFERRED,
    
	CHECK (MechanicalCondition IN ('poor', 'fair', 'good', 'excellent')),
	CHECK (BodyCondition IN ('poor', 'fair', 'good', 'excellent'))
);
------------------------------------------------------------------------
-- 10 
CREATE OR REPLACE FUNCTION must_onsale_tradein() 
RETURNS TRIGGER as $$
BEGIN
    IF EXISTS(
        SELECT 1 FROM TradeInCar WHERE VIN = NEW.VIN
    )
    THEN
        IF NEW.Status != 'for sale'
        THEN
            RAISE EXCEPTION 'TradeInVehicle % is not for sale when firstly added.', NEW.VIN;
        END IF;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- 20
CREATE TRIGGER trg_must_onsale_tradein
BEFORE INSERT ON Vehicle
FOR EACH ROW
EXECUTE FUNCTION must_onsale_tradein();

CREATE OR REPLACE FUNCTION check_name()
RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS(
        SELECT 1 FROM TradeInCar WHERE VIN = NEW.VIN
    )
    THEN
        IF (
            NEW.PreOwnerName !=
                (
                    SELECT CONCAT(FirstName, ' ', LastName) 
                    FROM People
                    WHERE PID =  (
                            SELECT CID
                            FROM SALE
                            WHERE SID =(
                                SELECT SID FROM TradeInCar WHERE VIN = NEW.VIN
                            )
                        )
                )
        )
        THEN RAISE EXCEPTION 'Preowned car % does not have a correct preowner name', NEW.VIN;
        END IF;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER trg_check_name
BEFORE INSERT ON PreOwnedCar
FOR EACH ROW
EXECUTE FUNCTION check_name();
------------------------------------------------------------------------
CREATE TABLE Aftermarket(
    SID INT,
    AID INT,
    Name varchar(100) NOT NULL,
    Description varchar(255) NOT NULL,
    Price DECIMAL(12,2) NOT NULL,

    PRIMARY KEY (SID,AID),
    CONSTRAINT no_duplicate_option UNIQUE (SID,AID),
    FOREIGN KEY (SID) 
        REFERENCES SALE(SID) 
        ON DELETE CASCADE
        ON UPDATE CASCADE
        DEFERRABLE INITIALLY DEFERRED,
    CHECK(Price > 0)
);

-- 12
CREATE OR REPLACE FUNCTION CheckMaxOptions()
RETURNS TRIGGER AS $$
BEGIN
    IF (SELECT COUNT(*) FROM Aftermarket WHERE SID = NEW.SID) >= 8 THEN
        RAISE EXCEPTION 'SID % cannot have more than 8 options', NEW.SID;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER TriggerMaxOptions
BEFORE INSERT OR UPDATE ON Aftermarket
FOR EACH ROW EXECUTE FUNCTION CheckMaxOptions();

-- 19
CREATE OR REPLACE FUNCTION validate_aftermarket_for_newcar()
RETURNS TRIGGER AS $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM NewCar WHERE VIN = (SELECT VIN FROM Sale WHERE SID = NEW.SID)
    )
    THEN
        RAISE EXCEPTION 'Aftermarket % cannot be added because it is not related to a new car.', NEW.AID;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_validate_aftermarket_for_newcar
BEFORE INSERT OR UPDATE ON Aftermarket
FOR EACH ROW
EXECUTE FUNCTION validate_aftermarket_for_newcar();
-------------------------------------------------------------------------
CREATE TABLE Payment(
    SID INT,
    PayID INT,
    Date DATE DEFAULT CURRENT_DATE NOT NULL,
    POption VARCHAR(20) NOT NULL,
    Amount DECIMAL(12,2) NOT NULL,


    -- R
    PRIMARY KEY (SID,PayID),
    FOREIGN KEY (SID) 
        REFERENCES Sale(SID) 
        ON DELETE CASCADE
        ON UPDATE CASCADE
        DEFERRABLE INITIALLY DEFERRED, 
    CHECK (POption IN ('Cash', 'Credit Card', 'Bank Transfer', 'Bank Financing')),
    CHECK(Amount > 0)
);

-- 11
CREATE OR REPLACE FUNCTION check_balance()
RETURNS TRIGGER AS $$
BEGIN
    IF (
     -- Base price
     (SELECT BasePrice FROM Sale WHERE NEW.SID = SID) - 
     -- Discount price
     (SELECT Discount FROM Sale WHERE NEW.SID = SID) + 
     -- After-market price
     (SELECT COALESCE(SUM(Price), 0) FROM AfterMarket WHERE NEW.SID = SID) - 
     -- Trade-in price 
     (SELECT COALESCE(SUM(TradeInPrice), 0) FROM Sale WHERE NEW.SID = SID) - 
     -- Already paid
     (SELECT COALESCE(SUM(Amount), 0) FROM Payment WHERE NEW.SID = SID) 
	  = 0
 	)
    THEN 
        UPDATE Sale
        SET Status = 'completed'
        WHERE NEW.SID = SID;
        RETURN NEW;
	ELSE
		RETURN NEW;
    END IF;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_check_balance
AFTER INSERT OR UPDATE ON Payment
FOR EACH ROW
EXECUTE FUNCTION check_balance();

-- 15
CREATE OR REPLACE FUNCTION only_one_Bank_Fina1()
RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS(
        SELECT 1 FROM PAYMENT WHERE SID=NEW.SID AND POption = 'Bank Financing'
    )
    THEN RAISE EXCEPTION 'Payment % not allowed add, since a Bank Financing already exist with same SID', NEW.PayID; 
    END IF;
    RETURN NEW;  
END;
$$ LANGUAGE plpgsql;
CREATE OR REPLACE FUNCTION only_one_Bank_Fina2()
RETURNS TRIGGER AS $$
BEGIN
    IF (
        (SELECT COUNT(*) FROM PAYMENT WHERE SID=NEW.SID AND POption = 'Bank Financing')>1
    )
    THEN RAISE EXCEPTION 'Payment % not allowed update, since a Bank Financing already exist with same SID', NEW.PayID; 
    END IF;
    RETURN NEW;  
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER trg_only_one_Bank_Fina1
BEFORE INSERT ON PAYMENT
FOR EACH ROW
EXECUTE FUNCTION only_one_Bank_Fina1();

CREATE TRIGGER trg_only_one_Bank_Fina2
AFTER UPDATE ON PAYMENT
FOR EACH ROW
EXECUTE FUNCTION only_one_Bank_Fina2();

-- 16
CREATE OR REPLACE FUNCTION bank_finan_have_proof()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.POption = 'Bank Financing' THEN
        IF NOT EXISTS(
            SELECT 1 FROM BankFinancing WHERE NEW.SID = SID
        )
        THEN RAISE EXCEPTION 'Payment % need support doc to register', NEW.PayID; 
        END IF;
    END IF;
    RETURN NEW;  
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER bank_finan_have_proof
BEFORE INSERT OR UPDATE ON PAYMENT
FOR EACH ROW
EXECUTE FUNCTION bank_finan_have_proof();
-----------------------------------------------------------------------------------------

CREATE TABLE BankFinancing(
    BFID INT,
    PayID INT NOT NULL UNIQUE,
    -- 13， 13.2
    SID INT NOT NULL UNIQUE,
    Bank VARCHAR(100) NOT NULL,
    LoanTerm INT NOT NULL,
    InterestRate DECIMAL(5,2) NOT NULL,
    ProofOfApproval BOOLEAN DEFAULT FALSE NOT NULL,

    PRIMARY KEY (BFID),
    -- 14
    FOREIGN KEY (SID, PayID) 
        REFERENCES Payment(SID, PayID)
        ON DELETE CASCADE
        ON UPDATE CASCADE
        DEFERRABLE INITIALLY DEFERRED ,
    CHECK (LoanTerm BETWEEN 12 AND 50),
    CHECK (InterestRate >= 0)
);
-----------------------------------------------------------------------------------------
-- INSERT statements to populate each relation
BEGIN;
    --image
    INSERT INTO Image (VID, Image) VALUES
    (4001, E'\\x89504E470D0A1A0A0000000D4948445200000001100000010802000000907753DE'), -- A   模拟图片二进制数据
    (4002, E'\\x89504E470D0A1A0A0000000D4948445200000001000300010802000000907753DE'), -- B
    (4002, E'\\x89504E470D0A1A0A0000000D4948445200000001000040010802000000907753DE'), -- B
    (4003, E'\\x89504E470D0A1A0A0000000D4948445200740001700040010802000000907753DE'); -- D



    ---vehicle listing
    INSERT INTO VehicleListing (VID, VIN, Description) VALUES
    (4001, 3001, 'Brand new Toyota Camry 2023'),
    (4002, 3002, 'Well-maintained Honda Civic 2020'),
    (4003, 3004, 'Well-maintained BYD Civic 2025');


    --Vehicle
    INSERT INTO NewCar (VIN) VALUES (3001); -- 新车A
    INSERT INTO NewCar (VIN) VALUES (3004); -- D
    INSERT INTO PreOwnedCar (VIN, PreOwnerName) VALUES (3002, 'Lihang Shen'); -- 需与People表中对应CID的姓名一致B
    INSERT INTO PreOwnedCar (VIN, PreOwnerName) VALUES (3003, 'Ziyang Jiang'); ---- C


    INSERT INTO Vehicle (VIN, Status, Make, Model, BuildYear, OdometerReading, Color, TransmissionType, ListedPrice) VALUES
    (3001, 'for sale', 'Toyota', 'Camry', 2023, 1500, 'Blue', 'Automatic', 25000.00), --- A
    (3002, 'for sale', 'Honda', 'Civic', 2020, 0, 'Red', 'Manual', 18000.00), --B
    (3003, 'has been sold', 'Tesla', 'Elect', 2020, 65000, 'Black', 'Manual', 28000.00), --C
    (3004, 'for sale', 'BYD', 'Civic', 2025, 4000, 'Red', 'Manual', 4008000.00); --D


    --testdrive
    INSERT INTO TestDriveRecord (TID, VIN, CustomerID, Feedback) VALUES
    (5001, 3001, 2001, 'Smooth driving experience'),
    (5002, 3001, 2002, 'Good condition'),
    (5003, 3002, 2002, 'Good condition but noisy engine'),
    (5004, 3002, 2003, 'Good condition but noisy engine'),
    (5005, 3002, 2003, 'Good condition but noisy engine'),
    (5006, 3001, 2004, 'Good condition but noisy engine'),
    (5007, 3001, 2004, 'Good condition but noisy engine');

    --customer
    INSERT INTO Customer (CustomerID) VALUES
    (2001),  -- xiao ming
    (2002),  -- li si
    (2003),   -- wang wu
    (2004);   -- zhang san

    --sale
    INSERT INTO Sale (SID, CID, VIN, SPID,date, BasePrice, Discount) VALUES
    (6001, 1001, 3001, 11001,'2025-04-01' ,25000.00, 1000.00), -- A Alice  xiao ming
    (6002, 1001, 3002, 11001,'2025-04-02' ,18000.00, 500.00); -- B  Alice  xiao ming

    INSERT INTO Sale(SID, CID, VIN, SPID, Date, Status, BasePrice, Discount, TradeInPrice) VALUES
    (6003, 1003, 3004, 1003, '2025-04-01', 'pending',100000,1000,20000); -- D wang wu  wang wu


    --image
    INSERT INTO Image (VID, Image) VALUES
    (4004, E'\\x89504E470D0A1A0A0200000D4948445200740001700040010802000000907753DE'); -- E

    --vehicle listing
    INSERT INTO VehicleListing(VID, VIN, Description) VALUES
    (4004, 3005, 'Well-maintained XIAOMI SU7 2025');

    --tradeincar
    INSERT INTO TradeInCar(vin, sid, bodycondition, mechanicalcondition) values
    (3005,6003,'good','good');

    --preowncar
    INSERT INTO PreOwnedCar(VIN, PreOwnerName) VALUES
    (3005,'wang wu');

    ---vehicle---
    INSERT INTO Vehicle (VIN, Status, Make, Model, BuildYear, OdometerReading, Color, TransmissionType, ListedPrice) VALUES
    (3005,'for sale','XIAOMI','SU7',2025,1000,'green','Auto',300000);



    --SAGCUSTOMER
    INSERT INTO SAGCustomer(CID, CustomerID, DriverID, State, Street, Zipcode, City) VALUES
    (1001,2001,0123456789,'AUS','REDFERN','2016','SYD'), --xiao ming

    (1003,2003,0000000001,'AUS','KINGSTREET','2017','SYD'); --wang wu

    --SALESPERSON
    INSERT INTO SalesPerson(SPID, AnnualGrossSalary, CommissionRate) VALUES
    (11001,100000,0.02),-- A Alice
    (11002,200000,0.03),-- B  Bob
    (1003,300000,0.01); --wang wu

    ---PEOPLE
    INSERT INTO People(PID, FirstName, LastName, PhoneNum, Email) VALUES
    (1001,'ming','xiao',12345,'12345@qq.com'),--xiao ming
    (1003,'si','li',1234567,'1234567@qq.com'),--wang wu
    (11001,'Alice','A',7654321,'7654321@qq.com'), -- A Alice
    (11002,'Bob','B',654321,'654321@qq.com');-- B  Bob

    -- schedual by
    INSERT INTO ScheduleBy(TID, VIN, SPID) VALUES
    (5001,3001,11001),
    (5002,3001,11002),
    (5003,3002,11002),
    (5004,3002,1003),
    (5005,3002,1003),
    (5006,3001,11001),
    (5007,3001,11001);

    --AFtermarketopy---
    INSERT INTO Aftermarket VALUES (6003, 1, 'Opt1', 'Desc', 200);
    INSERT INTO Aftermarket VALUES (6003, 2, 'Opt2', 'Desc', 300);
    INSERT INTO Aftermarket VALUES (6003, 3, 'Opt3', 'Desc', 300);
    INSERT INTO Aftermarket VALUES (6003, 4, 'Opt4', 'Desc', 400);
    INSERT INTO Aftermarket VALUES (6003, 5, 'Opt5', 'Desc', 1000);
    INSERT INTO Aftermarket VALUES (6003, 6, 'Opt6', 'Desc', 2000);
    INSERT INTO Aftermarket VALUES (6003, 7, 'Opt7', 'Desc', 300);
    INSERT INTO Aftermarket VALUES (6003, 8, 'Opt8', 'Desc', 100);


    --bank financing
    INSERT INTO BankFinancing(BFID, PayID, SID, Bank, LoanTerm, InterestRate) VALUES
    (01,5,6003,'COMMONWEALTH',20,0.12);


    --payment
    INSERT INTO Payment(SID, PayID, Date, POption, Amount)  VALUES
    (6001,1,'2025-04-01','Cash',1000),
    (6001,2,'2025-04-01','Credit Card',2000),
    (6002,3,'2025-04-02','Credit Card',17000),
    (6002,4,'2025-04-02','Credit Card',500),
    (6003,5,'2025-05-01','Bank Financing',83600);

END;