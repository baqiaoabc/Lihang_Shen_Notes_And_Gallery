package Data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Vector;

import org.postgresql.util.PSQLException;

import Business.User;
import Presentation.IRepositoryProvider;
import Business.CarSale;
import Business.Summary;
import org.postgresql.ds.PGSimpleDataSource;


/**
 * Encapsulates create/read/update/delete operations to PostgreSQL database
 */
public class PostgresRepositoryProvider implements IRepositoryProvider {

	// instance variable for the database connection   
	private final String userid = "";
    private final String passwd = "";
    private final String[] myHost = new String[] {""};
	
	private Connection openConnection() throws SQLException {
		PGSimpleDataSource source = new PGSimpleDataSource();
		source.setServerNames(myHost);
		source.setDatabaseName(userid);
		source.setUser(userid);
		source.setPassword(passwd);
		Connection conn = source.getConnection();
	    
	    return conn;
	}
	/**
	 * Check Salesperson login
	 *
	 * @param userName : the userName of Salesperson login
	 * @param password : the password of Salesperson login
	 */
	@Override
	public User checkLogin(String userName, String password) {
		User user = null;
		Connection connection = null;
		PreparedStatement stmt = null;

		try {
			connection = openConnection();
			stmt = connection.prepareStatement(
					"SELECT UserName, FirstName, LastName FROM Salesperson WHERE LOWER(UserName) = LOWER(?) AND Password = ?");
			stmt.setString(1, userName);
			stmt.setString(2, password);
			ResultSet rset = stmt.executeQuery();

			while (rset.next())
			{
				user = new User();
				user.setUserName(rset.getString("UserName"));
				user.setFirstName(rset.getString("FirstName"));
				user.setLastName(rset.getString("LastName"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				// clean up
				stmt.close();
				connection.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}


		return user;
	};

	/**
	 * Retrieves the summary of car sales from the database.
	 * Each Summary object holds details about the car sale including make, model, and sale details.
	 * @return A Vector<Summary> containing the car sale summaries.
	 */
	@Override
	public Vector<Summary> getCarSalesSummary() {
		Vector<Summary> summaryList = null;
		Connection connection = null;
		PreparedStatement stmt = null;

		try {
			summaryList = new Vector<Summary>();
			connection = openConnection();
			String sql = "SELECT m.MakeName AS make, md.ModelName AS model, " +
					"(SELECT COUNT(*) FROM CarSales c WHERE c.MakeCode = m.MakeCode AND c.ModelCode = md.ModelCode AND c.IsSold = FALSE) AS availableUnits, " +
					"(SELECT COUNT(*) FROM CarSales c WHERE c.MakeCode = m.MakeCode AND c.ModelCode = md.ModelCode AND c.IsSold = TRUE) AS soldUnits, " +
					"(SELECT COALESCE(SUM(c.Price), 0) FROM CarSales c WHERE c.MakeCode = m.MakeCode AND c.ModelCode = md.ModelCode AND c.IsSold = TRUE) AS soldTotalPrices, " +
					"(SELECT COALESCE(TO_CHAR(MAX(c.SaleDate), 'DD-MM-YYYY'), '') FROM CarSales c WHERE c.MakeCode = m.MakeCode AND c.ModelCode = md.ModelCode AND c.IsSold = TRUE) AS lastPurchaseAt " +
					"FROM Make m NATURAL JOIN Model md " +
					"ORDER BY m.MakeName, md.ModelName;";

			stmt = connection.prepareStatement(sql);
			ResultSet rset = stmt.executeQuery();
			while (rset.next())
			{
				Summary s = new Summary();
				s.setMake(rset.getString("make"));
				s.setModel(rset.getString("model"));
				s.setAvailableUnits(rset.getInt("availableUnits"));
				s.setSoldUnits(rset.getInt("soldUnits"));
				s.setSoldTotalPrices(rset.getString("soldTotalPrices"));
				s.setLastPurchaseAt(rset.getString("lastPurchaseAt"));
				summaryList.add(s);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				// clean up
				stmt.close();
				connection.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		return summaryList;
	}

	/**
	 * Searches for car sales in the database based on the provided search string.
	 * 
	 * Given an expression searchString like 'ro' or 'a class', this method should return
	 * returns a list of CarSale objects that match the search string.
	 *
	 * @param searchString : the searchString to use for finding carsales in the database
	 * @return A Vector<CarSale> containing the car sales that match the search criteria.
	 */
	@Override
	public Vector<CarSale> findCarSales(String searchString) {
		Vector<CarSale> results = null;
		Connection connection = null;
		CallableStatement stmt = null;
		System.out.println("findCarSales: " + searchString);

		try {
			results = new Vector<CarSale>();
			connection = openConnection();

			stmt = connection.prepareCall("{call findCarSalesByCriteria(?)}");
			stmt.setString(1, searchString);
			ResultSet rset = stmt.executeQuery();
			while (rset.next())
			{
				CarSale cs = new CarSale();
				cs.setCarSaleId(rset.getInt("CarSaleID"));
				cs.setMake(rset.getString("MakeName"));
				cs.setModel(rset.getString("ModelName"));
				cs.setBuiltYear(rset.getInt("BuiltYear"));
				cs.setOdometer(rset.getInt("Odometer"));
				cs.setPrice(rset.getBigDecimal("Price"));
				cs.setIsSold(rset.getBoolean("IsSold"));
				cs.setBuyer(rset.getString("Customer"));
				cs.setSalesperson(rset.getString("Salesperson"));
				cs.setSaleDate(rset.getString("SaleDate"));
				results.add(cs);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				// clean up
				stmt.close();
				connection.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		return results;
	}

	/**
	 * Adds a new car sale to the database.
	 *
	 * @param carSale : carSale The CarSale object to be added to the database.
	 * @return true if the car sale was added successfully, false if there was a failure.
	 */
	@Override
	public boolean addCarSale(CarSale carSale) {
		System.out.println("ADD CARSALE");

		Connection connection = null;
		CallableStatement stmt = null;

		try {
			connection = openConnection();
			connection.setAutoCommit(false);
			stmt = connection.prepareCall("{call addCarSale(?,?,?,?,?)}");
			stmt.setString(1, carSale.getMake());
			stmt.setString(2, carSale.getModel());
			stmt.setInt(3, carSale.getBuiltYear());
			stmt.setInt(4, carSale.getOdometer());
			stmt.setBigDecimal(5, carSale.getPrice());
			stmt.execute();
			connection.commit();
			return true;
		} catch (PSQLException pse) {
			System.out.println(pse.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				/* clean up */
				stmt.close();
				connection.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return false;
	}

	/**
	 * Updates an existing car sale in the database. The method assumes
 	 * that the car sale to be updated already exists in the system.
	 *
	 * @param carSale : The CarSale object containing updated details for the car sale.
	 * @return true if the car sale was updated successfully, false if the update failed.
	 */
	@Override
	public boolean updateCarSale(CarSale carSale) {
		System.out.println("UPDATE CARSALE");

		Connection connection = null;
		CallableStatement stmt = null;

		try {
			connection = openConnection();
			connection.setAutoCommit(false);

			stmt = connection.prepareCall("{call updateCarSale(?,?,?,?)}");
			stmt.setInt(1, carSale.getCarSaleId());
			stmt.setString(2, carSale.getBuyer());
			stmt.setString(3, carSale.getSalesperson());
			stmt.setDate(4, carSale.getSaleDate());
			stmt.execute();
			connection.commit();
			return true;
		} catch (PSQLException pse) {
			System.out.println(pse.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				/* clean up */
				stmt.close();
				connection.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return false;
	}
}
