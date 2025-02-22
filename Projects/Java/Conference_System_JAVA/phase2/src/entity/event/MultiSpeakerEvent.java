package entity.event;

import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class MultiSpeakerEvent extends Event{
    private ArrayList<Integer> speakerList = new ArrayList<>();

    public MultiSpeakerEvent(LocalDateTime startTime, LocalDateTime endTime,
                             int eventId, String title, int roomId, int capacity) {
        super(startTime, endTime, eventId, title, roomId, capacity);
    }

    /**
     *
     * @return the speaker list
     */
    public ArrayList<Integer> getSpeakerList() {
        return speakerList;
    }

    /**
     *
     * @return the speaker id
     */
    public void addNewSpeaker(int speakerId) {
        speakerList.add(speakerId);
    }

    /**
     *
     * @Description: set the speaker
     */
    public void setSpeaker(ArrayList<Integer> speakerList) {
        this.speakerList = speakerList;
    }

    /**
     *
     * @return the id of the speaker
     */
    public ArrayList<Integer> getSpeakerId(){
        return speakerList;
    }
}
