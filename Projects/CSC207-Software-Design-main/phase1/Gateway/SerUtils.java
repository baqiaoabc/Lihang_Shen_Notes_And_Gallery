import java.io.*;

public class SerUtils {

    /** Relative Path of the DB the file */
    static String relativePath = "./phase1/Gateway/data.ser";

    /**
     * @Description: Serializing database to file
     * @Param: [data]
     * @return: void
     * @Author:
     * @Date: 2020-11-09
     */
    public static void push (DataBase data) {
        try {
            FileOutputStream fileOut = new FileOutputStream(relativePath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(data);
            System.out.println("Database: Data Saved");
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * @Description: deserializing database from file and return the database. If file does not exists, create one
     * @Param: []
     * @return: java.lang.Object
     * @Author:
     * @Date: 2020-11-09
     */
    public static DataBase pull () {
        try
        {
            FileInputStream fileIn = new FileInputStream(relativePath);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            DataBase db = (DataBase) in.readObject();
            System.out.println("Database: Data Load");
            in.close();
            fileIn.close();
            return db;
        } catch(IOException i) {
            // i.printStackTrace();
            System.out.println("Database: Data File does not found, initializing new Data");
            DataBase d = new DataBase();
            d.firstOrganizer();
            return d;
        } catch(ClassNotFoundException c) {
            System.out.println("Database: Class not found");
            c.printStackTrace();
            return null;
        }
    }
}
