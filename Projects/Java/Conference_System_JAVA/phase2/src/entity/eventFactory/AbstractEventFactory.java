package entity.eventFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import entity.event.*;

public abstract class AbstractEventFactory {

    public abstract Event getEvent(int type, LocalDateTime startTime, LocalDateTime endTime,
                                   int eventId, String title, int roomId, int capacity);
}
