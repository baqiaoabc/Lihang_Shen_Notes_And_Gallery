import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

/**
 * The Event class.
 */
public class Event implements java.io.Serializable{
    private LocalDateTime start_time; // think about time.localdatetime.
    private int speaker_id;
    private String title;
    private int room_id;
    private ArrayList<Integer> singned_userid;
    private int event_id;

    /**
     * Constructor of an event object.
     *
     * @param start the start time of event
     * @param event_id the event id
     * @param speaker_id the speaker id of this event
     * @param topic the title of the event
     * @param room_n the room number this event will be hold
     */
    public Event(LocalDateTime start, int event_id, int speaker_id, String topic, int room_n){
        this.start_time = start;
        this.speaker_id = speaker_id;
        this.title = topic;
        this.room_id = room_n;
        this.singned_userid = new ArrayList<>();
        this.event_id = event_id;
    }

    /**
     * Remove a signed up user from this event.
     *
     * @param user_id the user id
     */
    public void remove_user(int user_id){
        singned_userid.remove(new Integer(user_id));
    }

    /**
     * Add a user who want to sign up for this event.
     * @param user_id the user id
     */
    public void add_user(int user_id){
        singned_userid.add(user_id);
    }

    /**
     * a getter for the start time of this event.
     *
     * @return the start time
     */
    public LocalDateTime getStart_time() {
        return start_time;
    }

    /**
     * a getter for the speaker id of this event.
     *
     * @return the speaker id
     */
    public int getSpeakerId() {
        return speaker_id;
    }

    /**
     * A getter for the title of this event.
     *
     * @return the title of this event
     */
    public String getTitle() {
        return title;
    }

    /**
     * A getter for the room id of this event.
     *
     * @return the room id this event will take place
     */
    public int getRoomId() {
        return room_id;
    }

    /**
     * A getter for the event id
     *
     * @return the event id of this event
     */
    public int getEvent_id() {
        return event_id;
    }

    public ArrayList<Integer> getSingned_userid() {
        return singned_userid;
    }

    public void setSpeaker_id(int speaker_id) {
        this.speaker_id = speaker_id;
    }

    @Override
    public String toString() {
        return "Event{" +
                "speaker_id=" + speaker_id +
                ", event_id=" + event_id +
                '}';
    }

    public void addSigned_userId(User user) {
        this.singned_userid.add(user.getUser_id());
    }
}
