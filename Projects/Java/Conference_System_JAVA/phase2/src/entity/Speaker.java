package entity;

import java.util.ArrayList;

/**
 * The Speaker class.
 */
public class Speaker extends User implements java.io.Serializable{

    private ArrayList<Integer> givingEventList;

    /**
     * Constructor for the speaker object
     *
     * @param uid the speaker id
     * @param password the password
     * @param name the user name
     */
    public Speaker(int uid, String password, String name) {
        super(uid, password, name);
        this.givingEventList = new ArrayList<>();
    }

    /**
     * Add event id to the giving event list
     *
     * @param eid the event id new assigned to speaker
     */
    public void addGivingEvent (int eid){
        givingEventList.add(eid);
    }

    /**
     * Remove event id to the giving event list
     *
     * @param eid the event id need to be removed
     */
    public void removeGivingEvent (int eid) {
        givingEventList.remove((Object) eid);}

    /**
     *
     * return the current event list
     */
    public ArrayList<Integer> get_GivingEventList(){
        return givingEventList;
    }

    /**
     *
     * Return the user id
     */
    @Override
    public int getUserId() {
        return super.getUserId();
    }

    @Override
    public String toString() {
        return "Speaker{" +
                "user id=" + super.getUserId() +
                ", username='" + super.getUserName() + '\'' +
                ", password='" + super.getPassword() + '\'' +
                ", giving event list=" + givingEventList +
                '}';
    }
}
