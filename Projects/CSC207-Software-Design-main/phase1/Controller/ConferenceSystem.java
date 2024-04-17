import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ConferenceSystem {

    /**
     * The ConferenceSystem Class.
     */
    private EventManager em = new EventManager();
    private MessageManager mm = new MessageManager();
    private RoomManager rm = new RoomManager();
    private UserManager um = new UserManager();
    private DataBase db;
    private Gateway gw = new Gateway();
    private int user; // store current logged in user's id


    /**
    * @Description: Initialization of Gateway and Databse
    * @Param: []
    * @return: void
    * @Date: 2020-11-14
    */
    public void init () throws IOException {
        shutDownHook();
        /** initial of database */
        this.db = gw.init();
    }
    /**
    * @Description: Hook for listening the Shutdown of program and save all the data before exist0
    * @Param: []
    * @return: void
    * @Date: 2020-11-14
    */
    private void shutDownHook() {
        Runtime run = Runtime.getRuntime();
        run.addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    System.out.println("System: ShutDownHook");
                    gw.termination(db);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Sign up a new attendee to the system.
     *
     * @param username User name of the new attendee. username should be unique.
     * @param password Password of the new attendee account. Password should be at least 6 characters long.
     * @return Return true if the account is created successfully, false otherwise.
     */
    public boolean signup(String username, String password){
        if (um.canCreateAttendee(username, db) && password.length() >= 6){
            um.createAttendee(password, username, db);
            return true;
        }
        return false;
    }

    /**
     * Logs in a user.
     *
     * @param username User name of the user attempting to log in
     * @param password Password of the user input to attempt to log in
     * @return Returns -1 when login fails. When the login is successful,
     *         returns 0 when the user is a Speaker;
     *         returns 1 when the user is an Organizer;
     *         returns 2 when the user is an Attendee.
     */
    public int login(String username, String password){
        if (um.isExistingUser(username, db)){
            String dbPassword = um.getUserPassword(username, db);
            if (dbPassword.equals(password)){
                this.user = um.getUserID(username, db);
                return um.getUserCategory(this.user, db);
            }
        }
        return -1;
    }

    /**
     * Resets the password for the logged in user. Checks if the password is 6 characters or longer.
     *
     * @param newPassword New password input by user.
     * @return Returns True when the password is valid and is changed. False otherwise.
     */
    public boolean resetPassword(String newPassword){
        // passwords should always be 6 characters or longer
        if (newPassword.length() >= 6){
            um.setPassword(user, newPassword, db);
            return true;
        }
        return false;
    }

//    /**
//     * Get current logged in user's name.
//     *
//     * @return Returns the user name.
//     */
//    public String getCurrentUserName(){
//        return um.getUserName(user, db);
//    }

    /**
     * Get user ID by user name.
     *
     * @param username User's name
     * @return Return user's ID
     */
    public String getUserIDbyUserName(String username){
        return Integer.toString(um.getUserID(username, db));
    }

    /**
     * Get user name by user ID.
     *
     * @param userID User's ID
     * @return Return user name
     */
    public String getUserNameByID(String userID){
        int uID = Integer.parseInt(userID);
        return um.getUserName(uID, db);
    }

    /**
     * Allow user to message all attendees in an event.
     *
     * @param eventID The event that all attendees and the speaker is in.
     * @param content The content of the message.
     * @return Return true if the message is sent successfully, false when input is invalid.
     */
    public boolean messageAllAttendeesInEvent(String eventID, String content){
        try{
            int eID = Integer.parseInt(eventID);
            if (em.isExistingEvent(eID, db)){
                mm.message_allusers(eID, user, content, db);
                return true;
            }
            return false;
        }
        catch(NumberFormatException nfe){
            return false;
        }
    }

    /**
     * Allow speaker to message all users in all events speaker is speaking in.
     *
     * @param content Content of the message
     * @return Return true if the message is sent successfully, else return false.
     */
    public boolean messageAllUsersInAllSpeakingEvents(String content){
        if (um.isExistingSpeaker(user, db)){
            mm.messageAllUsersInAllSpeakingEvents(user, content, db);
            return true;
        }
        return false;
    }

    /**
     * Allow user to message a specific attendee in an event
     *
     * @param eventID The event the attendees and speaker is in.
     * @param receiverID The receiver's ID.
     * @param content Content of the message.
     * @return Return true if the message is sent successfully, false when input is invalid.
     */
    public boolean messageOneSpecificUserInEvent(String eventID, String receiverID, String content){
        try{
            int eID = Integer.parseInt(eventID);
            int reID = Integer.parseInt(receiverID);
            if (em.isExistingEvent(eID, db) && mm.canMessageAttendeeOfEvent(eID, reID, db)){
                mm.message_oneuser(user, reID, content, db);
                return true;
            }
            return false;
        }
        catch(NumberFormatException nfe){
            return false;
        }
    }

    /**
     * Sends a message to all speakers at once. Only Organizers can perform such action.
     *
     * @param content Content of the message.
     * @return Return true if messages are sent successfully. False if the logged in user is not an organizer.
     */
    public boolean messageAllSpeakers(String content){
        if (mm.canMessageAllSpeakersOrAllAttendee(user, db)){
            mm.messageAllSpeakers(content, user, db);
            return true;
        }
        return false;
    }

    /**
     * Sends a message to a specific speaker.
     *
     * @param receiverID The receiver's (speaker) ID.
     * @param content Content of the message
     * @return Return true if the message is sent successfully. False if the input(ID) is invalid, or the user
     * is not allowed to message the speaker.
     */
    public boolean messageSpeaker(String receiverID, String content){
        try{
            int rID = Integer.parseInt(receiverID);
            if (mm.canMessageSpeaker(user, rID, db)){
                mm.message_oneuser(user, rID, content, db);
                return true;
            }
            return false;
        }
        catch(NumberFormatException nfe){
            return false;
        }
    }

    /**
     * Sends a message to all attendees in the system. This action can only be performed by an organizer.
     *
     * @param content Content of the message.
     * @return Return true if the messages are successfully sent. False if the logged in sender is not allowed to
     * perform this action.
     */
    public boolean messageAllAttendee(String content){
        if(mm.canMessageAllSpeakersOrAllAttendee(user, db)){
            mm.messageAllAttendees(user, content, db);
            return true;
        }
        return false;
    }

    /**
     * Sends a message to an attendee.
     *
     * @param receiverID Message receiver's ID.
     * @param content Content of the message.
     * @return Return true if the message is sent successfully. False if the user is not allowed to message this
     * attendee, or input is invalid.
     */
    public boolean messageAttendee(String receiverID, String content){
        try{
            int rID = Integer.parseInt(receiverID);
            if (mm.canMessageAttendee(user, rID, db)){
                mm.message_oneuser(user, rID, content, db);
                return true;
            }
            return false;
        }
        catch(NumberFormatException nfe){
            return false;
        }
    }

    /**
     * Reads the sent messages of the user currently logged in.
     *
     * @return List of Strings representing the messages the user sent.
     */
    public List<String> readSentMessages(){
        return mm.getSentMessageListByUserId(user, db);
    }

    /**
     * Reads the incoming messages of the user currently logged in.
     *
     * @return List of Strings representing the messages the user reveived.
     */
    public List<String> readReceivedMessages(){
        return mm.getReceivedMessageListByUserId(user, db);
    }

    /**
     * Reply to a specific message.
     *
     * @param messageIndex Position of the message (to identify which message it is replying)
     * @param content Content of the replying message.
     * @return Return true when the message is successfully sent, false if the user is not allowed to reply to the
     * message.
     */
    public boolean replyMessage(String messageIndex, String content){
        try{
            int mIndex = Integer.parseInt(messageIndex);
            if (mm.canReplyMessage(user, mIndex, db)){
                mm.replyMessage(content, user, mIndex, db);
                return true;
            }
            return false;
        }
        catch(NumberFormatException nfe){
            return false;
        }
    }

    /**
     * Allow current logged in user to sign up to an event.
     *
     * @param eventID ID of the event signing up for.
     * @return Return True if user is successfully signed up for the event, false otherwise.
     */
    public boolean signUpForEvent(String eventID) {
        try{
            int eid = Integer.parseInt(eventID);
            // check if the event exists, and user can sign up for event
            if (em.canAddUserToEvent(user, eid, db) && um.canSignUpForEvent(eid, user, db)){ //need confirm
                um.addEventToAttendeeOrOrganizer(eid, user, db);
                em.addUserToEvent(user, eid, db);
                return true;
            }
            // return false when event doesn't exist or user cannot sign up for event
            return false;
        }
        catch(NumberFormatException nfe){
            // return false when input is invalid
            return false;
        }
    }


    /**
     * Allow current logged in attendee/organizer to cancel their enrollment in an event.
     *
     * @param eventID ID of the event they want to deregister from.
     * @return Return True when the user has successful cancelled their enrollment in the event.
     */
    public boolean cancelEnrollmentInEvent(String eventID){
        try{
            int eid = Integer.parseInt(eventID);
            if (em.canRemoveUser(this.user ,eid, db)){
                em.removeUser(this.user, eid, db);
                um.cancelEventToAttendeeOrOrganizer(eid, this.user, db);
                return true;
            }
            return false;
        }
        catch(NumberFormatException nfe){
            return false;
        }

    }

    /**
     * Creates a new speaker account into the system.
     *
     * @param userName User name of the new speaker.
     * @param password Password of the new speaker account.
     * @return Return true when the account is created successfully.
     *          Return false when the password is invalid,
     *          or when the user name is not unique.
     */
    public boolean addNewSpeaker(String userName, String password){
        if (password.trim().length() >=6 && userName.trim().length() > 0 && um.canCreateSpeaker(userName, db)){
            um.createSpeaker(password.trim(), userName.trim(), db);
            return true;
        }
        return false;
    }

    /**
     * Set the speaker of an event.
     *
     * @param speakerID New Speaker's ID
     * @param eventID The event's ID
     * @return Return True when the speaker is assigned to the event successfully, false otherwise.
     */
    public boolean setSpeakerForEvent(String speakerID, String eventID){
        try{
            int sID = Integer.parseInt(speakerID);
            int eID = Integer.parseInt(eventID);
            if(um.canAddEventToSpeaker(eID, sID, db)){
                um.addEventToSpeaker(eID, sID, db);
                em.setSpeaker(sID, eID, db);
                return true;
            }
            return false; // return false when cannot add event to speaker
        }
        catch(NumberFormatException nfe){
            return false; // return false on invalid input
        }
    }

    /**
     * Creates a new room into the system.
     *
     * @param roomNumber Room number of the new room.
     * @return Return true when successfully added a new room, false otherwise.
     */
    public boolean addNewRoom(String roomNumber){
        try{
            if (rm.canAddRoom(roomNumber, db)){
                rm.add_room(roomNumber, db);
                return true;
            }
            return false;
        }
        catch(NumberFormatException nfe){
            return false;
        }
    }

    /**
     * Creates a new event into the system.
     *
     * @param startTime Start time of the event. In the format of "yyyy-MM-dd HH". Example: "2020-11-14 18"
     * @param speakerID ID of speaker giving the event.
     * @param topic Topic/title of the event.
     * @param roomNumber Room number of the location of this event.
     * @return Return true if successfully created a new event into the system, false otherwise.
     */
    public boolean newEvent(String startTime, String speakerID, String topic, String roomNumber){
        try{
            LocalDateTime sTime = LocalDateTime.parse(startTime, em.getStartTimeFormatter());
            int sID = Integer.parseInt(speakerID);
            int rID = rm.getRoomIDbyRoomNumber(roomNumber, db);
            if (um.isExistingSpeaker(sID, db) && em.canCreateEvent(rID, sTime, db) && um.isSpeakerBusy(sID,sTime,db)){
                int eventID = em.createEvent(sTime, sID, topic, rID, db);
                um.addEventToOrganizedList(eventID, user, db);
                um.addEventToSpeaker(eventID, sID, db);
                return true;
            }
            return false; // return false when unsuccessful
        }
        catch(DateTimeParseException | NullPointerException ex){
            return false; // return false on invalid input
        }
    }

    /**
     * Return all events that are currently scheduled.
     *
     * @return List of Strings of the events
     */
    public List<String> viewEvents(){
        List<Integer> events = em.getEventList(db);
        List<String> sEvents = new ArrayList<>();
        for (Integer eventID : events){
            sEvents.add(em.getStringOfEvent(eventID, db));
        }
        return sEvents;
    }

    /**
     * Return a list of events that the current logged in user has signed up for.
     *
     * @return List of Strings of the events
     */
    public List<String> viewSignedUpEvents(){
        List<Integer> events = um.getOrganizerOrAttendeeEventList(user, db);
        List<String> sEvents = new ArrayList<>();
        for (Integer eventID : events){
            sEvents.add(em.getStringOfEvent(eventID, db));
        }
        return sEvents;
    }

    /**
     * Return a list of events that the current logged in organizer has organized
     *
     * @return List of Strings of the events
     */
    public List<String> viewOrganizedEvents(){
        List<Integer> events = um.getOrganizedEventList(user, db);
        List<String> sEvents = new ArrayList<>();
        for (Integer eventID : events){
            sEvents.add(em.getStringOfEvent(eventID, db));
        }
        return sEvents;
    }

    /**
     * Return a list of events that the current Speaker is going to speak for.
     *
     * @return List of Strings of the events
     */
    public List<String> viewSpeakingEvents(){
        List<Integer> events = um.getSpeakerEventList(user, db);
        List<String> sEvents = new ArrayList<>();
        for (Integer eventID : events){
            sEvents.add(em.getStringOfEvent(eventID, db));
        }
        return sEvents;
    }

    /**
     * Return a list of events that the current logged in attendee can sign up for.
     *
     * @return List of Strings of the events
     */
    public List<String> viewCanSignUpEvents(){
        List<Integer> allEvents = em.getEventList(db);
        List<String> events = new ArrayList<>();
        for (Integer eventID : allEvents){
            if (um.canSignUpForEvent(eventID, this.user, db) && em.canAddUserToEvent(user, eventID, db)){
                events.add(em.getStringOfEvent(eventID, db));
            }
        }
        return events;
    }

    /**
     * View all attendees who are registered in one of the current speaker's speaking events. Without duplicates.
     *
     * @return Return a list of Strings that represent all attendees of all the events the speaker is speaking for.
     * Every attendee is represented by a string formated as follows: "UserName (userID)"
     */
    public List<String> viewAttendeesInSpeakingEvents(){
        List<Integer> allSpeakingEvents = um.getSpeakerEventList(user, db);
        Set<Integer> allAttendeesInEvents = new LinkedHashSet<>();
        List<String> sAllAttendeesInEvents = new ArrayList<>();
        for (Integer eventID : allSpeakingEvents){
            List<Integer> usersInEvent = em.getUserList(eventID, db);
            allAttendeesInEvents.addAll(usersInEvent);
        }
        for (Integer userID : allAttendeesInEvents){
            sAllAttendeesInEvents.add(um.getUserString(userID, db));
        }
        return sAllAttendeesInEvents;
    }

    /**
     * View all attendees in the system.
     *
     * @return Return a list of Strings that represent all attendees in the system.
     * Every attendee is represented by a string formatted as follows: "UserName (userID)"
     */
    public List<String> viewAllAttendees(){
        List<Integer> allAttendees = um.getListOfUsers(2, db);
        List<String> sAllAttendees = new ArrayList<>();
        for (Integer userID : allAttendees){
            sAllAttendees.add(um.getUserString(userID, db));
        }
        return sAllAttendees;
    }

    /**
     * View all speakers in the system.
     *
     * @return Return a list of Strings that represent all speakers in the system.
     * Every speaker is represented by a string formatted as follows: "UserName (userID)"
     */
    public List<String> viewAllSpeakers(){
        List<Integer> allSpeakers = um.getListOfUsers(0, db);
        List<String> sAllSpeakers = new ArrayList<>();
        for (Integer userID : allSpeakers){
            sAllSpeakers.add(um.getUserString(userID, db));
        }
        return sAllSpeakers;
    }

    /**
     * View all rooms in the system.
     *
     * @return Return a list of strings that represent all rooms in the system.
     * Every room is represented by a string formatted as follows: "RoomName/Number (RoomID)"
     */
    public List<String> viewAllRooms(){
        List<Room> allRooms = rm.getListOfRooms(db);
        List<String> sAllRooms = new ArrayList<>();
        for (Room r: allRooms) {
            sAllRooms.add(rm.getRoomString(r.getRid(),db));
        }
        return sAllRooms;
    }
}
