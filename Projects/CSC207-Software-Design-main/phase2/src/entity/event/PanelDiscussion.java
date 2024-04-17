package entity.event;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class PanelDiscussion extends MultiSpeakerEvent{
    public PanelDiscussion(LocalDateTime startTime, LocalDateTime endTime,
                           int eventId, String title, int roomId, int capacity) {
        super(startTime, endTime, eventId, title, roomId, capacity);
    }
}
