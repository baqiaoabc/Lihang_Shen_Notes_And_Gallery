package entity;

import java.util.ArrayList;

/**
 * The Organizer class, extends from Attendee.
 */
public class Organizer extends Attendee implements java.io.Serializable{

    private ArrayList<Integer> createdEventList;

    /**
     * Constructor for an organizer object
     *
     * @param uid the organizer id
     * @param password the password
     * @param name the user name
     */
    public Organizer(int uid, String password, String name) {
        super(uid, password, name);
        this.createdEventList = new ArrayList<>();
    }

    /**
     * Add created event into the list
     *
     * @param eid the event id
     */
    public void AddCreatedEvent (int eid) {
        if (!createdEventList.contains(eid)) {
            createdEventList.add(eid);
        }
    }

    /**
     * A getter for the created event
     *
     * @return the list of event id this organizer created
     */
    public ArrayList<Integer> getCreatedEventList() {
        return createdEventList;
    }

    @Override
    public String toString() {
        return "Organizer{" +
                "uid=" + super.getUserId() +
                ", username='" + super.getUserName() + '\'' +
                ", password='" + super.getPassword() + '\'' +
                ", created event list=" + createdEventList +
                ", signedup event list=" + this.getSignedUpEventList() +
                ", waiting event list=" + this.getMyWaitList() +
                '}';
    }

    public void cancelCreatedEvent(int eID){
        createdEventList.remove(new Integer(eID));
    }
}
