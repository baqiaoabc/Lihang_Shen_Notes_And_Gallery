import javax.xml.crypto.Data;
import java.sql.DatabaseMetaData;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class UserManager {


    public boolean canCreateSpeaker(String username, DataBase db){
        /**
         * @Description: checks if username is unique
         */
        List<User> users = db.getUserList();
        for (User user : users){
            if (user.getUserName().equals(username)){
                return false;
            }
        }
        return true;
    }

    public void createSpeaker(String password, String name, DataBase d){
        /**
         * @Description: create a Speaker account
         */
        Speaker s = new Speaker(d.getNextUserId(), password, name);
        d.addUser(s);
    }

    public boolean canCreateAttendee(String username, DataBase db){
        /**
         * @Description: judge whether a username is available for a new Attendee account
         */
        return db.getUserByUserName(username) == null;
    }

    public void createAttendee(String password, String name ,DataBase d) {
        /**
         * @Description: create a Attendee account
         */
        Attendee a = new Attendee(d.getNextUserId(), password, name);
        d.addUser(a);
    }

    public boolean canAddEventToSpeaker(int eventID, int speakerId, DataBase d){
        /**
         * @Description: judge whether a Event is conflict with Events which a Speaker participate
         */
        Speaker s = d.getSpeakerById(speakerId);
        Event event = d.getEventById(eventID);
        if (s == null || event == null) {
            return false;
        }
        else {
            ArrayList<Integer> events = s.get_GivingEventList();
            for (Integer integer : events) {
                if (d.getEventById(integer).getStart_time().equals(event.getStart_time())) {
                    return false;
                }
            }
            return true;
        }
    }

    public void addEventToSpeaker(int eventId, int speakerId, DataBase d){
        /**
         * @Description: add a Event to a Speaker
         */
        removeEventFromSpeaker(eventId,d);
        d.getSpeakerById(speakerId).addGivingEvent(eventId);

    }

    private void removeEventFromSpeaker(int eventId, DataBase d) {
        /**
         * @Description: remove a Event to a Speaker
         */
        Event e = d.getEventById(eventId);
        d.getSpeakerById(e.getSpeakerId()).removeGivingEvent(eventId);
    }

    public boolean canSignUpForEvent(int eventId, int userId, DataBase d) {
        /**
         * @Description: judge whether a User is eligible to sign up an Event
         */
        Event e = d.getEventById(eventId);
        if (e == null | isExistingSpeaker(userId, d) |
                d.getRoomById(e.getRoomId()).getCapacity() <= e.getSingned_userid().size()) {
            return false;
        }
        else {
            Attendee a = (Attendee) d.getUserById(userId);
            for (int i = 0; i <  a.getEventList().size(); i++) {
                if (d.getEventById(a.getEventList().get(i)).getStart_time().equals(e.getStart_time())){
                    return false;
                }
            }
            return true;
        }
    }

    public void addEventToAttendeeOrOrganizer(int eventId, int userId, DataBase d){
        /**
         * @Description: add an Event to Attendee or Organizer
         */
        if (!isExistingAttendee(userId, d)){
            d.getOrganizerById(userId).signUpEvent(eventId);
        }
        else{
            d.getAttendeeById(userId).signUpEvent(eventId);
        }
    }

    public void cancelEventToAttendeeOrOrganizer(int eventId, int userId, DataBase d) {
        /**
         * @Description: cancel an Event for Attendee or Organizer
         */
        if (!isExistingOrganizer(userId, d)) {
            d.getAttendeeById(userId).cancelEvent(eventId);
        } else {
            d.getOrganizerById(userId).cancelEvent(eventId);
        }
    }

    /**
     * Add event to the list of organized events of an organizer
     * @param eventID Event's ID
     * @param organizerID Organizer's ID
     * @param db Database
     */
    public void addEventToOrganizedList(int eventID, int organizerID, DataBase db){
        Organizer organizer = db.getOrganizerById(organizerID);
        organizer.AddCreatedEvent(eventID);
    }

    public void setPassword(int userId,String password, DataBase d) {
        /**
         * @Description: set password for an User
         */
        d.getUserById(userId).setPassword(password);
    }

    public String getUserName(int userId,DataBase d) {
        /**
         * @Description: get User name by userid
         */
        return d.getUserById(userId).getUserName();
    }

    public ArrayList<Integer> getOrganizerOrAttendeeEventList(int Id, DataBase d){
        /**
         * @Description: get Organizer or Attendee Event list
         */
        if (isExistingOrganizer(Id, d)){
            return d.getOrganizerById(Id).getEventList();
        }else {
            return d.getAttendeeById(Id).getEventList();
        }
    }

    public List<Integer> getOrganizedEventList(int organizerID, DataBase db){
        return db.getOrganizerById(organizerID).getCreatedEventList();
    }

    public List<Integer> getSpeakerEventList(int speakerID, DataBase d){
        /**
         * @Description: get Speaker Event list
         */
        return d.getSpeakerById(speakerID).get_GivingEventList();
    }

    public String getUserPassword(String username, DataBase d){
        /**
         * @Description: get User password by username
         */
        return d.getUserByUserName(username).getPassword();
    }

    public int getUserID(String username, DataBase db){
        /**
         * @Description: get User id by username
         */
        return db.getUserByUserName(username).getUser_id();
    }

    public int getUserCategory(int id, DataBase d){
        /**
         * @Description: judge the User category, 0 means speaker, 1 means organizer, 2 means attendee
         */
        if (isExistingSpeaker(id, d)){
            // return 0 when id is a speaker
            return 0;
        }
        else if (d.getOrganizerById(id) != null){
            // return 1 when id is an organizer
            return 1;
        }
        else{
            // return 2 when id is an attendee
            return 2;
        }
    }


    public List<Integer> getListOfUsers(int userType, DataBase db){
        /**
         * @Description: get needed user category list. speaker = 0, organizer = 1, attendee = 2
         */
        List<User> users = db.getUserList();
        List<Integer> neededUsers = new ArrayList<>();
        for (User user : users){
            int userID = user.getUser_id();
            if (getUserCategory(userID, db) == userType){
                neededUsers.add(userID);
            }
        }
        return neededUsers;
    }

    public String getUserString(int userID, DataBase db){
        /**
         * @Description: get User name and id
         */
        User user = db.getUserById(userID);
        return user.getUserName() + " (" + user.getUser_id() + ")";
    }

    public boolean isSpeakerBusy(int speakerId, LocalDateTime time, DataBase d) {
        /**
         * @Description: judge whether a speaker is speaking
         */
        for (int eid :d.getSpeakerById(speakerId).get_GivingEventList()) {
            if (d.getEventById(eid).getStart_time().equals(time)) {
                return false;
            }
        }
        return true;
    }

    public boolean isExistingSpeaker(int userID, DataBase db){
        /**
         * @Description: judge a speaker is exist
         */
        return db.getSpeakerById(userID) != null;
    }

    public boolean isExistingUser(String username, DataBase db){
        /**
         * @Description: judge a User is exist
         */
        return db.getUserByUserName(username) != null;
    }

    public boolean isExistingAttendee(int userID, DataBase db){
        /**
         * @Description: judge an Attendee is exist
         */
        return db.getAttendeeById(userID) != null;
    }

    public boolean isExistingOrganizer(int userID, DataBase db){
        /**
         * @Description: judge an organizer is exist
         */
        return db.getOrganizerById(userID) != null;
    }

}