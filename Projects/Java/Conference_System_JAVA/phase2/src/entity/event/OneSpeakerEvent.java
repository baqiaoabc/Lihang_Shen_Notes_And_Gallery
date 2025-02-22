package entity.event;

import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class OneSpeakerEvent extends Event{
    private int speakerId;
    public OneSpeakerEvent(LocalDateTime startTime, LocalDateTime endTime,
                           int eventId, String title, int roomId, int capacity) {
        super(startTime, endTime, eventId, title, roomId, capacity);
    }

    /**
     *
     * @return the speaker id
     */
    public int getSpeakerId() {
        return speakerId;
    }

    /**
     *
     * @Description: set the speaker id
     */
    public void setSpeaker(int speakerId) {
        this.speakerId = speakerId;
    }

}
