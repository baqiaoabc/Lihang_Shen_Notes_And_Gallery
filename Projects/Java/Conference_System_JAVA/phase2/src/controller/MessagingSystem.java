package controller;

import java.util.List;
import gateway.GatewayFacade;

class MessagingSystem extends subSystem {

    /**
     * Allow user to message all attendees in an event.
     *
     * @param eventID The event that all attendees and the speaker is in.
     * @param content The content of the message.
     * @return Return true if the message is sent successfully, false when input is invalid.
     */
    boolean messageAllAttendeesInEvent(String eventID, String title, String content, GatewayFacade gw){
        try{
            int eID = Integer.parseInt(eventID);
            if (em.isExistingEvent(eID, gw)){
                mm.messageAllUsersInEvent(eID, user, title, content, gw);
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
    boolean messageAllUsersInAllSpeakingEvents(String title, String content, GatewayFacade gw){
        if (um.isExistingSpeaker(user, gw)){
            mm.messageAllUsersInAllSpeakingEvents(user, title, content, gw);
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
    boolean messageOneSpecificUserInEvent(String eventID, String receiverID, String title, String content,
                                          GatewayFacade gw){
        try{
            int eID = Integer.parseInt(eventID);
            int reID = Integer.parseInt(receiverID);
            if (em.isExistingEvent(eID, gw) && um.isExistingAttendee(reID, gw) &&
                    mm.canMessageAttendeeOfSpeakingEvent(eID, reID, gw)){
                mm.messageOneUser(user, reID, title, content, gw);
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
    boolean messageAllSpeakers(String title, String content, GatewayFacade gw){
        if (mm.canMessageAllSpeakersOrAllAttendee(user, gw)){
            mm.messageAllSpeakers(title, content, user, gw);
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
    boolean messageSpeaker(String receiverID, String title, String content, GatewayFacade gw){
        try{
            int rID = Integer.parseInt(receiverID);
            if (um.isExistingSpeaker(rID, gw) && mm.canMessageSpeaker(user, rID, gw)){
                mm.messageOneUser(user, rID, title, content, gw);
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
    boolean messageAllAttendee(String title, String content, GatewayFacade gw){
        if(mm.canMessageAllSpeakersOrAllAttendee(user, gw)){
            mm.messageAllAttendees(user, title, content, gw);
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
    boolean messageAttendee(String receiverID, String title, String content, GatewayFacade gw){
        try{
            int rID = Integer.parseInt(receiverID);
            if (um.isExistingAttendee(rID, gw) && mm.canMessageAttendee(user, rID, gw)){
                mm.messageOneUser(user, rID, title, content, gw);
                return true;
            }
            return false;
        }
        catch(NumberFormatException nfe){
            return false;
        }
    }

    /**
     * Reply to a specific message.
     *
     * @param messageIndex Position of the message (to identify which message it is replying)
     * @param content Content of the replying message.
     * @return Return true when the message is successfully sent, false if the user is not allowed to reply to the
     * message.
     */
    boolean replyMessage(String messageIndex, String title, String content, GatewayFacade gw){
        try{
            int mIndex = Integer.parseInt(messageIndex);
            if (mm.canReplyMessage(user, mIndex, gw)){
                mm.replyMessage(title, content, user, mIndex, gw);
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
    List<List<String>> readSentMessages(GatewayFacade gw){
        return mm.getSentMessageListByUserId(user, gw);
    }

    /**
     * Reads the incoming messages of the user currently logged in.
     *
     * @return List of Strings representing the messages the user reveived.
     */
    List<List<String>> readReceivedMessages(GatewayFacade gw){
        return mm.getReceivedMessageListByUserId(user, gw);
    }

}
