package usecase;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import entity.*;
import entity.event.Event;
import gateway.GatewayFacade;
public class MessageManager {

    /**
     * @Description: message all signed up users in an event
     */
    public void messageAllUsersInEvent(int eventId, int senderId, String title, String content, GatewayFacade g){
        for (int receiverId : g.getEventById(eventId).getSignedUpUserList()){
            Message m = new Message(title, content, senderId, receiverId);
            g.addMessage(m);
        }
    }

    /**
     * @Description: message all signed up users in multiple speaking events
     */
    public void messageAllUsersInAllSpeakingEvents(int speakerID, String title, String content, GatewayFacade g){
        Speaker speaker = g.getSpeakerById(speakerID);
        List<Integer> speakingEvents = speaker.get_GivingEventList();
        for (int eventID : speakingEvents){
            messageAllUsersInEvent(eventID, speakerID, title, content, g);
        }
    }

    /**
     * @Description: judge whether receiver whose Id is receiverID participate Event which id is eventId
     */
    public boolean canMessageAttendeeOfSpeakingEvent(int eventId, int receiverId, GatewayFacade g){
        return g.getEventById(eventId).getSignedUpUserList().contains(receiverId);
    }

    public List<Integer> allMessageableAttendee(int userId, GatewayFacade g){
        List<Integer> eventListId = g.getAttendeeById(userId).getSignedUpEventList();
        List<Integer> canMessageableList = new ArrayList<>();
        for (int eventId: eventListId) {
            canMessageableList.addAll(g.getEventById(eventId).getSignedUpUserList());
        }
        List<Integer> listWithoutDuplicates = canMessageableList.stream().distinct().collect(Collectors.toList());
        listWithoutDuplicates.remove(userId);
        return listWithoutDuplicates;
    }

    /**
     * @Description: send message to one user whose id is receiverId
     */
    public void messageOneUser(int senderId, int receiverId, String title, String content, GatewayFacade g){
        Message m = new Message(title, content, senderId, receiverId);
        g.addMessage(m);
    }

    /**
     * @Description: judge whether a User whose Id is senderId is eligible to send a message to a Speaker whose
     * id is receiverId
     */
    public boolean canMessageSpeaker(int senderId, int receiverId, GatewayFacade g){
        boolean senderCheck = g.getOrganizerById(senderId) != null || g.getAttendeeById(senderId) != null;
        return senderCheck && g.getSpeakerById(receiverId) != null;
    }


    /**
     * @Description: judge whether a User whose Id is senderId is eligible to send a message to a Attendee whose
     * id is receiverId
     */
    public boolean canMessageAttendee(int senderId, int receiverId, GatewayFacade g){
        boolean senderCheck = g.getOrganizerById(senderId) != null || g.getAttendeeById(senderId) != null;
        boolean ishimself = senderId == receiverId;
        return (!ishimself) && senderCheck && g.getAttendeeById(receiverId) != null
                && g.getOrganizerById(receiverId) == null;
    }


    /**
     * @Description: judge whether a User is an Organizer
     */
    public boolean canMessageAllSpeakersOrAllAttendee(int senderId, GatewayFacade g){
        return g.getOrganizerById(senderId) != null;
    }


    /**
     * @Description: message all Speaker
     */
    public void messageAllSpeakers(String title, String content, int senderId, GatewayFacade g) {
        List<User> users = g.getUserList();
        for (User user : users){
            if (user instanceof Speaker){
                messageOneUser(senderId, user.getUserId(), title, content, g);
            }
        }
    }

    /**
     * @Description: message all Attendee
     */
    public void messageAllAttendees(int senderId, String title, String content, GatewayFacade g){
        List<User> users = g.getUserList();
        for (User user : users){
            if (user instanceof Attendee & !(user instanceof Organizer)){
                messageOneUser(senderId, user.getUserId(), title, content, g);
            }
        }
    }

    /**
     * Get formatted string of a received message
     * @param message the message wanted
     * @return String of the message in format "From: xxx Content: yyy"
     */
    private String getStringOfReceivedMessage(Message message, GatewayFacade g){
        return "From: " + g.getUserById(message.getSenderId()).getUserName() +
                " Content: " + message.getInfo();
    }

    /**
     * Get formatted string of a sent message
     * @param message the message wanted
     * @return String of the message in format "To: xxx Content: yyy"
     */
    private String getStringOfSentMessage(Message message, GatewayFacade g){
        return "To: " + g.getUserById(message.getReceiverId()).getUserName() +
                " Content: " + message.getInfo();
    }

    /**
     * Get received message list by using the user id
     * @param userID user id
     */
    public List<List<String>> getReceivedMessageListByUserId(int userID, GatewayFacade g){
        List<Message> messages = g.getReceivedMessageListByUserId(userID);
        List<List<String>> allMessageInfo = new ArrayList<>();
        for (Message message : messages){
            List<String> messageInfo = new ArrayList<String>(){
                {
                    add(String.valueOf(g.getUserById(message.getSenderId()).getUserName()));
                    add(message.getTitle());
                    add(message.getInfo());
                }
            };
            allMessageInfo.add(messageInfo);
        }
        return allMessageInfo;
    }


    /**
     * @Description: get all Messages which send by a User whose id is userID
     */
    public List<List<String>> getSentMessageListByUserId(int userID, GatewayFacade g) {
        List<Message> messages = g.getSentMessageListByUserId(userID);
        List<List<String>> allMessageInfo = new ArrayList<>();
        for (Message message : messages){
            List<String> messageInfo = new ArrayList<String>(){
                {
                    add(String.valueOf(g.getUserById(message.getReceiverId()).getUserName()));
                    add(message.getTitle());
                    add(message.getInfo());
                }
            };
            allMessageInfo.add(messageInfo);
        }
        return allMessageInfo;
    }


    /**
     * @Description: judge whether a User whose id is currentuserID is eligible to reply a message which id is
     * positionOfMessage
     */
    public boolean canReplyMessage(int currentUserID, int positionOfMessage, GatewayFacade g){
        Message message = g.getReceivedMessageListByUserId(currentUserID).get(positionOfMessage);
        int senderID = message.getSenderId();
        return g.getOrganizerById(senderID) == null;
    }

    /**
     * @Description: reply a message which id is positionOfMessage
     */
    public void replyMessage(String title, String content, int currentUserId, int positionOfMessage, GatewayFacade g) {
        Message m = g.getReceivedMessageListByUserId(currentUserId).get(positionOfMessage);
        Message reply = new Message(title, content, m.getReceiverId(), m.getSenderId());
        g.addMessage(reply);
    }


}