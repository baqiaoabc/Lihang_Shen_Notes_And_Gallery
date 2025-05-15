/*
 * Example JDBC client for University Registration DB
 * Make sure you have added an appropriate PostgreSQL JDBC driver library
 */
package jdbcclient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.Types;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.postgresql.ds.PGSimpleDataSource;

public class JDBCclient {
    // connection parameters - ENTER YOUR LOGIN AND PASSWORD HERE
    private final String userid   = "";
    private final String passwd   = "";
	private final String myHost	  = "";

    // instance variable for the database connection   
    private Connection conn = null; 
        
    /**
     * Establishes a connection to the PostgreSQL database.
     * The connection parameters are read from the instance variables above
     * (userid, passwd, and database).
     * @returns  true   on success and then the instance variable 'conn' 
     *                  holds an open connection to the database.
     *           false  otherwise
     */ 
    public boolean connectToDatabase ()
    {
       try 
       {   
           /* connect to the database */
		   PGSimpleDataSource source = new PGSimpleDataSource();
		   source.setServerName(myHost);
		   source.setDatabaseName(userid);
		   source.setUser(userid);
		   source.setPassword(passwd);

           conn = source.getConnection();
           return true;
       }
       catch (SQLException sql_ex) 
       {  
           /* error handling */
           System.out.println(sql_ex);
           return false;
       }
    }
        
    /**
     * open ONE single database connection
     */
    public boolean openConnection ()
    {
        boolean retval = true;
        
        if ( conn != null )
            System.err.println("You are already connected to PostgreSQL server; no second connection is needed!");
        else {
            if ( connectToDatabase() )
                System.out.println("You are successfully connected to PostgreSQL server.");
            else {
                System.out.println("Oops - something went wrong.");
                retval = false;
            }
        }
        
        return retval;
    }

    /**
     * close the database connection again
     */
    public void closeConnection ()
    {
        if ( conn == null )
            System.err.println("You are not connected to PostgreSQL server!");
        else try
        {
             conn.close(); // close the connection again after usage! 
             conn = null;
        }
        catch (SQLException sql_ex) 
        {  /* error handling */
             System.out.println(sql_ex);
        }
    }
    
    

    /**
     * Example Function, Exercise 2:
     * Lists on the screen all course offerings ascending by uos_Code
     * including all semesters when the course is offered.
     *
     * Assumes that we are already connected to the database
     */
    public void listUnits ()
    {
       try
       {
          /* prepare a dynamic query statement */
          PreparedStatement stmt = conn.prepareStatement(
                                    "SELECT uosCode, uosName, credits, semester, year "
                                     + "  FROM UoSOffering JOIN UnitOfStudy USING (uosCode)"
                                     + " ORDER BY uosCode,year,semester");
     
          /* execute the query and loop through the resultset */
          ResultSet rset = stmt.executeQuery(); 
          int nr = 0;
          while ( rset.next() )
          {
             nr++;
             System.out.println(rset.getString("uosCode") 
                                + " - " + rset.getString("uosName") 
                                + " ("  + rset.getInt("credits") 
                                + "cp) "+ rset.getInt("year")
                                + "-"   + rset.getString("semester"));
          }
              
          if ( nr == 0 )
             System.out.println("No entries found.");
                 
          /* clean up! (NOTE this really belongs in a finally{} block) */
          stmt.close();
       }
       catch (SQLException sqle) 
       {  
           /* error handling */
           System.out.println("SQLException : " + sqle);
       }
    }

	/**
     * Solution for Exercise 2a:
     * Lists on the screen all courses ascending by uosCode and year/sem
     * including the faculty member's name who was teaching it.
     *
     * Assumes that we are already connected to the database
     */
    public void listUnitsWithFacultyMember ()
    {
       try
       {
           /* prepare a dynamic query statement */
           PreparedStatement stmt = conn.prepareStatement(
/* ADD lecturer name */                "SELECT uosCode, uosName, credits, semester, year, name "
                                     + "  FROM UoSOffering JOIN UnitOfStudy USING (uosCode)"
/* ADD Join!*/                       + "       JOIN AcademicStaff ON (id=InstructorId)"
                                     + " ORDER BY uosCode,year,semester");
     
           /* execute the query and loop through the resultset */
           ResultSet rset = stmt.executeQuery(); 
           int nr = 0;
           while ( rset.next() )
           {
                 nr++;
                 System.out.println(rset.getString("uosCode") 
                                    + " - " + rset.getString("uosName") 
                                    + " ("  + rset.getInt("credits") 
                                    + "cp) "+ rset.getInt("year")
                                    + "-"   + rset.getString("semester")
/* ADD print lecturer name */       + " by "+ rset.getString("name"));
           }
              
           if ( nr == 0 )
              System.out.println("No entries found.");
                 
           /* clean up */
           stmt.close();
       }
       catch (SQLException sqle) 
       {  
           /* error handling */
           System.out.println("SQLException : " + sqle);
       }
    }

	public void listUnitsOfGivenFacultyMember ( String name )
    {
       try
       {
              /* prepare a dynamic query statement */
              PreparedStatement stmt = conn.prepareStatement(
/* ADD lecturer name */                "SELECT uosCode, uosName, credits, semester, year, name "
                                     + "  FROM UoSOffering JOIN UnitOfStudy USING (uosCode) "
/* ADD Join!*/                       + "       JOIN AcademicStaff ON (id=InstructorId)"
/* ADD WHERE clause */               + " WHERE name=? "
                                     + " ORDER BY uosCode,year,semester");
/* ADD */     stmt.setString(1, name);
     
              /* execute the query and loop through the resultset */
              ResultSet rset = stmt.executeQuery(); 
              int nr = 0;
              while ( rset.next() )
              {
                 nr++;
                 System.out.println(rset.getString("uosCode") 
                                    + " - " + rset.getString("uosName") 
                                    + " ("  + rset.getInt("credits") 
                                    + "cp) "+ rset.getInt("year")
                                    + "-"   + rset.getString("semester")
/* ADD print lecturer name */       + " by "+ rset.getString("name"));
              }
              
              if ( nr == 0 )
                 System.out.println("No entries found.");

              /* clean up */
              stmt.close();
       }
       catch (SQLException sqle) 
       {  
           /* error handling */
           System.out.println("SQLException : " + sqle);
       }
    }
    	    
    /**
     * Exercise 3b:
     * Display the transcript of a specific student
     */
	 public void listTranscript3b(int studentID)
    {
        try
        {
        		/* prepare a dynamic query statement */
        		PreparedStatement stmt = conn.prepareStatement(
        					"SELECT uosCode, uosName, credits, semester, year, grade " +
        					"FROM Transcript JOIN UnitOfStudy USING(uosCode) " +
        					"WHERE studId=? " +
        					"ORDER BY uosCode,year,semester");
        			

               
        		/* ADD studentId*/     
        		stmt.setInt(1, studentID);
      
               /* execute the query and loop through the resultset */
               ResultSet rset = stmt.executeQuery(); 
               int nr = 0;
               while ( rset.next() )
               {
                  nr++;
                  System.out.println(rset.getString("uosCode") 
                                     + " - " + rset.getString("uosName") 
                                     + " ("  + rset.getInt("credits") 
                                     + "cp) "+ rset.getInt("year")
                                     + "-"   + rset.getString("semester")
                                     + " | "+ rset.getString("grade")
                		  );
               }
               
               if ( nr == 0 )
                  System.out.println("No entries found.");

               /* clean up */
               stmt.close();
        }
        catch (SQLException sqle) 
        {  
            /* error handling */
            System.out.println("SQLException : " + sqle);
        }    	
    }

	public void listTranscript3d ( int studentID )
    {
       try
       {
		   conn.setAutoCommit(false);
              CallableStatement stmt = conn.prepareCall("{? = call listTranscript(?)}");
              
              stmt.setInt(2, studentID);
              stmt.registerOutParameter(1, Types.OTHER);
              stmt.execute();
              ResultSet rset = (ResultSet)stmt.getObject(1);
              
              int nr = 0;
              while ( rset.next() )
              {
                 nr++;
                 System.out.println(rset.getString("uosCode") 
                         + " - " + rset.getString("uosName") 
                         + " ("  + rset.getInt("credits") 
                         + "cp) "+ rset.getInt("year")
                         + "-"   + rset.getString("semester")
                         + " | "+ rset.getString("grade")
                		 );
              }
              
              if ( nr == 0 )
                 System.out.println("No entries found.");

              /* clean up */
              stmt.close();
       }
       catch (SQLException sqle) 
       {  
           /* error handling */
           System.out.println("SQLException : " + sqle);
       }
    }	  
    
    /**
     * Main program.
     */
    public static void main ( String[] args )
    {
       // create our actual client and test the database connection
       JDBCclient uniDB = new JDBCclient();
      
       if ( uniDB.openConnection() ) {
		   // original example function
           uniDB.listUnits();

		   // run Exercise 2a
		   System.out.println("\n*** Exercise 2a ***");
		   uniDB.listUnitsWithFacultyMember();

		   // run Exercise 2b
		   System.out.println("\n*** Exercise 2b ***");
		   String name = "Uwe Roehm";
		   uniDB.listUnitsOfGivenFacultyMember(name);

		   // run Exercise 3b:
		   System.out.println("\n*** Exercise 3b ***");
		   int sid1 = 316424328;
		   uniDB.listTranscript3b(sid1);

		   // run Exercise 3d:
		   System.out.println("\n*** Exercise 3d ***");
		   int sid2 = 316424328;
		   uniDB.listTranscript3d(sid2);

           uniDB.closeConnection();
        }
    }
}
