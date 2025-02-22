package presenter;

import controller.ConferenceSystem;

import java.util.List;

/**
 * This is an presenter class for the panel made by the PanelFactory
 */
public class PanelPresenter extends Presenter{
    ConferenceSystem cs;
    IUpdate panel;

    /**
     * Construct the PanelPresenter
     * @param presenter the presenter whose ConferenceSystem will be used in the PanelPresenter
     * @param panel the IUpdate panel whose update method we need to use
     */
    public PanelPresenter(Presenter presenter, IUpdate panel) {
        this.cs = presenter.cs;
        this.panel = panel;
    }


    /**
     * sign up the event which doesn't have a waiting list at present
     * @param eventID the event ID that the user wants to sign up
     */
    public void signUpEvent(String eventID) {
        Boolean success = cs.signUpForEvent(eventID);
        if (success) {
            panel.update("succeedSignUpEvent");
        } else {
            panel.update("failSignUpEvent");
        }
    }

    /**
     * Sign up the event that need to be waited
     * @param eventID the event ID that the user wants to sign up
     */
    public void waitEvent(String eventID) {
        Boolean success = cs.signUpForEventWaitList(eventID);
        if (success) {
            panel.update("succeedWaitEvent");
        } else {
            panel.update("failWaitEvent");
        }
    }

    /**
     * cancel the event
     * @param eventID the event ID that the user wants to sign up
     */
    public void cancelEvent(String eventID) {
        Boolean success = cs.cancelEvent(eventID);
        if (success) {
            panel.update("succeedCancelEvent");
        } else {
            panel.update("failCancelEvent");
        }
    }

    /**
     * change the event
     * @param type the type of the user
     * @param eventID the event ID that the user wants to sign up
     * @param newSetting new setting of the event
     */
    public void changeEvent(int type, String eventID, String newSetting) {
        Boolean success;
        if (type == 0) {
            success = cs.modifySpeakerForEvent(newSetting, eventID);
        } else if (type ==  2) {
            Boolean VIP = false;
            if (newSetting.equalsIgnoreCase("yes")){
                VIP = true;
            }
            success = cs.changeVipStatusOfEvent(eventID, VIP);
        } else {
            success = cs.changeEventCapacity(eventID, newSetting);
        }
        if (success) {
            panel.update("succeedChangeEvent");
        } else {
            panel.update("failChangeEvent");
        }
    }


    /**
     * check if a user is VIP or not
     * @param VIP vip status
     */
    private Boolean vIPConverter(int VIP) {
        if (VIP == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * create an user
     * @param type the type of the user
     * @param username the user name
     * @param password password for a user
     */
    public void createUser(String type, String username, String password) {
        Boolean success;
        if (type == "Speaker") {
            success = cs.createSpeaker(username, password);
        } else if (type == "Organizer") {
            success = cs.createOrganizer(username, password);
        } else if (type == "Attendee") {
            success = cs.createAttendee(username, password);
        } else {
            success = cs.createVipUser(username, password);
        }
        if (success) {
            panel.update("succeedCreateUser");
        } else {
            panel.update("failCreateUser");
        }
    }

    /**
     * create an event
     * @param eventType type of the event
     * @param startTime start time of the event
     * @param endTime end time of the event
     * @param speakers speakers of the event
     * @param roomNum location of the event indicated by the room number
     * @param topic topic of the event
     * @param capacity capacity of the event
     * @param vipStatus whether the event need a VIP or not
     */
    public void createEvent(int eventType, String startTime, String endTime, String speakers,
                            String roomNum, String topic, String capacity, int vipStatus) {
        Boolean success = cs.newEvent(eventType, startTime, endTime, speakers, topic, roomNum, capacity, vIPConverter(vipStatus));
        if (success) {
            panel.update("succeedCreateEvent");
        } else {
            panel.update("failCreateEvent");
        }
    }

    /**
     * cancel the enrollment for an event that is not in the waiting list
     * @param eventID event id
     */
    public void cancelEnrollment(String eventID) {
        Boolean success = cs.cancelEnrollmentInEvent(eventID);
        if (success) {
            panel.update("succeedCancelEnrollment");
        } else {
            panel.update("failCancelEnrollment");
        }
    }

    /**
     * remove a spot in an event that is in the waiting list
     * @param eventID event id
     */
    public void removeWait(String eventID) {
        Boolean success = cs.removeEventFromWaitList(eventID);
        if (success) {
            panel.update("succeedRemoveWait");
        } else {
            panel.update("failRemoveWait");
        }
    }

    /**
     * add other rooms
     * @param roomNumber room number
     * @param capacity capacity
     */
    public void addRoom(String roomNumber, String capacity) {
        Boolean success = cs.addNewRoom(roomNumber, capacity);
        if (success) {
            panel.update("succeedAddRoom");
        } else {
            panel.update("failAddRoom");
        }
    }

    /**
     * view all the events
     */
    public List<List<String>> viewAllEvent() {
        return cs.viewEvents();
    }

    /**
     * view all the events in the waiting list that the user can sign up
     */
    public List<List<String>> viewCanWaitlistEvents() {
        return cs.viewCanWaitlistEvents();
    }

    /**
     * view all the events not in the waiting list that the user can sign up
     */
    public List<List<String>> viewCanSignUpEvents() {
        return cs.viewCanSignUpEvents();
    }

    /**
     * view all the rooms
     */
    public List<List<String>> viewAllRooms() {
        return cs.viewAllRooms();
    }

    /**
     * view all the speakers
     */
    public List<List<String>> viewAllSpeaker() {
        return cs.viewAllSpeakers();
    }

    /**
     * view all the signed up events
     */
    public List<List<String>> viewSignedUpEvents() {
        return cs.viewSignedUpEvents();
    }

    /**
     * view my wait list
     */
    public List<List<String>> viewMyWaitList() {
        return cs.viewMyWaitList();
    }

    /**
     * view all the organized events
     */
    public List<List<String>> viewOrganizedEvents() {
        return cs.viewOrganizedEvents();
    }

    /**
     * view all the speaking events
     */
    public List<List<String>> viewSpeakingEvents() {
        return cs.viewSpeakingEvents();
    }

    /**
     * reset password
     * @param newPass new password
     */
    public void reSetPass(String newPass){
        Boolean success = cs.resetPassword(newPass);
        if(success){
            panel.update("succeedResetPass");
        }else{
            panel.update("failResetPass");
        }
    }
}