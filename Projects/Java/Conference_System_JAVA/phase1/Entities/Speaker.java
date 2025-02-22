import java.util.ArrayList;
import java.util.List;

/**
 * The Speaker class.
 */
public class Speaker extends User implements java.io.Serializable{

    private ArrayList<Integer> giving_event_list;

    /**
     * Constructor for the speaker object
     *
     * @param uid the speaker id
     * @param password the password
     * @param name the user name
     */
    public Speaker(int uid, String password, String name) {
        super(uid, password, name);
        this.giving_event_list = new ArrayList<>();
    }

    /**
     * Add event id to the giving event list
     *
     * @param eid the event id new assigned to speaker
     */
    public void addGivingEvent (int eid){
        giving_event_list.add(eid);
    }

    /**
     * Remove event id to the giving event list
     *
     * @param eid the event id need to be removed
     */
    public void removeGivingEvent (int eid) {giving_event_list.remove((Object) eid);}

    public ArrayList<Integer> get_GivingEventList(){
        return giving_event_list;
    }

    @Override
    public String toString() {
        return "Speaker{" +
                "uid=" + super.getUser_id() +
                ", username='" + super.getUserName() + '\'' +
                ", password='" + super.getPassword() + '\'' +
                ", giving_event_list=" + giving_event_list +
                '}';
    }
}
