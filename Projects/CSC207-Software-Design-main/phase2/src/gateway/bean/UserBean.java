package gateway.bean;

import entity.User;

/**
 * @program: group_0173
 * @description: Storing a user in purpose of easily serializing/deserializing genetic data
 * @create: 2020-11-26 18:45
 **/
public class UserBean {

    private User[] data = new User[1];

    public UserBean(User u) {
        this.data[0] = u;
    }

    public Object convertToObject () {
        return data[0];
    }
}
