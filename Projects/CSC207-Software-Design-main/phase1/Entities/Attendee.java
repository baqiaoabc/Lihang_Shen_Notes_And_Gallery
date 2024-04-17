import java.util.ArrayList;
import java.util.List;

/**
 * The class Attendee, extends from User.
 */
public class Attendee extends User implements java.io.Serializable{

    private ArrayList<Integer> signed_up_event;

    /**
     * Constructor of a Attendee object.
     *
     * @param uid the user id
     * @param password the user password
     * @param name the username
     */
    public Attendee(int uid,String password, String name) {
        super(uid, password, name);
        this.signed_up_event = new ArrayList<>();
    }

    /**
     * Sign up an event.
     *
     * @param eid the event id
     */
    public void signUpEvent (int eid){
            signed_up_event.add(eid);
    }

    /**
     * Cancel an event.
     *
     * @param eid event id
     */
    public void cancelEvent (int eid){
            signed_up_event.remove(eid);
    }

    /**
     * A getter for list of signed up events' ids
     * @return the list of signed up events' ids
     */
    public ArrayList<Integer> getEventList (){
        return signed_up_event;
    }

    @Override
    public String toString() {
        return "Attendee{" +
                "uid=" + super.getUser_id() +
                ", username='" + super.getUserName() + '\'' +
                ", password='" + super.getPassword() + '\'' +
                ", signed_up_event=" + signed_up_event +
                '}';
    }
}
