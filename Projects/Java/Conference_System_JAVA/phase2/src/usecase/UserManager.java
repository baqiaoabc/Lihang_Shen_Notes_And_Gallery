package usecase;

import entity.*;
import entity.event.Event;
import entity.event.MultiSpeakerEvent;
import entity.event.NonSpeakerEvent;
import entity.event.OneSpeakerEvent;
import gateway.GatewayFacade;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class UserManager {

    /**
     * @Description: checks if username is unique
     */
    public boolean canCreateUser(String userName, GatewayFacade gw){
        if (userName.length() > 0 && !isExistingUser(userName, gw)){
            return true;
        }
        return false;
    }

    /**
     * @Description: create a Speaker account
     */
    public void createSpeaker(String password, String name, GatewayFacade g){
        Speaker s = new Speaker(g.getNextUserId(), password, name);
        g.addUser(s);
    }

    /**
     * @Description: create an organizer
     * @param password password
     * @param name the name of the organizer
     */
    public void createOrganizer(String password, String name, GatewayFacade g){
        /**
         * @Description: create a organizer account
         * @para password of organizer account
         * @para name of organizer account
         */
        Organizer o = new Organizer(g.getNextUserId(), password, name);
        g.addUser(o);
    }

    /**
     * @Description: create a Attendee account
     */
    public void createAttendee(String password, String name ,GatewayFacade g) {
        Attendee a = new Attendee(g.getNextUserId(), password, name);
        g.addUser(a);
    }

    /**
     * @Description: create a VIP account
     */
    public void createVIP(String password, String name ,GatewayFacade g){
        VipUser vip = new VipUser(g.getNextUserId(), password, name);
        g.addUser(vip);
    }


    /**
     * @Description: judge whether a Event is conflict with Events which a Speaker participate
     */
    public boolean canAddEventToSpeaker(int eventID, int speakerId, GatewayFacade g){
        Speaker s = g.getSpeakerById(speakerId);
        Event event = g.getEventById(eventID);
        if (s == null || event == null || event instanceof NonSpeakerEvent) {
            return false;
        }
        else return !isSpeakerBusy(speakerId, event.getStartTime(), event.getEndTime(), g);
    }


    /**
     * @Description: add a Event to a Speaker
     */
    public void addEventToSpeaker(int eventId, int speakerId, GatewayFacade g){
        Event event = g.getEventById(eventId);
        if (event instanceof OneSpeakerEvent){
            removeOneSpeakerEventFromSpeaker(eventId, g);
        }
        Speaker speaker = g.getSpeakerById(speakerId);
        speaker.addGivingEvent(eventId);
        g.updateUser(speaker);
    }


    /**
     * @Description: remove a Event to a Speaker
     */
    private void removeOneSpeakerEventFromSpeaker(int eventId, GatewayFacade g) {
        OneSpeakerEvent e =  g.getOneSpeakerEventById(eventId);
        Speaker speaker = g.getSpeakerById(e.getSpeakerId());
        speaker.removeGivingEvent(eventId);
        g.updateUser(speaker);
    }


    /**
     * @Description: judge whether a User is eligible to sign up an Event
     */
    public boolean canSignUpForEvent(int eventId, int userId, GatewayFacade g) {
        Event e = g.getEventById(eventId);
        if (!(e == null || isExistingSpeaker(userId, g)
                || (!(g.getUserById(userId) instanceof VipUser) & e.isVipEvent()))) {
            Attendee a = (Attendee) g.getUserById(userId);

            for (int i = 0; i < a.getSignedUpEventList().size(); i++) {
                if (g.getEventById(a.getSignedUpEventList().get(i)).getStartTime().isBefore(e.getEndTime())
                        & g.getEventById(a.getSignedUpEventList().get(i)).getEndTime().isAfter(e.getStartTime())) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * @Description: add an Event to Attendee or Organizer
     */
    public void addEventToUser(int eventId, int userId, GatewayFacade g){
        Attendee attendee = (Attendee) g.getUserById(userId);
        attendee.signUpEvent(eventId);
        g.updateUser(attendee);
    }

    /**
     * @Description: cancel an Event for Attendee or Organizer
     */
    public void cancelEventFromUser(int eventId, int userId, GatewayFacade g) {
        Attendee attendee = (Attendee) g.getUserById(userId);
        attendee.cancelEvent(eventId);
        g.updateUser(attendee);
    }

    /**
     * @Description: transfer the Waiting Event To SignedUp
     */
    public void transferWaitingEventToSignedUp(int eventId, int userId, GatewayFacade g) {
        Attendee attendee = (Attendee) g.getUserById(userId);
        attendee.signUpEvent(eventId);
        attendee.removeWaitingEvent(eventId);
        g.updateUser(attendee);
    }

    // used by ems.changeEventCapacity & ems.changeVIPStatusOfEvent
    public void transferWaitingEventToSignedUp(int eid, List<Integer> users, GatewayFacade gw) {
        for (int userID : users){
            transferWaitingEventToSignedUp(eid, userID, gw);
        }
    }

    /**
     * @Description: add the event to the wait list
     */
    public void addEventToMyWaitList(int eventId, int userId, GatewayFacade g) {
        Attendee attendee = (Attendee) g.getUserById(userId);
        attendee.addWaitingEvent(eventId);
        g.updateUser(attendee);
    }

    /**
     * @Description: cancel event from the waitlist
     */
    public void cancelEventFromMyWaitList(int eventId, int userId, GatewayFacade g) {
        Attendee attendee = (Attendee) g.getUserById(userId);
        attendee.removeWaitingEvent(eventId);
        g.updateUser(attendee);
    }

    /**
     * Add event to the list of organized events of an organizer
     * @param eventID Event's ID
     * @param organizerID Organizer's ID
     * @param g Database
     */
    public void addEventToOrganizedList(int eventID, int organizerID, GatewayFacade g){
        Organizer organizer = g.getOrganizerById(organizerID);
        organizer.AddCreatedEvent(eventID);
        g.updateUser(organizer);
    }

    /**
     * @Description: set password for an User
     */
    public void setPassword(int userId,String password, GatewayFacade g) {
        User user = g.getUserById(userId);
        user.setPassword(password);
        g.updateUser(user);
    }

    /**
     * @Description: get User name by userid
     */
    public String getUserName(int userId, GatewayFacade g) {
        return g.getUserById(userId).getUserName();
    }

    /**
     * @Description: get Organizer or Attendee Event list
     */
    public ArrayList<Integer> getUserSignedUpEvent(int userId, GatewayFacade g){
            Attendee a = (Attendee) g.getUserById(userId);
            return a.getSignedUpEventList();
    }


    /**
     *
     * @param userId event id
     * @param g the database
     * @Description get the waitlist of the user.
     */
    public ArrayList<Integer> getUserWaitList(int userId, GatewayFacade g) {
            Attendee a = (Attendee) g.getUserById(userId);
            return a.getMyWaitList();
    }


    /**
     *
     * @param organizerID organizer ID
     * @param g the database
     * @Description get the event list of the organizer.
     */
    public List<Integer> getOrganizedEventList(int organizerID, GatewayFacade g){
        return g.getOrganizerById(organizerID).getCreatedEventList();
    }


    /**
     * @Description: get Speaker Event list
     */
    public List<Integer> getSpeakerGivingEventList(int speakerID, GatewayFacade g){
        return g.getSpeakerById(speakerID).get_GivingEventList();
    }


    /**
     * @Description: get User password by username
     */
    public String getUserPassword(String username, GatewayFacade g){
        return g.getUserByUserName(username).getPassword();
    }


    /**
     * @Description: get User id by username
     */
    public int getUserID(String username, GatewayFacade g){
        if (isExistingUser(username, g)){
            return g.getUserByUserName(username).getUserId();
        }
        return -1;
    }


    /**
     * @Description: judge the User category, 0 means speaker, 1 means organizer, 2 means attendee/VIP
     */
    public int getUserCategory(int id, GatewayFacade g){
        if (isExistingSpeaker(id, g)){
            return 0;
        }
        else if (isExistingOrganizer(id, g)){
            return 1;
        }
        else{
            return 2;
        }
    }

    /**
     * @Description: get needed user category list. speaker = 0, organizer = 1, attendee = 2
     */
    public List<Integer> getListOfUsers(int userType, GatewayFacade g){
        List<User> users = g.getUserList();
        List<Integer> neededUsers = new ArrayList<>();
        for (User user : users){
            int userID = user.getUserId();
            if (getUserCategory(userID, g) == userType){
                neededUsers.add(userID);
            }
        }
        return neededUsers;
    }


    /**
     * @return the rank of the user from the wait list
     */
    public int getUserRankInWaitList(int userId, int eventId, GatewayFacade g) {
        return g.getEventById(eventId).getWaitList().indexOf(userId) + 1;
    }


    /**
     * @Description: get User name and id
     */
    public List<String> getUserInfo(int userID, GatewayFacade g){
        User user = g.getUserById(userID);
        return new ArrayList<String>(){
            {
                add(String.valueOf(user.getUserId()));
                add(user.getUserName());
            }
        };
    }


    /**
     * @Description: judge whether a speaker is speaking
     */
    public boolean isSpeakerBusy(int speakerId, LocalDateTime start, LocalDateTime end, GatewayFacade g) {
        for (int eid :g.getSpeakerById(speakerId).get_GivingEventList()) {
            if ((g.getEventById(eid).getStartTime().isBefore(end) & !(g.getEventById(eid).getEndTime().isBefore(start) |
                    g.getEventById(eid).getEndTime().equals(start)))) {
                return true;
            }
        }
        return false;
    }


    /**
     *
     * @Description check if a speaker is busy or not.
     */
    public boolean isSpeakerBusy(ArrayList<Integer> speakerId, LocalDateTime start, LocalDateTime end, GatewayFacade g) {
        for (int sid : speakerId) {
            if (isSpeakerBusy(sid, start, end, g)) {
                return true;
            }
        }
        return false;
    }


    /**
     * @Description: judge a speaker is exist
     */
    public boolean isExistingSpeaker(int userID, GatewayFacade g){
        return g.getSpeakerById(userID) != null;
    }


    /**
     *
     * @Description check if a speaker exists
     */
    public boolean isExistingSpeaker(List<Integer> speakerList, GatewayFacade g) {
        for (int i : speakerList) {
            if (g.getSpeakerById(i) == null) {
                return false;
            }
        }
        return true;
    }


    /**
     * @Description: judge a User is exist
     */
    public boolean isExistingUser(String username, GatewayFacade g){
        return g.getUserByUserName(username) != null;
    }

    public boolean isExistingUser(int userID, GatewayFacade gw){
        return gw.getUserById(userID) != null;
    }


    /**
     * @Description: judge an Attendee is exist
     */
    public boolean isExistingAttendee(int userId, GatewayFacade g){
        return g.getAttendeeById(userId) != null;
    }


    /**
     * @Description: judge an organizer is exist
     */
    public boolean isExistingOrganizer(int userId, GatewayFacade g){
        return g.getOrganizerById(userId) != null;
    }

    /**
     *
     * @Description check if an event can be canceled
     */
    // only the organizer who organized the event can cancel the event
    public boolean canCancelEvent(int userID, int eventID, GatewayFacade gw) {
        try{
            Organizer user = gw.getOrganizerById(userID);
            return user.getCreatedEventList().contains(eventID);
            }
        catch(NullPointerException npe){
            return false; // return false when userID is not pointing to organizer
        }
    }

    /**
     *
     * @Description cancel an event.
     */
    public void cancelEvent(int eventID, int organizerID, GatewayFacade gw) {
        if (gw.getMultiSpeakerEventById(eventID) != null){
            MultiSpeakerEvent event = gw.getMultiSpeakerEventById(eventID);
            cancelEventFromSignedUsers(eventID, event, gw);
            cancelEventFromOrganized(eventID, organizerID, gw);
            cancelEventFromWaitingUsers(eventID, event, gw);
            List<Integer> speakerList = event.getSpeakerList();
            for (Integer speakerID : speakerList){
                Speaker speaker = gw.getSpeakerById(speakerID);
                speaker.removeGivingEvent(eventID);
                gw.updateUser(speaker);
            }
        }
        else if(gw.getOneSpeakerEventById(eventID) != null){
            OneSpeakerEvent event = gw.getOneSpeakerEventById(eventID);
            cancelEventFromSignedUsers(eventID, event, gw);
            cancelEventFromOrganized(eventID, organizerID, gw);
            cancelEventFromWaitingUsers(eventID, event, gw);
            removeOneSpeakerEventFromSpeaker(eventID, gw);
        }
        else{
            NonSpeakerEvent event = gw.getNonSpeakerEventById(eventID);
            cancelEventFromOrganized(eventID, organizerID, gw);
            cancelEventFromSignedUsers(eventID, event, gw);
            cancelEventFromWaitingUsers(eventID, event, gw);
        }
    }

    private void cancelEventFromWaitingUsers(int eventID, Event event, GatewayFacade gw) {
        List<Integer> userList = event.getWaitList();
        for (Integer userID : userList){
            cancelEventFromMyWaitList(eventID, userID, gw);
        }
    }

    /**
     * @Description: cancel the event from signup up users
     * @param eventID event id
     * @param event the event
     */
    private void cancelEventFromSignedUsers(int eventID, Event event, GatewayFacade gw){
        List<Integer> userList = event.getSignedUpUserList();
        for (Integer userID : userList){
            cancelEventFromUser(eventID, userID, gw);
        }
    }

    /**
     * @return true if we can change the event capacity
     * @param user user
     */
    public boolean canChangeEventCapacity(int user, GatewayFacade gw) {
        return isExistingOrganizer(user, gw);
    }


    /**
     * @Description: remove all the non vip events
     * @param eventId event id
     * @param droppedUsers a list of user id
     */
    public void dropNonVipEventFromNonVIP(List<Integer> droppedUsers, int eventId, GatewayFacade gw) {
        for (int userID : droppedUsers){
            Attendee attendee = (Attendee) gw.getUserById(userID);
            if (attendee.getSignedUpEventList().contains(eventId)){
                cancelEventFromUser(eventId, userID, gw);
            }
            else{
                cancelEventFromMyWaitList(eventId, userID, gw);
            }
        }
    }

    private void cancelEventFromOrganized(int eventID, int organizerID, GatewayFacade gw){
        Organizer organizer = gw.getOrganizerById(organizerID);
        organizer.cancelCreatedEvent(eventID);
        gw.updateUser(organizer);
    }
}