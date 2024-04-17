import java.util.ArrayList;
import java.util.List;
public class MessageManager {

    private int senderId;

    public void setSender_id(int i) {
        this.senderId = i;
    }

    public void message_allusers(int eventId, int senderId, String content, DataBase d){
        /**
         * @Description: message all signed up users in an event
         */
        for (int receiver_id : d.getEventById(eventId).getSingned_userid()){
            Message m = new Message(content, senderId, receiver_id);
            d.addMessage(m);
        }
    }

    public void messageAllUsersInAllSpeakingEvents(int speakerID, String content, DataBase db){
        /**
         * @Description: message all signed up users in multiple speaking events
         */
        Speaker speaker = db.getSpeakerById(speakerID);
        List<Integer> speakingEvents = speaker.get_GivingEventList();
        for (int eventID : speakingEvents){
            message_allusers(eventID, speakerID, content, db);
        }
    }

    public boolean canMessageAttendeeOfEvent(int eventId, int receiverId, DataBase db){
        /**
         * @Description: judge whether receiver whose Id is receiverID participate Event which id is eventId
         */
        return db.getEventById(eventId).getSingned_userid().contains(receiverId);
    }

    public void message_oneuser(int senderId, int receiverId, String content, DataBase d){
        /**
         * @Description: send message to one user whose id is receiverId
         */
        Message m = new Message(content, senderId, receiverId);
        d.addMessage(m);
    }


    public boolean message_specific_user(int senderId, int receiverId, String content, DataBase d){
        /**
         * @Description: message a specific user that is not an organizer
         */
        if (d.getUserById(receiverId) instanceof Organizer ) {
            return false;
        }
        else {
                Message m = new Message(content, senderId, receiverId);
                d.addMessage(m);
                return true;

        }
    }

    public boolean canMessageSpeaker(int senderId, int receiverId, DataBase db){
        /**
         * @Description: judge whether a User whose Id is senderId is eligible to send a message to a Speaker whose
         * id is receiverId
         */
        boolean senderCheck = db.getOrganizerById(senderId) != null || db.getAttendeeById(senderId) != null;
        return senderCheck && db.getSpeakerById(receiverId) != null;
    }

    public boolean canMessageAttendee(int senderId, int receiverId, DataBase db){
        /**
         * @Description: judge whether a User whose Id is senderId is eligible to send a message to a Attendee whose
         * id is receiverId
         */
        boolean senderCheck = db.getOrganizerById(senderId) != null || db.getAttendeeById(senderId) != null;
        boolean ishimself = senderId == receiverId;
        return (!ishimself) && senderCheck && db.getAttendeeById(receiverId) != null
                && db.getOrganizerById(receiverId) == null;
    }

    public boolean message_speaker(String content, int senderId, int reveiverId, DataBase d){
        /**
         * @Description: message a speaker
         */
        if (! (d.getSpeakerById(reveiverId) == null)) {
            Message m = new Message(content, senderId, reveiverId);
            d.addMessage(m);
            return true;
        }
        else {
            return false;
        }
    }


    public boolean canMessageAllSpeakersOrAllAttendee(int senderId, DataBase db){
        /**
         * @Description: judge whether a User is an Organizer
         */
        return db.getOrganizerById(senderId) != null;
    }

    public void messageAllSpeakers(String content, int senderId, DataBase d) {
        /**
         * @Description: message all Speaker
         */
        List<User> users = d.getUserList();
        for (User user : users){
            if (user instanceof Speaker){
                message_oneuser(senderId, user.getUser_id(), content, d);
            }
        }
    }

    public void messageAllAttendees(int senderId, String content, DataBase db){
        /**
         * @Description: message all Attendee
         */
        List<User> users = db.getUserList();
        for (User user : users){
            if (user instanceof Attendee & !(user instanceof Organizer)){
                message_oneuser(senderId, user.getUser_id(), content, db);
            }
        }
    }

    /**
     * Get formatted string of a received message
     * @param message the message wanted
     * @return String of the message in format "From: xxx Content: yyy"
     */
    private String getStringOfReceivedMessage(Message message, DataBase db){
        return "From: " + db.getUserById(message.getSenderId()).getUserName() +
                " Content: " + message.getInfo();
    }

    /**
     * Get formatted string of a sent message
     * @param message the message wanted
     * @return String of the message in format "To: xxx Content: yyy"
     */
    private String getStringOfSentMessage(Message message, DataBase db){
        return "To: " + db.getUserById(message.getReceiverId()).getUserName() +
                " Content: " + message.getInfo();
    }

    public List<String> getReceivedMessageListByUserId(int userID, DataBase d){
        /**
         * @Description: get all Messages which received by a User whose id is userID
         */
        List<Message> messages = d.getReceivedMessageListByUserId(userID);
        List<String> sMessages = new ArrayList<>();
        for (Message message : messages){
            sMessages.add(getStringOfReceivedMessage(message, d));
        }
        return sMessages;
    }

    public List<String> getSentMessageListByUserId(int userID, DataBase d) {
        /**
         * @Description: get all Messages which send by a User whose id is userID
         */
        List<Message> messages = d.getSentMessageListByUserId(userID);
        List<String> sMessages = new ArrayList<>();
        for (Message message : messages){
            sMessages.add(getStringOfSentMessage(message, d));
        }
        return sMessages;
    }

    public boolean canReplyMessage(int currentuserID, int positionOfMessage, DataBase db){
        /**
         * @Description: judge whether a User whose id is currentuserID is eligible to reply a message which id is
         * positionOfMessage
         */
        Message message = db.getReceivedMessageListByUserId(currentuserID).get(positionOfMessage);
        int senderID = message.getSenderId();
        return db.getOrganizerById(senderID) == null;
    }

    public void replyMessage(String content, int currentuserId, int positionOfMessage, DataBase d) {
        /**
         * @Description: reply a message which id is positionOfMessage
         */
        Message m = d.getReceivedMessageListByUserId(currentuserId).get(positionOfMessage);
        Message reply = new Message(content, m.getReceiverId(), m.getSenderId());
        d.addMessage(reply);
    }


}