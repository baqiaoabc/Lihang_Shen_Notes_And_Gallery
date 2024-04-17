package gateway.serializationStrategy;

import com.google.gson.Gson;
import entity.User;
import entity.event.Event;
import gateway.bean.EventBean;
import gateway.bean.UserBean;

/**
 * @program: group_0173
 * @description:
 * @author:
 * @create: 2020-12-10 09:41
 **/
public class EventSerializer implements SerializationStrategy {
    @Override
    public Object deserialize(String data, Gson gson) {
        EventBean eventBean = gson.fromJson(data, EventBean.class);
        return eventBean.convertToObject();
    }

    @Override
    public String serialize(Object obj, Gson gson) {
        return gson.toJson(new EventBean((Event)obj));
    }
}
