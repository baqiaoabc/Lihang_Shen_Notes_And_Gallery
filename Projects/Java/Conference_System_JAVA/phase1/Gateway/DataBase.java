import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
 * CSC207.Store All the Data
 */
public class DataBase implements Serializable {
    /**
     * Attributes
     * NOTE: Use @SerializeName("total_number") to change the serilization rule
     */
    private int nextUserId = 0;
    private int nextEventId = 0;
    private int nextRoomId = 0;
    // private int nextMessageId = 0;
    private List<User> userList = new ArrayList<>();
    private List<Event> eventList = new ArrayList<>();
    private List<Room> roomList = new ArrayList<>();
    private List<Message> messageList = new ArrayList<>();


    /**
     * === Getter & Setter ===
     */

    /** Return the next user id and self increase by 1 */
    public int getNextUserId() {
        return nextUserId++;
    }

    /** Return the next event id and self increase by 1 */
    public int getNextEventId() {
        return nextEventId++;
    }

    /** Return the next room id and self increase by 1 */
    public int getNextRoomId() {
        return nextRoomId++;
    }

    /** Return the next message id and self increase by 1 */

    /**
    * @Description: Get user by given id
    * @Param: [id]
    * @return: User
    * @Author:
    * @Date: 2020-11-11
    */
    public User getUserById(int id) {
        ArrayList<User> userList = (ArrayList<User>) getUserList();
        for (User u : userList) {
            if (u.getUser_id() == id) {
                return u;
            }
        }
        return null;
    }


    /**
    * @Description: get User by given username
    * @Param: [username]
    * @return: User
    * @Author:
    * @Date: 2020-11-13
    */
    public User getUserByUserName(String username) {
        ArrayList<User> userList = (ArrayList<User>) getUserList();
        for (User u : userList) {
            if (u.getUserName().equals(username)) {
                return u;
            }
        }
        return null;
    }

    /**
    * @Description: Return Attendee by given <id>, if the user does not exist or is not Attendee, return null
    * @Param: [id]
    * @return: Attendee
    * @Author: 
    * @Date: 2020-11-12
    */
    public Attendee getAttendeeById(int id) {
        User user = this.getUserById(id);
        if (user.getClass().equals(Attendee.class)) {
            return (Attendee)user;
        }
        return null;
    }
    
    /**
    * @Description: get attendee by given username
    * @Param: [username]
    * @return: Attendee
    * @Author: 
    * @Date: 2020-11-13
    */
    public Attendee getAttendeeByUserName(String username) {
        User user = this.getUserByUserName(username);
        if (user.getClass().equals(Attendee.class)) {
            return (Attendee)user;
        }
        return null;
    }
    
    /**
    * @Description: Return Speaker by given <id>, if the user does not exist or is not Speaker, return null
    * @Param: [id]
    * @return: Speaker
    * @Author:
    * @Date: 2020-11-12
    */
    public Speaker getSpeakerById(int id) {
        User user = this.getUserById(id);
        if (user.getClass().equals(Speaker.class)) {
            return (Speaker)user;
        }
        return null;
    }

    /**
    * @Description: get speaker by given username
    * @Param: [username]
    * @return: Speaker
    * @Author: 
    * @Date: 2020-11-13
    */
    public Speaker getSpeakerByUserName(String username) {
        User user = this.getUserByUserName(username);
        if (user.getClass().equals(Speaker.class)) {
            return (Speaker)user;
        }
        return null;
    }
    /**
    * @Description: 
    * @Param: [username]
    * @return: Organizer
    * @Author: 
    * @Date: 2020-11-13
    */
    public Organizer getOrganizerByUserName(String username) {
        User user = this.getUserByUserName(username);
        if (user.getClass().equals(Organizer.class)) {
            return (Organizer)user;
        }
        return null;
    }

    /**
    * @Description: Return Organizer by given <id>, if the user does not exist or is not Organizer, return null
    * @Param: [id]
    * @return: Organizer
    * @Author:
    * @Date: 2020-11-12
    */
    public Organizer getOrganizerById(int id) {
        User user = this.getUserById(id);
        if (user.getClass().equals(Organizer.class)) {
            return (Organizer)user;
        }
        return null;
    }

    /**
    * @Description: get List of all users
    * @Param: []
    * @return: java.util.List<User>
    * @Author:
    * @Date: 2020-11-11
    */
    public List<User> getUserList() {
        return userList;
    }
    /**
    * @Description: add user to user list
    * @Param: [user]
    * @return: void
    * @Author: 
    * @Date: 2020-11-12
    */
    public void addUser(User user) {   
        this.userList.add(user);
    }

    
    /**
    * @Description: get list of all events
    * @Param: []
    * @return: java.util.List<Event>
    * @Author:
    * @Date: 2020-11-11
    */
    public List<Event> getEventList() {
        return eventList;
    }
    /**
    * @Description: get event by given id
    * @Param: [id]
    * @return: Event
    * @Author:
    * @Date: 2020-11-11
    */
    public Event getEventById(int id) {
        ArrayList<Event> eventList = (ArrayList<Event>) getEventList();
        for (Event e : eventList) {
            if (e.getEvent_id() == id) {
                return e;
            }
        }
        return null;
    }
    /**
    * @Description: add event to event list
    * @Param: [event]
    * @return: void
    * @Author:
    * @Date: 2020-11-12
    */
    public void addEvent(Event event) {
        this.eventList.add(event);
    }

    /**
    * @Description: get list of rooms
    * @Param: []
    * @return: java.util.List<Room>
    * @Author:
    * @Date: 2020-11-11
    */
    public List<Room> getRoomList() {
        return roomList;
    }
    /**
    * @Description: get room by given id
    * @Param: [id]
    * @return: Room
    * @Author:
    * @Date: 2020-11-11
    */
    public Room getRoomById(int id) {
        ArrayList<Room> roomList = (ArrayList<Room>) getRoomList();
        for (Room r : roomList) {
            if (r.getRid() == id) {
                return r;
            }
        }
        return null;
    }
    /**
    * @Description: get room by given roomNum
    * @Param: [roomNum]
    * @return: Room
    * @Author: 
    * @Date: 2020-11-14
    */
    public Room getRoomByRoomNum(String roomNum) {
        ArrayList<Room> roomList = (ArrayList<Room>) getRoomList();
        for (Room r : roomList) {
            if (r.getRoom_num().equals(roomNum)) {
                return r;
            }
        }
        return null;
    }
    /**
    * @Description: add room to room list
    * @Param: [room]
    * @return: void
    * @Author: 
    * @Date: 2020-11-12
    */
    public void addRoom(Room room) {
        this.roomList.add(room);
    }

    /**
    * @Description: get List of all messages
    * @Param: []
    * @return: java.util.List<Message>
    * @Date: 2020-11-11
    */
    private List<Message> getMessageList() {
        return messageList;
    }

    /**
    * @Description: Return the List of messages related to <userId>; Cannot add message
    * @Param: [userId]
    * @return: java.util.List<Message>
    * @Date: 2020-11-11
    */
    public List<Message> getAllMessageListByUserId(int userId) {
        ArrayList<Message> messageList = (ArrayList<Message>) getMessageList();
        List<Message> ret = new ArrayList<>();
        for (Message m : messageList) {
            if (m.getReceiverId() == userId || m.getSenderId() == userId) {
                ret.add(m);
            }
        }
        return ret;
    }
    /**
    * @Description: Return the List of Sent messages related to <userId>; Cannot add message
    * @Param: [userId]
    * @return: java.util.List<Message>
    * @Author:
    * @Date: 2020-11-14
    */
    public List<Message> getSentMessageListByUserId(int userId) {
        ArrayList<Message> messageList = (ArrayList<Message>) getMessageList();
        List<Message> ret = new ArrayList<>();
        for (Message m : messageList) {
            if (m.getSenderId() == userId) {
                ret.add(m);
            }
        }
        return ret;
    }
    /**
    * @Description: Return the List of Received messages related to <userId>; Cannot add message
    * @Param: [userId]
    * @return: java.util.List<Message>
    * @Author:
    * @Date: 2020-11-14
    */
    public List<Message> getReceivedMessageListByUserId(int userId) {
        ArrayList<Message> messageList = (ArrayList<Message>) getMessageList();
        List<Message> ret = new ArrayList<>();
        for (Message m : messageList) {
            if (m.getReceiverId() == userId) {
                ret.add(m);
            }
        }
        return ret;
    }
    /**
    * @Description: add Message to the Message List
    * @Param: [message]
    * @return: void
    * @Author: 
    * @Date: 2020-11-12
    */
    public void addMessage(Message message) {
        this.messageList.add(message);
    }

    public void firstOrganizer() {
        Organizer o= new Organizer(getNextUserId(), "password", "user");
        addUser(o);
    }

// This method is used for test
//    public void printDataBase () {
//        System.out.println("DataBase: Data for testing");
//        System.out.println("---------- ---------- ---------- ----------");
//        System.out.println("+ UserNextId: " + this.nextUserId);
//        System.out.println("+ EventNextId: " + this.nextEventId);
//        System.out.println("+ RoomNextId: " + this.nextRoomId);
//        System.out.println("+ User List");
//        getUserList().forEach((u) -> System.out.println("   - " + u.toString()));
//        System.out.println("+ Event List");
//        getEventList().forEach((e) -> System.out.println("  - " + e.toString()));
//        System.out.println("+ Room List");
//        getRoomList().forEach((r) -> System.out.println("   - " + r.toString()));
//        System.out.println("+ Message List");
//        getMessageList().forEach((m) -> System.out.println("    - " + m.toString()));
//        System.out.println("---------- ---------- ---------- ----------");
//    }
}
