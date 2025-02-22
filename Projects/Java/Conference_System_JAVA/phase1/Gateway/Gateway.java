import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: group_0173
 * @description:
 * @author:
 * @create: 2020-11-09 21:58
 **/
public class Gateway {
    /**
    * @Description: deserializing database from file and return the database
    * @Param: []
    * @return: DataBase
    * @Author:
    * @Date: 2020-11-14
    */
    public DataBase init () throws IOException {
        DataBase db = SerUtils.pull();
//        db.printDataBase();  ## this line is for testing.
        return db;
    }

    /**
    * @Description: Serializing database to file
    * @Param: [database]
    * @return: void
    * @Author:
    * @Date: 2020-11-14
    */
    public void termination (DataBase database) throws IOException {
        SerUtils.push(database);
    }


    /**

    public static void showData () {
        DataBase db = SerUtils.pull();
        System.out.println("=== User List ===");
        db.getUserList().forEach((u) -> System.out.println("UserId: " + u.getUser_id() + " |UserName: " + u.getUserName() + " |Class: " + u.getClass()));
        System.out.println("=== Event List ===");
        db.getEventList().forEach((e) -> System.out.println("EventId: "+ e.getEvent_id() + " |RoomId: " + e.getRoomId() + " |SpeakerId: " + e.getSpeakerId()));
    }


    public static void main(String[] args) throws IOException {
        showData();
        read();
        write();
    }

    public static void read () throws IOException {
        // init
        Gateway gw = new Gateway();

        // read from file
        DataBase db = gw.init();
        // DataBase db = null;

        // print after reading from file
        System.out.println(db);
        db.getUserList().forEach((u) -> System.out.println(u.getUserName() + u.getClass()));
        System.out.println("RoomId:" + db.getNextRoomId());
        System.out.println("UserId:" + db.getNextUserId());
    }

    public static void write() throws IOException {
        // init
        Gateway gw = new Gateway();
        DataBase db = gw.init();

        // manipulate db
        User u1 = new Attendee(db.getNextUserId(), "Ji321312m", "Jim");
        User u2 = new Attendee(db.getNextUserId(), "J1231", "jim2");
        Organizer o1 = new Organizer(db.getNextUserId(), "31231231", "jim3");
        db.addUser(u1);
        db.addUser(u2);
        db.addUser(o1);

        // save db
        gw.termination(db);
    }
 */
}
