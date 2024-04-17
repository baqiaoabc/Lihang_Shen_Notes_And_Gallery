package gateway.serializationStrategy;

import com.google.gson.Gson;
import entity.User;
import gateway.bean.UserBean;

/**
 * @program: group_0173
 * @description:
 * @author:
 * @create: 2020-12-10 09:35
 **/
public class UserSerializer implements SerializationStrategy {
    @Override
    public Object deserialize(String data, Gson gson) {
        UserBean userBean = gson.fromJson(data, UserBean.class);
        return userBean.convertToObject();
    }

    @Override
    public String serialize(Object obj, Gson gson) {
        return gson.toJson(new UserBean((User)obj));
    }
}
