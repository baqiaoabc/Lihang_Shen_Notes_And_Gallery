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

    myHost = "localhost"
    userid = "postgres"
    passwd = "Jzy20030515!"
    
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


def checkLogin(login, password):
    '''
    Validate salesperson based on username and password using the stored function check_login
    '''
    try:
        conn = openConnection()  # Assuming openConnection() is defined elsewhere
        cur = conn.cursor()

        cur.callproc("check_login",[login, password])


        # Fetch the result
        salesperson = cur.fetchone()

        cur.close()
        conn.close()

        # If a salesperson is found, return the result as a list
        if salesperson:
            return list(salesperson)  # Convert the tuple to a list
        return None

    except psycopg2.Error as sqle:
        print("psycopg2.Error: " + str(sqle))
        return None



"""
    Retrieves the summary of car sales.

    This method fetches the summary of car sales from the database and returns it 
    as a collection of summary objects. Each summary contains key information 
    about a particular car sale.

    :return: A list of car sale summaries.
"""


def getCarSalesSummary():
    try:
        # Assuming openConnection() is defined elsewhere
        conn = openConnection()
        if not conn:
            return []

        cur = conn.cursor()

        # Call the PostgreSQL function to check login credentials
        cur.callproc("get_car_sales_summary",[])

        # Fetch the result
        cols = [desc[0] for desc in cur.description]
        summary = [dict(zip(cols, row)) for row in cur.fetchall()]


        for record in summary:
            if record["soldTotalPrices"] == 0:
                record["soldTotalPrices"] = 0
            else:
                record["soldTotalPrices"] = "{:.2f}".format(record["soldTotalPrices"])


        #close
        cur.close()
        conn.close()

        return summary

    except psycopg2.Error as sqle:
        print("psycopg2.Error : ", sqle.pgerror)
        return []


def findCarSales(searchString):
    # split search String
    try:
        conn = openConnection()
        curs = conn.cursor()
        # we need to make sure case insensitive for username
        curs.execute(
            """
            SELECT
                c.cid AS "carsale_id",
                COALESCE(c.make) AS "make",
                COALESCE(c.model) AS "model",
                COALESCE(c."year") AS "builtYear",
                COALESCE(c.odometer) AS "odometer",
                COALESCE(c.price) AS "price",
                COALESCE(c.issold) AS "isSold",

                COALESCE(TO_CHAR(c.saledate, 'DD-MM-YYYY'), '') AS "sale_date",
                COALESCE(c.buyer, '') AS "buyer",
                COALESCE(c.salesperson, '') AS "salesperson",

                COALESCE(c.saledate) AS "sale_date_for_order"
            FROM
            (

                -- only have unsold car in here
                SELECT
                      mo.modelname model,
                      ma.makename make,
                      ca.carsaleid cid,
                      ca.odometer odometer,
                      ca.issold issold,
                      ca.builtyear "year",
                      ca.price price,

                      ca.saledate saledate,
                      c.firstname || ' ' || c.lastname AS buyer,
                      s.firstname || ' ' || s.lastname AS salesperson
                FROM carsales ca
                NATURAL JOIN model mo
                NATURAL JOIN make ma
                JOIN customer c ON c.customerid = ca.buyerid
                JOIN salesperson s on s.username = ca.salespersonid

                UNION

                -- only have unsold car in here
                SELECT
                      mo.modelname model,
                      ma.makename make,
                      ca.carsaleid cid,
                      ca.odometer odometer,
                      ca.issold issold,
                      ca.builtyear "year",
                      ca.price price,

                      NULL AS saledate,
                      '' AS buyer,
                      '' AS salesperson
                FROM carsales ca
                NATURAL JOIN model mo
                NATURAL JOIN make ma
                WHERE ca.issold = false

            ) c
            WHERE
                (STRPOS(LOWER(c.make), LOWER(%s)) > 0 OR
                STRPOS(LOWER(c.model), LOWER(%s)) > 0 OR
                STRPOS(LOWER(c.buyer), LOWER(%s)) > 0 OR
                STRPOS(LOWER(c.salesperson), LOWER(%s)) > 0
                )
                AND
                (c."issold" = false
                    OR
                c."issold" = true AND c."saledate" >= CURRENT_DATE - INTERVAL '3 years')
            ORDER BY
                "isSold" ASC,
                "sale_date_for_order" ASC,
                "make" ASC,
                "model" ASC;
            """,
            (searchString, searchString, searchString, searchString),
        )
        cols = [desc[0] for desc in curs.description]
        summary = [dict(zip(cols, row)) for row in curs.fetchall()]

        curs.close()
        return summary
    except psycopg2.Error as sqle:
        print("psycopg2.Error : ", sqle.pgerror)


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
        # print(f"[DEBUG] odometer: {odometer}, price: {price}")
        if((not isinstance(odometer,int))and int(odometer)<= 0) or float(price)<= 0:
            return False
    except (ValueError, TypeError):
        print("odometer and price should be positive number!")
        return False

    try:
        conn = openConnection()
        curs = conn.cursor()

        # get make corresponding name
        curs.execute(
            """
            SELECT make.makecode, model.modelcode
            FROM make
            NATURAL JOIN model
            WHERE 
                make.makename ILIKE %s
                AND
                model.modelname ILIKE %s;
            """,(make,model,),
        )
        row = curs.fetchone()
        # print("row: ",row)
        makecode = row[0]
        modelcode = row[1]
        # print(makecode,modelcode)
        # insert the data
        curs.execute(
            """
            INSERT INTO carsales (MakeCode, ModelCode, BuiltYear, Odometer, Price, IsSold) 
            VALUES (%s, %s, %s, %s, %s, %s);
            """,
            (makecode, modelcode, builtYear, odometer, price, False)
        )
        # remember to close the connection
        conn.commit()
        curs.close()
        return True
    except psycopg2.Error as sqle:
        print("psycopg2.Error : ", sqle.pgerror)
        return False
    except (ValueError, TypeError):
        print("Illegal instance")
        return False
"""
    Updates an existing car sale in the database.

    This method updates the details of a specific car sale in the database, ensuring
    that all fields of the CarSale object are modified correctly. It assumes that 
    the car sale to be updated already exists.

    :param car_sale: The CarSale object containing updated details for the car sale.
    :return: A boolean indicating whether the update was successful or not.
"""

def updateCarSale(carsaleid, customer, salesperosn, saledate):
    try:
        conn = openConnection()
        curs = conn.cursor()
        # get cid
        curs.execute(
            """
            SELECT customerid
            FROM customer
            WHERE customerid ILIKE %s;
            """,
            (customer,),
        )
        row = curs.fetchone()
        print("row: ",row)
        cid = row[0]
        print(cid)

        # get username
        curs.execute(
            """
            SELECT username
            FROM salesperson
            WHERE username ILIKE %s;
            """,
            (salesperosn,),
        )
        row = curs.fetchone()
        print("row: ",row)
        sname = row[0]
        print(sname)

        # insert the data
        curs.execute(
            """
            UPDATE carsales 
            SET buyerid = %s, salespersonid = %s, saledate = %s, issold = %s
            WHERE carsaleid = %s
            AND %s::DATE <= CURRENT_DATE; -- use input as a condition, if fail then not update
            """,
            (cid, sname, saledate, True, carsaleid, saledate)
        )
        if curs.rowcount == 0:
            print("Update failed: sale date is in the future or carsaleid not found.")
            curs.close()
            return False

    # remember to close the connection
        conn.commit()
        curs.close()
        return True
    except psycopg2.Error as sqle:
        print("psycopg2.Error : ", sqle.pgerror)
        return False
    except (ValueError, TypeError):
        print("Illegal instance")
        return False

