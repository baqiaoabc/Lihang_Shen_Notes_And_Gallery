package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * The class Attendee, extends from User.
 */
public class Attendee extends User implements java.io.Serializable{

    private ArrayList<Integer> signedUpEvent;
    private ArrayList<Integer> myWaitList;

    /**
     * Constructor of a Attendee object.
     *
     * @param uid the user id
     * @param password the user password
     * @param name the username
     */
    public Attendee(int uid,String password, String name) {
        super(uid, password, name);
        this.signedUpEvent = new ArrayList<>();
        this.myWaitList = new ArrayList<>();
    }

    /**
     * Sign up an event.
     *
     * @param eid the event id
     */
    public void signUpEvent (int eid){
        signedUpEvent.add(eid);
    }

    /**
     * Cancel an event.
     *
     * @param eid event id
     */
    public void cancelEvent (int eid){
            signedUpEvent.remove(new Integer(eid));
    }

    /**
     * return the wairlist
     *
     */
    public ArrayList<Integer> getMyWaitList() {
        return myWaitList;
    }

    /**
     * add a waiting event.
     *
     * @param eid event id
     */
    public void addWaitingEvent(int eid) {
        myWaitList.add(eid);
    }

    /**
     * remove an event.
     *
     * @param eid event id
     */
    public void removeWaitingEvent (int eid) {
        myWaitList.remove(new Integer(eid));
    }

    /**
     * A getter for list of signed up events' ids
     * @return the list of signed up events' ids
     */
    public ArrayList<Integer> getSignedUpEventList(){
        return signedUpEvent;
    }

    @Override
    public String toString() {
        return "Attendee{" +
                "uid=" + super.getUserId() +
                ", username='" + super.getUserName() + '\'' +
                ", password='" + super.getPassword() + '\'' +
                ", signed up event=" + signedUpEvent +
                ", my waiting events=" + myWaitList +
                '}';
    }
}
