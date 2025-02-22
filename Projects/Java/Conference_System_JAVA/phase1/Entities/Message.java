/**
 * The Message class.
 */
public class Message implements java.io.Serializable {
    private String content;
    private int sender_id;
    private int receiver_id;

    /**
     * Constructor of a message object.
     *
     * @param information the message content
     * @param sender_id the sender id
     * @param receiver_id the receiver id
     */
    public Message(String information, int sender_id, int receiver_id){
        this.content = information;
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
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
        return sender_id;
    }

    /**
     * A getter for the receiver id
     *
     * @return the receiver id of this message
     */
    public int getReceiverId() {
        return receiver_id;
    }

    @Override
    public String toString() {
        return "Message{" +
                "content='" + content + '\'' +
                ", sender_id=" + sender_id +
                ", receiver_id=" + receiver_id +
                '}';
    }
}
