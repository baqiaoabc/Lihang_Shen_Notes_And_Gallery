package entity.event;

//import com.sun.istack.internal.Nullable;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class  Event {
    private int eventId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Duration duration;
    private String title;
    private int roomId;
    private int capacity;
    private boolean isVipEvent = false;
    private ArrayList<Integer> signedUserList = new ArrayList<>();
    private ArrayList<Integer> waitList = new ArrayList<>();


    public Event(LocalDateTime startTime, LocalDateTime endTime, int eventId, String title, int roomId, int capacity){
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventId = eventId;
        this.title = title;
        this.roomId = roomId;
        this.capacity = capacity;
        duration = Duration.between(startTime,endTime);
    }

    /**
     *
     * @return the event id
     */
    public int getEventId() {
        return eventId;
    }

    /**
     *
     * @return the starting time of an event
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     *
     * @return the ending time of an event
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     *
     * @return the duration of an event
     */
    public Duration getDuration() {
        return duration;
    }

    /**
     *
     * @return the title of an event
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @return the room id of an event
     */
    public int getRoomId() {
        return roomId;
    }

    /**
     *
     * @return the capacity of an event
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * @param capacity capacity of an event
     * @Description: set the capacity of an event
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     *
     * @Description: check if an event is VIP or not
     */
    public boolean isVipEvent() {
        return isVipEvent;
    }

    /**
     *
     * @Description: set an event to VIP
     */
    public void setVipEvent(boolean vipEvent) {
        isVipEvent = vipEvent;
    }

    /**
     *
     * @return the signed up user list
     */
    public ArrayList<Integer> getSignedUpUserList() {
        return signedUserList;
    }

    /**
     *
     * @return the waiting list
     */
    public ArrayList<Integer> getWaitList() {
        return waitList;
    }

    /**
     *
     * @param userId the id of the user
     * @Description: add the user to event
     */
    public void addUserToEvent(int userId) {signedUserList.add(userId);}

    /**
     *
     * @param userId the id of the user
     * @Description: remove the user from the event
     */
    public void removeUserFromEvent(int userId) {
        signedUserList.remove(new Integer(userId));
    };

    /**
     *
     * @param userId the id of the user
     * @Description: add the user to waiting list
     */
    public void addUserToWaitList(int userId) {waitList.add(userId);}

    /**
     *
     * @param userId the id of the user
     * @Description: remove the user from waiting list.
     */
    public void removeUserFromWaitList(int userId) {waitList.remove(new Integer (userId));}

    @Override
    public String toString() {
        if (this instanceof Party){
            if (this.isVipEvent){
                return "VIPParty{" +
                        "eventID=" + eventId +
                        ", title='" + title + '\'' +
                        ", startTime=" + startTime +
                        ", endTime=" + endTime +
                        ", roomId=" + roomId +
                        ", capacity=" + capacity +
                        ", signedUserList=" + signedUserList +
                        ", waitList=" + waitList +
                        '}';
            }
            else{
                return "Party{" +
                        "eventID=" + eventId +
                        ", title='" + title + '\'' +
                        ", startTime=" + startTime +
                        ", endTime=" + endTime +
                        ", roomId=" + roomId +
                        ", capacity=" + capacity +
                        ", signedUserList=" + signedUserList +
                        ", waitList=" + waitList +
                        '}';
            }
        }
        if (this instanceof Talk){
            if (this.isVipEvent){
                return "VIPTalk{" +
                        "eventID=" + eventId +
                        ", title='" + title + '\'' +
                        ", speaker=" + ((Talk) this).getSpeakerId() +
                        ", startTime=" + startTime +
                        ", endTime=" + endTime +
                        ", roomId=" + roomId +
                        ", capacity=" + capacity +
                        ", signedUserList=" + signedUserList +
                        ", waitList=" + waitList +
                        '}';
            }
            else{
                return "Talk{" +
                        "eventID=" + eventId +
                        ", title='" + title + '\'' +
                        ", speaker=" + ((Talk) this).getSpeakerId() +
                        ", startTime=" + startTime +
                        ", endTime=" + endTime +
                        ", roomId=" + roomId +
                        ", capacity=" + capacity +
                        ", signedUserList=" + signedUserList +
                        ", waitList=" + waitList +
                        '}';
            }
        }
        if (this instanceof PanelDiscussion){
            if (this.isVipEvent){
                return "VIPPanelDiscussion{" +
                        "eventID=" + eventId +
                        ", title='" + title + '\'' +
                        ", speaker=" + ((PanelDiscussion) this).getSpeakerId() +
                        ", startTime=" + startTime +
                        ", endTime=" + endTime +
                        ", roomId=" + roomId +
                        ", capacity=" + capacity +
                        ", signedUserList=" + signedUserList +
                        ", waitList=" + waitList +
                        '}';
            }
            else{
                return "PanelDiscussion{" +
                        "eventID=" + eventId +
                        ", title='" + title + '\'' +
                        ", speaker=" + ((PanelDiscussion) this).getSpeakerId() +
                        ", startTime=" + startTime +
                        ", endTime=" + endTime +
                        ", roomId=" + roomId +
                        ", capacity=" + capacity +
                        ", signedUserList=" + signedUserList +
                        ", waitList=" + waitList +
                        '}';
            }
        }
        return "Event{" +
                "eventID=" + eventId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", duration=" + duration +
                ", title='" + title + '\'' +
                ", roomId=" + roomId +
                ", capacity=" + capacity +
                ", isVipEvent=" + isVipEvent +
                ", signedUserList=" + signedUserList +
                ", waitList=" + waitList +
                '}';
    }
}
