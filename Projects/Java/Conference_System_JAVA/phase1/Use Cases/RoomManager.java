import java.util.ArrayList;
import java.util.List;

//!!!ADD FUNCTION NAMES IF YOU THINK NECESSARY!!!!!!
// FEEL FREE TO ADD THEM!!! THANKSSSS :)

/**
 * The Room Manager class
 */
public class RoomManager {

    /**
     * Retrun all the rooms in the database
     * @param bd the database
     * @return the list of room id in the database
     */
    public List<Room> getListOfRooms(DataBase bd) {
        return bd.getRoomList();
    }

    /**
     * Judge whether a room can be added to the database
     *
     * @param room_num the room number
     * @param bd the database
     * @return the boolean shows that whether a room can be added to the database
     */
    public boolean canAddRoom(String room_num, DataBase bd) {
        for (Room r : bd.getRoomList()) {
            if (r.getRoom_num().equals(room_num)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Add a new room to the database.
     *
     * @param roomNumber the room number
     * @param d the database
     */
    public void add_room(String roomNumber, DataBase d) {
        Room room = new Room(roomNumber, d.getNextRoomId());
        d.addRoom(room);
    }

    /**
     * Get the room id by the room number
     *
     * @param roomNumber the room number
     * @param db the database
     * @return the room id
     */
    public int getRoomIDbyRoomNumber(String roomNumber, DataBase db){
        return db.getRoomByRoomNum(roomNumber).getRid();
    }

    /**
     * Get the room number by the room id
     * @param roomId the room id
     * @param db the database
     * @return the room number
     */
    public String getRoomString(int roomId, DataBase db){
        Room room = db.getRoomById(roomId);
        return room.getRoom_num();
    }
}