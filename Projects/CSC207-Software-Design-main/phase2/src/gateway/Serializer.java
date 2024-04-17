package gateway;

import com.google.gson.Gson;
import entity.User;
import entity.event.Event;
import gateway.bean.EventBean;
import gateway.bean.UserBean;
import gateway.serializationStrategy.EventSerializer;
import gateway.serializationStrategy.UserSerializer;

import java.lang.reflect.Type;

/**
 * @program: group_0173
 * @description: Serializer to determine which data bean should be used to ser/deser specific type of data
 * @create: 2020-12-03 23:38
 **/
public class Serializer {

    /**
    * @Description: deserialize the data with given gson and type
    * @Param: [data, gson, type]
    * @return: java.lang.Object
    * @Date: 2020-12-10
    */
    public Object deserialize (String data, Gson gson, Type type) {
        if (type.equals(User.class)) {  // User Class
            return new UserSerializer().deserialize(data, gson);
        } else if (type.equals(Event.class)) {  // Event Class
            return new EventSerializer().deserialize(data, gson);
        }
        return null;
    }

    /**
    * @Description: serialize the data with given gson and type
    * @Param: [obj, gson, type]
    * @return: java.lang.String
    * @Date: 2020-12-10
    */
    public String serialize (Object obj, Gson gson, Type type) {
        if (type.equals(User.class)) {  // User Class
            return new UserSerializer().serialize(obj, gson);
        } else if (type.equals(Event.class)) {  // Event Class
            return new EventSerializer().serialize(obj, gson);
        }
        return null;
    }
}
