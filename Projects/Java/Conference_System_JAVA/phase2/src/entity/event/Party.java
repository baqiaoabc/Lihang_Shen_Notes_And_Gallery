package entity.event;

import java.time.LocalDateTime;

public class Party extends NonSpeakerEvent{
    public Party(LocalDateTime startTime, LocalDateTime endTime,
                 int eventId, String title, int roomId, int capacity) {
        super(startTime, endTime, eventId, title, roomId, capacity);
    }
}
