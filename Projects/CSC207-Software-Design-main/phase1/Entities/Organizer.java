import java.util.ArrayList;
import java.util.List;

/**
 * The Organizer class, extends from Attendee.
 */
public class Organizer extends Attendee implements java.io.Serializable{

    private ArrayList<Integer> created_event_list;

    /**
     * Constructor for an organizer object
     *
     * @param uid the organizer id
     * @param password the password
     * @param name the user name
     */
    public Organizer(int uid, String password, String name) {
        super(uid, password, name);
        this.created_event_list = new ArrayList<>();
    }

    /**
     * Add created event into the list
     *
     * @param eid the event id
     */
    public void AddCreatedEvent (int eid) {
        if (!created_event_list.contains(eid)) {
            created_event_list.add(eid);
        }
    }

    /**
     * A getter for the created event
     *
     * @return the list of event id this organizer created
     */
    public ArrayList<Integer> getCreatedEventList() {
        return created_event_list;
    }

    @Override
    public String toString() {
        return "Organizer{" +
                "uid=" + super.getUser_id() +
                ", username='" + super.getUserName() + '\'' +
                ", password='" + super.getPassword() + '\'' +
                ", created_event_list=" + created_event_list +
                '}';
    }
}
