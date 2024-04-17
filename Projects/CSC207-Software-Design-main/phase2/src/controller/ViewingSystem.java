package controller;

import gateway.Gateway;
import gateway.GatewayFacade;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

class ViewingSystem extends subSystem{

    /**
     * @param idList the event id list
     * Viewing System which allows users to view users, events and rooms in the Conference.
     */
    private List<List<String>> getEventList(List<Integer> idList, GatewayFacade gw){
        List<List<String>> allEvents = new ArrayList<>();
        for (Integer id : idList){
            allEvents.add(em.getInfoOfEvent(id, gw));
        }
        return allEvents;
    }

    /**
     *
     * @param idList the event id list
     * @return all the organized event list
     */
    private List<List<String>> getOrganizedEventList(List<Integer> idList, GatewayFacade gw){
        List<List<String>> allEvents = new ArrayList<>();
        for (Integer id : idList){
            List<String> info = em.getInfoOfEvent(id, gw);
            info.add(em.getStringOfSpeakerOfEvent(id, gw));
            info.add(String.valueOf(em.getCapacity(id, gw)));
            allEvents.add(info);
        }
        return allEvents;
    }

    /**
     * Return all events that are currently scheduled.
     *
     * @return List of Strings of the events
     */
    List<List<String>> viewEvents(GatewayFacade gw){
        List<Integer> events = em.getEventList(gw);
        return getEventList(events, gw);
    }

    /**
     * Return a list of events that the current logged in user has signed up for.
     *
     * @return List of Strings of the events
     */
    List<List<String>> viewSignedUpEvents(GatewayFacade gw){
        List<Integer> events = um.getUserSignedUpEvent(user, gw);
        return getEventList(events, gw);
    }

    /**
     * Return a list of events that the current logged in organizer has organized
     *
     * @return List of Strings of the events
     */
    List<List<String>> viewOrganizedEvents(GatewayFacade gw){
        List<Integer> events = um.getOrganizedEventList(user, gw);
        return getOrganizedEventList(events, gw);
    }

    /**
     * Return a list of events that the current Speaker is going to speak for.
     *
     * @return List of Strings of the events
     */
    List<List<String>> viewSpeakingEvents(GatewayFacade gw){
        List<Integer> events = um.getSpeakerGivingEventList(user, gw);
        return getEventList(events, gw);
    }

    /**
     * Return a list of events that the current logged in user can sign up for. These events are events that users
     * do not have to be on a waitlist.
     *
     * @return List of Strings of the events
     */
    List<List<String>> viewCanSignUpEvents(GatewayFacade gw) {
        List<Integer> allEvents = em.getEventList(gw);
        List<List<String>> allEventsInfo = new ArrayList<>();
        for (Integer eventID : allEvents) {
            if (um.canSignUpForEvent(eventID, user, gw) && em.canAddUserToEvent(user, eventID, gw)) {
                allEventsInfo.add(em.getInfoOfEvent(eventID, gw));
            }
        }
        return allEventsInfo;
    }

    // return format: [title, eventID, startTime, endTime, duration, room, VIPstatus, waitlistLength]
    /**
     * View all the events that are waitlistable
     *
     * @param gw GatewayFacade
     * @return all the events that can be added to the waitlist
     *
     */
    List<List<String>> viewCanWaitListEvents(GatewayFacade gw){
        List<Integer> allEvents = em.getEventList(gw);
        List<List<String>> allEventsInfo = new ArrayList<>();
        for (Integer eventID : allEvents){
            if (um.canSignUpForEvent(eventID, user, gw) && em.canAddUserToWaitList(eventID, user, gw)){
                List<String> info = em.getInfoOfEvent(eventID, gw);
                info.add(String.valueOf(em.getWaitlistLength(eventID, gw)));
                allEventsInfo.add(info);
            }
        }
        return allEventsInfo;
    }

    // when use this method, you need to restrict the type of user to be attendee type...
    // return format: [title, eventID, startTime, endTime, duration, room, VIPstatus, waitlistRank]
    /**
     * View the waitlist
     *
     * @param gw GatewayFacade
     * @return all the users that are in the waitlist
     *
     */
    List<List<String>> viewMyWaitList(GatewayFacade gw) {
        List<Integer> myWaitList = um.getUserWaitList(user, gw);
        List<List<String>> myWaitingEventsInfo = new ArrayList<>();
        for (Integer eventId: myWaitList) {
            int rank = um.getUserRankInWaitList(user, eventId, gw);
            List<String> eventInfo = em.getInfoOfEvent(eventId, gw);
            eventInfo.add(String.valueOf(rank));
            myWaitingEventsInfo.add(eventInfo);
        }
        return myWaitingEventsInfo;
    }



    /**
     * View all attendees who are registered in one of the current speaker's speaking events. Without duplicates.
     *
     * @return Return a list of Strings that represent all attendees of all the events the speaker is speaking for.
     * Every attendee is represented by a string formated as follows: "UserName (userID)"
     */
    List<List<String>> viewAttendeesInSpeakingEvents(GatewayFacade gw){
        List<Integer> allSpeakingEvents = um.getSpeakerGivingEventList(user, gw);
        Set<Integer> allAttendeesInEvents = new LinkedHashSet<>();
        List<List<String>> sAllAttendeesInEvents = new ArrayList<>();
        for (Integer eventID : allSpeakingEvents){
            List<Integer> usersInEvent = em.getUserList(eventID, gw);
            allAttendeesInEvents.addAll(usersInEvent);
        }
        for (Integer userID : allAttendeesInEvents){
            sAllAttendeesInEvents.add(um.getUserInfo(userID, gw));
        }
        return sAllAttendeesInEvents;
    }

    /**
     * View all rooms in the system.
     *
     * @param idList the list User id
     * @return Return the user lists
     *
     */
    private List<List<String>> getUserList(List<Integer> idList, GatewayFacade gw){
        List<List<String>> sUser = new ArrayList<>();
        for (Integer id : idList){
            sUser.add(um.getUserInfo(id, gw));
        }
        return sUser;
    }

    /**
     * View all attendees in the system.
     *
     * @return Return a list of Strings that represent all attendees in the system.
     * Every attendee is represented by a string formatted as follows: "UserName (userID)"
     */
    List<List<String>> viewAllAttendees(GatewayFacade gw){
        List<Integer> allAttendees = um.getListOfUsers(2, gw);
        return getUserList(allAttendees, gw);
    }

    /**
     * View all speakers in the system.
     *
     * @return Return a list of Strings that represent all speakers in the system.
     * Every speaker is represented by a string formatted as follows: "UserName (userID)"
     */
    List<List<String>> viewAllSpeakers(GatewayFacade gw){
        List<Integer> allSpeakers = um.getListOfUsers(0, gw);
        return getUserList(allSpeakers, gw);
    }

    /**
     * View all rooms in the system.
     *
     * @return Return a list of strings that represent all rooms in the system.
     * Every room is represented by a string formatted as follows: "RoomName/Number (RoomID)"
     */
    List<List<String>> viewAllRooms(GatewayFacade gw){
        return rm.allRooms(gw);
    }

    List<List<String>> viewAllMessageableAttendee(GatewayFacade g) {
        return getUserList(mm.allMessageableAttendee(user, g), g);
    }

    public List<List<String>> viewSignedUpUser(int eventId, GatewayFacade g) {
        return getUserList(em.getSignedUpUser(eventId, g), g);
    }
}
