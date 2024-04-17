import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

//!!!ADD FUNCTION NAMES IF YOU THINK NECESSARY!!!!!!
// FEEL FREE TO ADD THEM!!! THANKSSSS :)

/**
 * The Event Manager class
 */
public class EventManager {

    //don't need these instance
//    private int event_id;
//    private int speaker_id;
//    private String title;
//
//don't need these setter
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public void setEventId(int event_id) {
//        this.event_id = event_id;
//    }
//
//    public void setSpeakerId(int speaker_id) {
//        this.speaker_id = speaker_id;
//    }
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");

    /**
     * Judge whether the new event can be created
     *
     * @param room_id the room id this new event will take place
     * @param start the start time of this new event
     * @param db the database
     * @return the boolean show whether the new event can be created
     */
    public boolean canCreateEvent(int room_id, LocalDateTime start, DataBase db){
        List<Event> all_event = db.getEventList();
        for (Event event : all_event) {
            if (room_id == event.getRoomId() && start.equals(event.getStart_time())) {
                return false;
            }
        }
        return true;
    }

    // return eventID of the newly created event

    /**
     * Create a new event
     * @param start the start time
     * @param speakerId the speaker id of this event
     * @param title the title of this event
     * @param roomId the room id where this event will take place
     * @param d the database
     * @return the new event id
     */
    public int createEvent(LocalDateTime start, int speakerId, String title, int roomId, DataBase d){
        Event nEvent = new Event(start, d.getNextEventId(), speakerId, title, roomId);
        d.addEvent(nEvent);
        return nEvent.getEvent_id();
    }

// this method is never used
//    public boolean addEventToDB(Event new_event, DataBase db){
//        boolean value = canCreateEvent(new_event.getRoomId(), new_event.getStart_time(), db);
//        if(value == true){
//            db.addEvent(new_event);
//            return true;
//        }
//        return false;
//    }

    /**
     * Set a new speaker to the exist event
     * @param speakerId the new speaker id
     * @param eventId the event id
     * @param d the database
     */
    public void setSpeaker(int speakerId, int eventId, DataBase d){
        d.getEventById(eventId).setSpeaker_id(speakerId);

    }

    /**
     * Judge whether a user can sign up to an event
     *
     * @param userid the userid
     * @param eventid the event id
     * @param d the database
     * @return the boolean shows whether a user can sign up to an event
     */
    public boolean canAddUserToEvent(int userid, int eventid, DataBase d) {
        if (!isExistingEvent(eventid, d)) {
            return false;
        }
        else {
            Event e = d.getEventById(eventid);
            if (e.getSingned_userid().contains(userid)
                    | d.getRoomById(e.getRoomId()).getCapacity() <= e.getSingned_userid().size()) {
                return false;
            }
            return true;
        }
    }

    /**
     * Add user to an event
     * @param userId the user id
     * @param eventId the event id
     * @param d the database
     */
    public void addUserToEvent(int userId, int eventId, DataBase d){
        d.getEventById(eventId).add_user(userId);
    }

    /**
     * Judge whether a user can be removed by an event
     * @param userid the user id
     * @param eventid the event id
     * @param d the database
     * @return the boolean whether a user can be removed by an event
     */
    public boolean canRemoveUser(int userid, int eventid, DataBase d) {
        if (isExistingEvent(eventid, d)) {
            return d.getEventById(eventid).getSingned_userid().contains(userid);
        }
        return false;
    }

    /**
     * Remove the user from an event
     * @param userId the user id
     * @param eventId the event id
     * @param d the database
     */
    public void removeUser(int userId, int eventId, DataBase d) {
        d.getEventById(eventId).remove_user(userId);
    }

    // methods never used
//    public LocalDateTime getStart_time(Event event) {
//        return event.getStart_time();
//    }
//
//    public int getSpeakerId(Event event) {
//        return event.getSpeakerId();
//    }
//
//    public String getTitle(Event event) {
//        return event.getTitle();
//    }
//
//    public int getRoomId(Event event) {
//        return event.getRoomId();
//    }
//
//    public int getEvent_id(Event event) {
//        return event.getEvent_id();
//    }

//    public List<User> getUserList(Event event, DataBase bd){
//        ArrayList<User> all_User= new ArrayList<>();
//        ArrayList<Integer> allUserID = event.getSingned_userid();
//        for (Integer integer : allUserID) {
//            all_User.add(bd.getUserById(integer));
//        }
//        return all_User;
//    }

    /**
     * A getter for the all signed up user of and event
     *
     * @param eventID the event id
     * @param db the database
     * @return all signed up user of and event
     */
    public List<Integer> getUserList(int eventID, DataBase db){
        Event event = db.getEventById(eventID);
        return event.getSingned_userid();
    }

//    public List<Event> getEventList(DataBase bd) {
//        ArrayList<Event> all_Events = new ArrayList<>();
//        List<Event> events = bd.getEventList();
//        for (Event e : events) {
//            all_Events.add(bd.getEventById(e.getEvent_id()));
//        }
//        return all_Events;
//    }

    /**
     * A getter for ids of all events in the database
     * @param db the database
     * @return the list of ids of all events in the database
     */
    public List<Integer> getEventList(DataBase db){
        List<Integer> allEvents = new ArrayList<>();
        List<Event> events = db.getEventList();
        for (Event event : events){
            allEvents.add(event.getEvent_id());
        }
        return allEvents;
    }

    public String getStringOfEvent(int eventID, DataBase db){
        Event event = db.getEventById(eventID);
        return "The event " + event.getTitle() +
                " with ID " + event.getEvent_id() +
                " by " + db.getSpeakerById(event.getSpeakerId()).getUserName() +
                " starts at " + event.getStart_time().format(getStartTimeFormatter()) +
                " takes place in " + db.getRoomById(event.getRoomId()).getRoom_num();
    }

    /**
     * A getter for the event start time format
     *
     * @return the event start time format
     */
    public DateTimeFormatter getStartTimeFormatter(){
        return this.formatter;
    }

    /**
     * Judge whether the event is in the database
     * @param eventID event id
     * @param db the database
     * @return the boolean shows whether the event is in the database
     */
    public boolean isExistingEvent(int eventID, DataBase db){
        return db.getEventById(eventID) != null;
    }
}



//    read events from database and create map of events with key (room) and value (event) sorted by time.
//        create a new event if it is not conflict with current events.
//        save new event to database and add it to list.
//        set the Speaker for event
//        add new signed up user to the event.
//        delete current signed up user from event.
//        getter for start time, room, speaker, user list
//        getter for the event list