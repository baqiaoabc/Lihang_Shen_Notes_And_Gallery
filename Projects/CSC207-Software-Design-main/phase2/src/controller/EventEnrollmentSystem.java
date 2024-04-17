package controller;

import gateway.GatewayFacade;

class EventEnrollmentSystem extends subSystem{

    /**
     * Allow current logged in user to sign up to an event.
     *
     * @param eventID ID of the event signing up for.
     * @return Return True if user is successfully signed up for the event, false otherwise.
     */
    boolean signUpForEvent(String eventID, GatewayFacade gw) {
        try{
            int eid = Integer.parseInt(eventID);
            // check if the event exists, and user can sign up for event
            if (em.canAddUserToEvent(user, eid, gw) && um.canSignUpForEvent(eid, user, gw)){ //need confirm
                um.addEventToUser(eid, user, gw);
                em.addUserToEvent(user, eid, gw);
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
     * Signup for event wait list
     *
     * @param eventId ID of the event they want to deregister from.
     * @return Return True when the user has successful signuped for event wait list
     */
    boolean signUpForEventWaitList(String eventId, GatewayFacade gw) {
        try{
            int eid = Integer.parseInt(eventId);
            if (em.canAddUserToWaitList(eid, user, gw) & um.canSignUpForEvent(eid, user, gw)){
                em.addUserToWaitList(eid, user, gw);
                um.addEventToMyWaitList(eid, user, gw);
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
     * @param eventId ID of the event they want to deregister from.
     * @return Return True when the user has successful cancelled their enrollment in the event.
     */
    boolean cancelEnrollmentInEvent(String eventId, GatewayFacade gw){
        try{
            int eid = Integer.parseInt(eventId);
            if (em.canRemoveSignedUpUser(user ,eid, gw)){
                em.removeSignedUpUser(user, eid, gw);
                um.cancelEventFromUser(eid, user, gw);
                int userId = em.add1stRankedWaitListUser(eid, gw);
                um.transferWaitingEventToSignedUp(eid, userId, gw);
                return true;
            }
            return false;
        }
        catch(NumberFormatException nfe){
            return false;
        }
        catch(IndexOutOfBoundsException ioob){ // return true when waitlist is empty
            return true;
        }
    }

    /**
     * Remove the event from the waitlist
     *
     * @param eventId ID of the event they want to deregister from.
     * @return Return True when the the event has been removed from the wait list
     */
    boolean removeEventFromWaitList(String eventId, GatewayFacade gw) {
        try{
            int eid = Integer.parseInt(eventId);
            if (em.canRemoveWaitingUser(eid, user, gw)){
                em.removeWaitingUser(eid, user, gw);
                um.cancelEventFromMyWaitList(eid, user, gw);
                return true;
            }
            return false;
        }
        catch(NumberFormatException nfe){
            return false;
        }
    }
}
