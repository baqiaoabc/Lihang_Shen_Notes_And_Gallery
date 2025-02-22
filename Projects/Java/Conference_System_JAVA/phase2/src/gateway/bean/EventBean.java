package gateway.bean;

import entity.event.Event;

/**
 * @program: group_0173
 * @description: Storing a event in purpose of easily serializing/deserializing genetic data
 * @create: 2020-12-04 00:31
 **/
public class EventBean {

    private Event[] data = new Event[1];

    public EventBean(Event e) {
        this.data[0] = e;
    }

    public Object convertToObject () {
        return data[0];
    }
}
