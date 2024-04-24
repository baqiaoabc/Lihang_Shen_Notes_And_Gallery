package gateway;

import entity.*;
import entity.event.*;

import java.util.*;

/**
 * @program: group_0173
 * @description: GatewayFacade integrate all different gateways and encapsulate as integrated gateway API
 * @author:
 * @create: 2020-12-3 10:55
 **/
public class GatewayFacade {

    /** Gateways
     *  used to connect to DB, related info should be set in Config file
     * */
    private final HashGateway<User> userGateway = new HashGateway<>(Config.NEXT_USER_ID, Config.USER_HASH, User.class, true);
    private final HashGateway<Event> eventGateway = new HashGateway<>(Config.NEXT_EVENT_ID, Config.EVENT_HASH, Event.class, true);
    private final HashGateway<Room> roomGateway = new HashGateway<>(Config.NEXT_ROOM_ID, Config.ROOM_HASH, Room.class, false);
    private final ListGateway<Message> messageGateway = new ListGateway<>(Config.MESSAGE_LIST, Message.class, false);

    /** Return the next user id and self increase by 1 */
    public int getNextUserId() {
        return userGateway.getAndIncreaseNextId();
    }

    /** Return the next event id and self increase by 1 */
    public int getNextEventId() {
        return eventGateway.getAndIncreaseNextId();
    }

    /** Return the next room id and self increase by 1 */
    public int getNextRoomId() {
        return roomGateway.getAndIncreaseNextId();
    }


    // ===== User: Hash=====
    /**
     * @Description: Add user to the remote database. It will automatically substitute the user in DB with the same uid.
     * @Param: [user]
     * @return: void
     * @Date: 2020-12-3
     */
    public void addUser(User user) {
        userGateway.add(user.getUserId(), user);
    }

    /**
    * @Description: Update User in the remote database.
    * @Param: [user]
    * @return: void
    * @Date: 2020-12-06
    */
    public void updateUser(User user) {
        userGateway.update(user.getUserId(), user);
    }

    /**
    * @Description: Delete user in the remote database
    * @Param: [user]
    * @return: void
    * @Date: 2020-12-06
    */
    public void deleteUser(User user) {
        userGateway.delete(user.getUserId());
    }

    /**
     * @Description: Get user by given id. Return none if it does not exist.
     * @Param: [id]
     * @return: User
     * @Date: 2020-12-3
     */
    public User getUserById(int id) {
        return userGateway.get(id);
    }

    /**
     * @Description: Get the User List. *This is method may lag the performance.
     * @Param: []
     * @return: java.util.List<User>
     * @Author:
     * @Date: 2020-12-3
     */
    public List<User> getUserList() {
        return userGateway.getList();
    }

    /**
     * @Description: Get User by given username. Return none if it does not exist. *This is method may lag the performance. Then use `getUserById` instead.
     * @Param: [username]
     * @return: User
     * @Date: 2020-11-28
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
     * @Description: Get Attendee by given id. Return none if it does not exist.
     * @Param: [id]
     * @return: Attendee
     * @Date: 2020-11-12
     */
    public Attendee getAttendeeById(int id) {
        User user = this.getUserById(id);
        if (user.getClass().equals(Attendee.class) | user.getClass().equals(VipUser.class)) {
            return (Attendee)user;
        }
        return null;
    }

    /**
     * @Description: Get attendee by given username. Return none if it does not exist.
     * @Param: [username]
     * @return: Attendee
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
     * @Description: Get Speaker by given id. Return none if it does not exist.
     * @Param: [id]
     * @return: Speaker
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
     * @Description: Get speaker by given username. Return none if it does not exist.
     * @Param: [username]
     * @return: Speaker
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
     * @Description: Get organizer by given username. Return none if it does not exist.
     * @Param: [username]
     * @return: Organizer
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
     * @Description: Get Organizer by given id. Return none if it does not exist.
     * @Param: [id]
     * @return: Organizer
     * @Date: 2020-11-12
     */
    public Organizer getOrganizerById(int id) {
        User user = this.getUserById(id);
        if (user.getClass().equals(Organizer.class)) {
            return (Organizer)user;
        }
        return null;
    }


    // ===== Event: Hash=====
    /**
     * @Description: Add event to the remote database.
     * @Param: [event]
     * @return: void
     * @Date: 2020-12-3
     */
    public void addEvent(Event event) {
        eventGateway.add(event.getEventId(), event);
    }

    /**
    * @Description: Update event in the remote database.
    * @Param: [event]
    * @return: void
    * @Date: 2020-12-06
    */
    public void updateEvent(Event event) {
        eventGateway.update(event.getEventId(), event);
    }

    /**
    * @Description: Delete event in the remote database.
    * @Param: [event]
    * @return: void
    * @Date: 2020-12-06
    */
    public void deleteEvent(Event event) {
        eventGateway.delete(event.getEventId());
    }

    /**
     * @Description: Get list of all events. *This is method may lag the performance.
     * @Param: []
     * @return: java.util.List<Event>
     * @Date: 2020-12-3
     */
    public List<Event> getEventList() {
        return eventGateway.getList();
    }

    /**
     * @Description: Get event by given id. Return none if it does not exist.
     * @Param: [id]
     * @return: Event
     * @Date: 2020-12-3
     */
    public Event getEventById(int id) {
        return eventGateway.get(id);
    }

    /**
    * @Description: Get multi-speakers of the event by given id. Return none if it does not exist.
    * @Param: [id]
    * @return: entity.event.MultiSpeakerEvent
    * @Date: 2020-12-06
    */
    public MultiSpeakerEvent getMultiSpeakerEventById(int id) {
        Event event = getEventById(id);
        if (event instanceof MultiSpeakerEvent) {
            return (MultiSpeakerEvent)event;
        }
        return null;
    }

    /**
    * @Description: Get none-speakers of the event by given id. Return none if it does not exist.
    * @Param: [id]
    * @return: entity.event.NonSpeakerEvent
    * @Date: 2020-12-06
    */
    public NonSpeakerEvent getNonSpeakerEventById(int id) {
        Event event = getEventById(id);
        if (event instanceof NonSpeakerEvent) {
            return (NonSpeakerEvent)event;
        }
        return null;
    }

    /**
    * @Description: Get one-speakers of the event by given id. Return none if it does not exist.
    * @Param: [id]
    * @return: entity.event.OneSpeakerEvent
    * @Date: 2020-12-06
    */
    public OneSpeakerEvent getOneSpeakerEventById(int id) {
        Event event = getEventById(id);
        if (event instanceof OneSpeakerEvent) {
            return (OneSpeakerEvent)event;
        }
        return null;
    }

    // ===== Room: Hash=====
    /**
     * @Description: Add room to the remote database.
     * @Param: [room]
     * @return: void
     * @Author:
     * @Date: 2020-12-3
     */
    public void addRoom(Room room) {
        roomGateway.add(room.getRid(), room);
    }

    /**
    * @Description: Update room in the remote database.
    * @Param: [room]
    * @return: void
    * @Date: 2020-12-06
    */
    public void updateRoom(Room room) {
        roomGateway.update(room.getRid(), room);
    }

    /**
    * @Description: Delete the room in the remote databse.
    * @Param: [room]
    * @return: void
    * @Date: 2020-12-06
    */
    public void deleteRoom(Room room) {
        roomGateway.delete(room.getRid());
    }

    /**
     * @Description: Get list of rooms
     * @Param: []
     * @return: java.util.List<Room>
     * @Date: 2020-12-3
     */
    public List<Room> getRoomList() {
        return roomGateway.getList();
    }

    /**
     * @Description: Get room by given id. Return none if it does not exist.
     * @Param: [id]
     * @return: Room
     * @Date: 2020-12-3
     */
    public Room getRoomById(int id) {
        return roomGateway.get(id);
    }

    /**
     * @Description: Get room by given roomNum. Return none if it does not exist.
     * @Param: [roomNum]
     * @return: Room
     * @Date: 2020-11-28
     */
    public Room getRoomByRoomNum(String roomNum) {
        ArrayList<Room> roomList = (ArrayList<Room>) getRoomList();
        for (Room r : roomList) {
            if (r.getRoomNum().equals(roomNum)) {
                return r;
            }
        }
        return null;
    }

    // ===== Message: List =====
    /**
     * @Description: add Message to the Message List
     * @Param: [message]
     * @return: void
     * @Date: 2020-12-3
     */
    public void addMessage(Message message) {
        messageGateway.add(message);
    }

    /**
     * @Description: Get List of all messages
     * @Param: []
     * @return: java.util.List<Message>
     * @Date: 2020-12-3
     */
    public List<Message> getMessageList() {
        return messageGateway.getList();
    }

    /**
     * @Description: Return the List of messages related to userId. Do not directly add message to this list!
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
     * @Description: Return the List of Sent messages related to userId. Do not directly add message to this list!
     * @Param: [userId]
     * @return: java.util.List<Message>
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
     * @Description: Return the List of Received messages related to userId. Do not directly add message to this list!
     * @Param: [userId]
     * @return: java.util.List<Message>
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
}