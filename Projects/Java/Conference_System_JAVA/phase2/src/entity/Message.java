package entity;

/**
 * The Message class.
 */
public class Message implements java.io.Serializable {
    private String content;
    private int senderId;
    private int receiverId;
    private String title;

    /**
     * Constructor of a message object.
     *
     * @param information the message content
     * @param senderId the sender id
     * @param receiverId the receiver id
     */
    public Message(String title, String information, int senderId, int receiverId){
        this.content = information;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.title = title;
    }

    public String getTitle() {
        /**
         * A getter for the title
         *
         * @return the content of this message
         */
        return title;
    }

    /**
     * A getter for the content
     *
     * @return the content of this message
     */
    public String getInfo() {
        return content;
    }

    /**
     * A getter for the sender id
     *
     * @return the sender id of this message
     */
    public int getSenderId() {
        return senderId;
    }

    /**
     * A getter for the receiver id
     *
     * @return the receiver id of this message
     */
    public int getReceiverId() {
        return receiverId;
    }

    @Override
    public String toString() {
        return "Message{" +
                "title=" + title + '\'' +
                "content='" + content + '\'' +
                ", sender_id=" + senderId +
                ", receiver_id=" + receiverId +
                '}';
    }
}
