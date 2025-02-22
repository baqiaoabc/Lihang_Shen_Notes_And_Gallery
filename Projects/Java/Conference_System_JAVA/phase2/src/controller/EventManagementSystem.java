package controller;

import gateway.GatewayFacade;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

class EventManagementSystem extends subSystem{

    /**
     * change type of a event
     * @param eventId eventid of event
     * @return Return true if change correctly, false otherwise.
     */
    boolean changeVipStatusOfEvent(String eventId, boolean type, GatewayFacade gw){
        try{
            int eID = Integer.parseInt(eventId);
            if (em.getVipStatusOfEvent(eID, gw) != type && um.isExistingOrganizer(user, gw)){
                em.changeVipStatusOfEvent(eID, type, gw);
                if (type){ // change from non-vip to vip
                    List<Integer> droppedUsers = em.dropNonVipFromVipEvent(eID, gw);
                    List<Integer> movedUsers = em.addWaitlistUsersToEvent(eID, gw);
                    um.dropNonVipEventFromNonVIP(droppedUsers, eID, gw);
                    um.transferWaitingEventToSignedUp(eID, movedUsers, gw);
                }
                return true;
            }
            return false;
        }
        catch (NumberFormatException | NullPointerException nfe){
            return false;
        }

    }

    /**
     * return the event type, true means event is VIP, false means event is not VIP
     * @param eventId event id
     * @return the type of event
     */
    boolean getVipStatusOfEvent(int eventId, GatewayFacade gw){
        return em.getVipStatusOfEvent(eventId, gw);
    }

    /**
     * Modify the Speaker for event
     * @param eventID event id
     * @param speakerID speaker ID
     * @return True if we modify the speaker for event
     */
    boolean modifySpeakerForEvent(String speakerID, String eventID, GatewayFacade gw){
        try{
            int eID = Integer.parseInt(eventID);
            int type = em.determineEventType(eID, gw);
            boolean flag = false;
            switch(type){
                case 1:
                    flag = changeSpeakerForOneSpeakerEvent(speakerID, eventID, gw);
                    break;
                case 2:
                    flag = addSpeakerToMultiSpeakerEvent(speakerID, eventID, gw);
                    break;
            }
            return flag;
        }
        catch (NumberFormatException | NullPointerException e){
            return false;
        }
    }

    /**
     * Set the speaker of an event.
     *
     * @param speakerID New Speaker's ID
     * @param eventID The event's ID
     * @return Return True when the speaker is assigned to the event successfully, false otherwise.
     */
    private boolean changeSpeakerForOneSpeakerEvent(String speakerID, String eventID, GatewayFacade gw){
        try{
            int sID = Integer.parseInt(speakerID);
            int eID = Integer.parseInt(eventID);
            if(um.canAddEventToSpeaker(eID, sID, gw) & gw.getOneSpeakerEventById(eID) != null){
                um.addEventToSpeaker(eID, sID, gw);
                em.setSpeakerToOneSpeakerEvent(sID, eID, gw);
                return true;
            }
            return false; // return false when cannot add event to speaker
        }
        catch(NumberFormatException | NullPointerException e){
            return false; // return false on invalid input
        }
    }

    /**
     * Add Speaker to Multiple Speaker Event
     *
     * @param speakerId New Speaker's ID
     * @param eventId The event's ID
     * @param gw GatewayFacade
     * @return Return True when the speaker is assigned to the event successfully, false otherwise.
     */
    private boolean addSpeakerToMultiSpeakerEvent(String speakerId, String eventId, GatewayFacade gw) {
        try{
            int sID = Integer.parseInt(speakerId);
            int eID = Integer.parseInt(eventId);
            if(um.canAddEventToSpeaker(eID, sID, gw) & gw.getMultiSpeakerEventById(eID) != null){
                um.addEventToSpeaker(eID, sID, gw);
                em.addSpeakerToMultiSpeakerEvent(sID, eID, gw);
                return true;
            }
            return false; // return false when cannot add event to speaker
        }
        catch(NumberFormatException | NullPointerException e){
            return false; // return false on invalid input
        }
    }

    // if no speaker, pass in empty string
    // if more than one speaker, pass "id1,id2"
    /**
     * Add Speaker to Multiple Speaker Event
     *
     * @param startTime Start time
     * @param endTime end time
     * @param speakerID Speaker Id
     * @param topic The topic of the event
     * @param roomNumber Room number
     * @param capacity Room Capacity
     * @param vipStatus VIP Status
     * @param gw GatewayFacade
     * @return Return True when the speaker is assigned to the event successfully, false otherwise.
     */
    boolean newEvent(int type, String startTime, String endTime, String speakerID,
                            String topic, String roomNumber, String capacity, boolean vipStatus, GatewayFacade gw){
        try{
            List<Integer> types = determineEventTypes(type);
            LocalDateTime sTime = LocalDateTime.parse(startTime, em.getTimeFormatter());
            LocalDateTime eTime = LocalDateTime.parse(endTime, em.getTimeFormatter());
            int rID = rm.getRoomIDbyRoomNumber(roomNumber, gw);
            int cap = Integer.parseInt(capacity);
            if (!(speakerID.length() == 0)){
                if (speakerID.contains(",")) { // have more than 1 speaker
                    ArrayList<Integer> sID = new ArrayList<>();
                    StringTokenizer token = new StringTokenizer(speakerID,",");
                    while (token.hasMoreElements()){
                        sID.add(Integer.parseInt(token.nextToken()));
                    }
                    return newEventMoreThan1Speaker(types.get(0), types.get(1), sID, sTime, eTime, topic,
                            rID, cap, vipStatus, gw);
                }
                else { // only have 1 speaker
                    int sID = Integer.parseInt(speakerID);
                    return newEvent1Speaker(types.get(0), types.get(1), sID, sTime, eTime, topic, rID,
                            cap, vipStatus, gw);
                }
            }
            else{ // no speaker
                return newEventNoSpeaker(types.get(0), types.get(1), sTime, eTime, topic, rID, cap, vipStatus, gw);
            }
        }
        catch(NumberFormatException | DateTimeParseException e){
            return false;
        }
    }

    // 0: party, 1: talk, 2: panel discussion
    /**
     * Determine the type of the event
     *
     * @param pType GatewayFacade
     * @return Return the correct type of the event
     */
    private List<Integer> determineEventTypes(int pType){
        List<Integer> types = new ArrayList<>();
        switch (pType){
            case 0: types.add(0);
                    types.add(0);
                    break;
            case 1: types.add(1);
                    types.add(0);
                    break;
            case 2: types.add(2);
                    types.add(0);
                    break;
        }
        return types;
    }

    /**
     * Create Event
     *
     * @param sID User Id
     * @param rID room ID
     * @param sTime Starting Time
     * @param eTime Ending time
     * @param cap capacity
     * @param gw GatewayFacade
     * @return Return the correct type of the event
     */
    private boolean canCreateEvent(int sID, int rID, LocalDateTime sTime, LocalDateTime eTime, int cap,
                                   GatewayFacade gw){
        return um.isExistingSpeaker(sID, gw) && em.canCreateEvent(rID, sTime, eTime, cap, gw) &&
                ! um.isSpeakerBusy(sID, sTime, eTime, gw);
    }


    /**
     * Create Event for Speaker
     *
     * @param sID Speaker ID
     * @param sTime starting time
     * @param eTime ending time
     * @param cap Capacity
     * @param topic The topic of the event
     * @param rID Room id
     * @return true if we successfully created the new event for speaker
     */
    private boolean newEvent1Speaker(int type1, int type2, int sID, LocalDateTime sTime,
                                     LocalDateTime eTime, String topic, int rID, int cap,
                                     boolean vipStatus, GatewayFacade gw){

        if (type1 != 1){
            return false;
        }
        if (canCreateEvent(sID, rID, sTime, eTime, cap, gw)) {
            int eventID = em.createEvent(type1, type2, sID, sTime, eTime, topic, rID, cap, vipStatus, gw);
            um.addEventToOrganizedList(eventID, user, gw);
            um.addEventToSpeaker(eventID, sID, gw);
            return true;
        }
        return false;
    }

    /**
     * Create Event for multiple Speakers
     *
     * @param sID a List of Speaker ID
     * @param sTime starting time
     * @param eTIme ending time
     * @param topic The topic of the event
     * @param rID room id
     * @param cap Capacity
     * @return true if we successfully created the new event for multiple speaker
     */
    private boolean newEventMoreThan1Speaker(int type1, int type2, ArrayList<Integer> sID, LocalDateTime sTime,
                                             LocalDateTime eTIme, String topic, int rID, int cap,
                                             boolean vipStatus, GatewayFacade gw){
        if (type1 != 2){
            return false;
        }
        for (int speakerID : sID){
            if (!canCreateEvent(speakerID, rID, sTime, eTIme, cap, gw)){
                return false;
            }
        }
        int eventID = em.createEvent(type1, type2, sID, sTime, eTIme, topic, rID, cap, vipStatus, gw);
        um.addEventToOrganizedList(eventID, user, gw);
        for (int speakerID : sID){
            um.addEventToSpeaker(eventID, speakerID, gw);
        }
        return true;
    }

    /**
     * Create Event for no Speakers
     *
     * @param vipStatus VIP Status
     * @param sTime starting time
     * @param eTime ending time
     * @param topic The topic of the event
     * @param rID room id
     * @param cap Capacity
     * @return true if we successfully created the new event for no speaker
     */
    private boolean newEventNoSpeaker(int type1, int type2, LocalDateTime sTime, LocalDateTime eTime,
                                      String topic, int rID, int cap, boolean vipStatus, GatewayFacade gw){
        if (type1 != 0 || !em.canCreateEvent(rID, sTime, eTime, cap, gw)){
            return false;
        }
        int eventID = em.createEvent(type1, type2, sTime, eTime, topic, rID, cap, vipStatus, gw);
        um.addEventToOrganizedList(eventID, user, gw);
        return true;
    }

    /**
     * Cancle Event
     *
     * @param eventID Event ID
     * @return true if we successfully canceled an event
     */
    boolean cancelEvent(String eventID, GatewayFacade gw){
        try{
            int eID = Integer.parseInt(eventID);
            if (um.canCancelEvent(user, eID, gw) && em.canCancelEvent(eID, gw)){
                um.cancelEvent(eID, user, gw);
                em.cancelEvent(eID, gw);
                return true;
            }
            return false;
        }
        catch(NumberFormatException nfe){
            return false;
        }
    }

    /**
     * Change the capacity of an event
     *
     * @param eventId Event ID
     * @param capacity Capacity
     * @return true if we successfully changed that capacity of an event
     */
    boolean changeEventCapacity(String eventId, String capacity, GatewayFacade gw) {
        try {
            int cap = Integer.parseInt(capacity);
            int eid = Integer.parseInt(eventId);
            if (em.canChangeEventCapacity(eid, cap, gw) && um.canChangeEventCapacity(user, gw)) {
                List<Integer> offWaitlistUsers = em.changeEventCapacity(eid, cap, gw);
                um.transferWaitingEventToSignedUp(eid, offWaitlistUsers, gw);
                return true;
            }
            return false;
        }
        catch (NumberFormatException | NullPointerException e) {
            return false;
        }
    }
}
