create table if not exists Flight(
	plane_Id varchar(8) references plane,
	flight_Id int PRIMARY KEY,
	departs date NOT NULL,
	origin varchar(187) NOT NULL,
	destination varchar(187) NOT NULL
);

/*
Alter table Flight 
add constraint one_flight
unique (plane_Id,departs);
*/

/*
INSERT INTO Plane VALUES (1, 'jet', 10);

INSERT INTO Plane VALUES (2, 'jet', 10);

INSERT INTO Plane VALUES (3, 'jet', 10);
*/

/*
INSERT INTO Flight VALUES (1, 1, '2000-01-01','sydeny','shanghai');
*/

SELECT *
FROM Plane;

SELECT *
FROM Flight;
