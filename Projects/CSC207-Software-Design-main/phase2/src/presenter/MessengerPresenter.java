package presenter;

import controller.ConferenceSystem;

import java.util.List;
/**
 * A presenter class for the message window.
 */

public class MessengerPresenter extends Presenter {
    /**
     * Construct the PanelPresenter.
     *
     */
    ConferenceSystem conferenceSystem;
    IMessage msgWindow;

    public MessengerPresenter(IMessage msgWindow, Presenter presenter) {
        conferenceSystem = presenter.cs;
        this.msgWindow = msgWindow;
    }


    /**
     * Allows attendee to message all the attendees of the event.
     * @param title the title of the message.
     * @param content the message's content.
     * @param eventID the ID of the event
     */
    void messageAllAttendees(String title, String content, String eventID){
        boolean success = conferenceSystem.messageAllAttendeesInEvent(eventID, title,  content);
        msgWindow.messageSuccess(success);

    }

    /**
     * Allows attendee to message one the attendee
     * @param title the title of the message.
     * @param content the message's content.
     */
    void messageOneAttendee(String title, String content, String user){
        String userID = conferenceSystem.getUserIDbyUserName(user);
        boolean success = conferenceSystem.messageAttendee(userID, title, content);
        msgWindow.messageSuccess(success);
    }

    /**
     * Allows users to see the messages they received
     */
   List<List<String>> readReceivedMessages(){
        return conferenceSystem.readReceivedMessages();
   }

    /**
     * Allows users to see the messages they sent
     */
    List<List<String>>  readSentMessages(){
       return conferenceSystem.readSentMessages();
    }

    /**
     * Allows organizer to message all the speakers.
     * @param title the title of the message.
     * @param content the message's content.
     */
    void messageAllSpeakers(String title, String content){
        boolean success = conferenceSystem.messageAllSpeakers(title, content);
        msgWindow.messageSuccess(success);
    }
    /**
     * Allows organizer to message the speaker.
     * @param title the title of the message.
     * @param content the message's content.
     * @param speaker the usernname of the speaker they want to send message to.
     */
    void messageOneSpeaker(String title, String content, String speaker){
        String speakerID = conferenceSystem.getUserIDbyUserName(speaker);
        boolean success = conferenceSystem.messageSpeaker(speakerID, title, content);
        msgWindow.messageSuccess(success);
    }
    /**
     * Allows speaker to message all the attendees of their events.
     * @param title the title of the message.
     * @param content the message's content.
     */
    void messageAllAttendeesOfSpeakerEvent(String title, String content){
        boolean success = conferenceSystem.messageAllUsersInAllSpeakingEvents(title, content);
        msgWindow.messageSuccess(success);
    }

    /**
     * Allows speaker to message all the attendee of their event.
     * @param title the title of the message.
     * @param content the message's content.
     * @param eventID the ID of the event.
     * @param userID the userID of the attendee they want to send their message to
     */
    void messageOneSpecificAttendee(String title, String content, String eventID, String userID){
        boolean success = conferenceSystem.messageOneSpecificUserInEvent(eventID, userID, title, content);
        msgWindow.messageSuccess(success);
    }

    /**
     * Allows organizer to message all the attendees.
     * @param title the title of the message.
     * @param content the message's content.
     */
    void messageAllAttendeeOrganizer(String title, String content){
        msgWindow.messageSuccess(conferenceSystem.messageAllAttendee(title, content));
    }

    /**
     * Allows users to reply to the received message.
     * @param title the title of the message.
     * @param content the message's content.
     * @param messageID the ID of the message.
     */
    void replyTo(String content, String title, String messageID){
        boolean success = conferenceSystem.replyMessage(messageID, title, content);
        msgWindow.messageSuccess(success);
    }

    /**
     * Return information of all messageable attendee. This method should be used by speaker only
     *
     * @return the List contains information of all messagable attendees.
     */
    List<List<String>> allMessageableAttendee() {
        return conferenceSystem.allMessageableAttendee();
    }

    /**
     * Return all the in iformation of Attendees in conference system
     *
     * @return the List contains information of all attendees.
     */
    List<List<String>> viewAllAttendees() {
        return conferenceSystem.viewAllAttendees();
    }

    /**
     * Return all the in iformation of Speaker in conference system
     *
     * @return the List contains information of all speakers.
     */
    List<List<String>> viewAllSpeakers() {
        return conferenceSystem.viewAllSpeakers();
    }

    /**
     * Send message to all the attenddes that participate in one given event.
     *
     * @param title The message title
     * @param content the message content
     * @param eventId the eventId
     */
    public void messageAllAttendeesOfOneSpeakerEvent(String title, String content, String eventId) {
        boolean success = conferenceSystem.messageAllAttendeesInEvent(eventId, title, content);
        msgWindow.messageSuccess(success);
    }

    /**
     * Return all the events the speaker is giving.
     * @return the List of information of all events the speaker is giving
     */
    public List<List<String>> viewSpeakingEvent() {
        return conferenceSystem.viewSpeakingEvents();
    }

    /**
     * Return all the attendee in one event.
     *
     * @param eventId the event Id
     * @return the List of information of all attendee in one event.
     */
    public List<List<String>> viewAllAttendeesInEvent(String eventId) {
        return conferenceSystem.viewSignedUpUser(eventId);
    }
}


