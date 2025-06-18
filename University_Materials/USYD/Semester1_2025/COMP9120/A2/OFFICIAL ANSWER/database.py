#!/usr/bin/env python3
import psycopg2

#####################################################
##  Database Connection
#####################################################

'''
Connect to the database using the connection string
'''
def openConnection():
    # connection parameters - ENTER YOUR LOGIN AND PASSWORD HERE
    userid = ""
    passwd = ""
    myHost = ""

    # Create a connection to the database
    conn = None
    try:
        # Parses the config file and connects using the connect string
        conn = psycopg2.connect(database=userid,
                                    user=userid,
                                    password=passwd,
                                    host=myHost)

    except psycopg2.Error as sqle:
        print("psycopg2.Error : " + sqle.pgerror)
    
    # return the connection to use
    return conn

'''
Validate salesperson based on username and password
'''
def checkLogin(login, password):
    userInfo = None

    try:
        conn = openConnection()
        curs = conn.cursor()

        # execute the query
        curs.execute("""SELECT UserName, FirstName, LastName
                        FROM Salesperson
                        WHERE LOWER(UserName) = LOWER(%s) AND Password = %s""",(login, password))
        user = curs.fetchone()

        if user is not None:
            userInfo = [user[0], user[1], user[2]]
        else:
            print("Wrong login userName or password")

    except psycopg2.Error as sqle:
        print("There is a problem with check patient login. ", sqle.pgerror)

    finally:
        # commit the transaction
        conn.commit()

        # clean up
        curs.close()
        conn.close()

    return userInfo


"""
    Retrieves the summary of car sales.

    This method fetches the summary of car sales from the database and returns it 
    as a collection of summary objects. Each summary contains key information 
    about a particular car sale.

    :return: A list of car sale summaries.
"""
def getCarSalesSummary():
    summary = []
    try:
        conn = openConnection()
        curs = conn.cursor()

        curs.execute("""SELECT m.MakeName AS make, md.ModelName AS model,
                        (SELECT COUNT(*) FROM CarSales c WHERE c.MakeCode = m.MakeCode AND c.ModelCode = md.ModelCode AND c.IsSold = FALSE) AS available,
                        (SELECT COUNT(*) FROM CarSales c WHERE c.MakeCode = m.MakeCode AND c.ModelCode = md.ModelCode AND c.IsSold = TRUE) AS sold,
                        (SELECT COALESCE(SUM(c.Price), 0) FROM CarSales c WHERE c.MakeCode = m.MakeCode AND c.ModelCode = md.ModelCode AND c.IsSold = TRUE) AS soldTotalPrices,
                        (SELECT COALESCE(TO_CHAR(MAX(c.SaleDate), 'DD-MM-YYYY'), '') FROM CarSales c WHERE c.MakeCode = m.MakeCode AND c.ModelCode = md.ModelCode AND c.IsSold = TRUE) AS lastPurchaseAt
                    FROM Make m NATURAL JOIN Model md
                    ORDER BY m.MakeName, md.ModelName;""")
        
        result = curs.fetchall()

        summary = [{
            'make': row[0],
            'model': row[1],
            'availableUnits': row[2],
            'soldUnits': row[3],
            'soldTotalPrices': row[4],
            'lastPurchaseAt': row[5]
        } for row in result]
    
    except psycopg2.Error as sqle:
        print("There is a problem with connection. " ,  sqle.pgerror)

    finally:
        # commit the transaction
        conn.commit()

        # clean up
        curs.close()
        conn.close()
    return summary


"""
    Finds car sales based on the provided search string.

    This method searches the database for car sales that match the provided search 
    string. See assignment description for search specification

    :param search_string: The search string to use for finding car sales in the database.
    :return: A list of car sales matching the search string.
"""
def findCarSales(searchString):
    vehicle_list = []

    try:
        conn = openConnection()
        curs = conn.cursor()

        curs.callproc('findCarSalesByCriteria', [searchString])
        result = curs.fetchall()
        
        vehicle_list = [{
            'carsale_id': row[0],
            'make': row[1],
            'model': row[2],
            'builtYear': row[3],
            'odometer': row[4],
            'price': row[5],
            'isSold': row[6],
            'sale_date': row[7],
            'buyer': row[8],
            'salesperson' : row[9],
        } for row in result]
    
    except psycopg2.Error as sqle:
        print("There is a problem with find Vehicles by searching. " ,  sqle.pgerror)

    finally:
        # commit the transaction
        conn.commit()

        # clean up
        curs.close()
        conn.close()
    return vehicle_list


"""
    Adds a new car sale to the database.

    This method accepts a CarSale object, which contains all the necessary details 
    for a new car sale. It inserts the data into the database and returns a confirmation 
    of the operation.

    :param car_sale: The CarSale object to be added to the database.
    :return: A boolean indicating if the operation was successful or not.
"""
def addCarSale(make, model, builtYear, odometer, price):
    try:
        conn = openConnection()
        curs = conn.cursor()
        curs.callproc('addCarSale', [make, model, builtYear, odometer, price])

    except psycopg2.Error as sqle:
        print("There is a problem with add car sale record: ",  sqle.pgerror)
        return False

    finally:
        # commit the transaction
        conn.commit()

        # clean up
        curs.close()
        conn.close()

    return True

"""
    Updates an existing car sale in the database.

    This method updates the details of a specific car sale in the database, ensuring
    that all fields of the CarSale object are modified correctly. It assumes that 
    the car sale to be updated already exists.

    :param car_sale: The CarSale object containing updated details for the car sale.
    :return: A boolean indicating whether the update was successful or not.
"""
def updateCarSale(carsaleid, customer, salesperson, saledate):
    try:
        conn = openConnection()
        curs = conn.cursor()
        curs.callproc('updateCarSale', [carsaleid, customer, salesperson, saledate])

    except psycopg2.Error as sqle:
        print("There is a problem with update car sale record: ",  sqle.pgerror)
        return False

    finally:
        # commit the transaction
        conn.commit()

        # clean up
        curs.close()
        conn.close()

    return True
