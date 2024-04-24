package gateway;

import entity.Organizer;
import entity.User;

/**
 * @program: group_0173
 * @description: Configuration of DATABASE.
 * @create: 2020-11-30 14:33
 **/
public class Config {
    // server ip Address
    static final String DATABASE_URL = "192.168.186.128";
    // DB port number
    static final int DATABASE_PORT = 6379;
    // DB password; should be set in redis.conf file
    static final String DATABASE_PASSWORD = "csc207";

    static final String NEXT_USER_ID = "next_user_id";
    static final String NEXT_EVENT_ID = "next_event_id";
    static final String NEXT_ROOM_ID = "next_room_id";
    static final String USER_HASH = "user_hash";
    static final String EVENT_HASH = "event_hash";
    static final String ROOM_HASH = "room_hash";
    static final String MESSAGE_LIST = "message_list";
}
