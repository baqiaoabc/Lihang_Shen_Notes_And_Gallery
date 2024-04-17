/**
 * The Room class
 */
public class Room implements java.io.Serializable {
    private String room_num;
    private final int rid;
    private int capacity;

    /**
     * Constructor of Room object.
     *
     * @param room_num the room number
     * @param rid the room id
     */
    public Room(String room_num, int rid){
        this.capacity = 2;
        this.room_num = room_num;
        this.rid = rid;
    }

    /**
     * A getter for the room number
     *
     * @return the room number
     */
    public String getRoom_num() {
        return room_num;
    }

    /**
     * A getter for the room capacity
     *
     * @return the room capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * A getter for the room id
     *
     * @return the room id
     */
    public int getRid() {
        return rid;
    }

    @Override
    public String toString() {
        return "Room{" +
                "room_num=" + room_num +
                ", rid=" + rid +
                ", capacity=" + capacity +
                '}';
    }
}