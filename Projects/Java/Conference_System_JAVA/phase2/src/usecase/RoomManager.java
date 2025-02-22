package usecase;

import java.util.ArrayList;
import java.util.List;
import entity.Room;
import gateway.GatewayFacade;

/**
 * The Room Manager class
 */
public class RoomManager {

    /**
     * Retrun all the rooms in the database
     * @param g the database
     * @return the list of room id in the database
     */
    public List<Room> getListOfRooms(GatewayFacade g) {
        return g.getRoomList();
    }

    /**
     * Judge whether a room can be added to the database
     *
     * @param room_num the room number
     * @param g the database
     * @return the boolean shows that whether a room can be added to the database
     */
    public boolean canAddRoom(String room_num, GatewayFacade g) {
        for (Room r : g.getRoomList()) {
            if (r.getRoomNum().equals(room_num)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Add a new room to the database.
     *
     * @param roomNumber the room number
     * @param g the database
     */
    public void addRoom(String roomNumber, int capacity, GatewayFacade g) {
        Room room = new Room(roomNumber, g.getNextRoomId(), capacity);
        g.addRoom(room);
    }

    /**
     * Get the room id by the room number
     *
     * @param roomNumber the room number
     * @param g the database
     * @return the room id
     */
    public int getRoomIDbyRoomNumber(String roomNumber, GatewayFacade g){
        return g.getRoomByRoomNum(roomNumber).getRid();
    }


    /**
     * Get the room number by the room id
     * @param roomId the room id
     * @param g the database
     * @return the room number
     */
    private List<String> getRoomString(int roomId, GatewayFacade g){
        List<String> rString = new ArrayList<>();
        Room room = g.getRoomById(roomId);
        rString.add(room.getRoomNum());
        rString.add(String.valueOf(room.getCapacity()));
        return rString;
    }


    /**
     * View all rooms in the system.
     *
     * @return Return a list of strings that represent all rooms in the system.
     * Every room is represented by a string formatted as follows: "RoomName/Number (RoomID)"
     */
    public List<List<String>> allRooms(GatewayFacade gw){
        List<Room> allRooms = getListOfRooms(gw);
        List<List<String>> sAllRooms = new ArrayList<>();
        for (Room r: allRooms) {
            List<String> rString = getRoomString(r.getRid(), gw);
            sAllRooms.add(rString);
        }
        return sAllRooms;
    }
}