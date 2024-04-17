package gateway;

import redis.clients.jedis.Jedis;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: group_0173
 * @description: Gateway operating one field-value in database with type of list.
 * @create: 2020-12-03 21:18
 **/
public class ListGateway<T> extends Gateway<T> {

    private String listKey;

    /**
    * @Description: Constructor
    * @Param: [listKey, type, genericEnable]
    */
    public ListGateway(String listKey, Type type, boolean genericEnable) {
        super(type, genericEnable);
        this.listKey = listKey;
    }

    /**
    * @Description: Add object to the remote list
    * @Param: [obj]
    * @return: void
    */
    public void add (T obj) {
        String value = serialize(obj);
        Jedis jedis = getJedis();
        String type = jedis.type(this.listKey);
        if (!type.equals("list")) {
            jedis.del(this.listKey);
        }
        jedis.lpush(this.listKey, value);
        closeJedis(jedis);
    }

    /**
    * @Description: Get the whole list from the remote list
    * @Param: []
    * @return: java.util.List<T>
    */
    public List<T> getList () {
        Jedis jedis = getJedis();
        List<String> dateList = jedis.lrange(this.listKey,0, -1);
        closeJedis(jedis);
        List<T> list = new ArrayList<>();
        for (String data : dateList) {
            try {
                list.add(deserialize(data));
            } catch(Exception e) {
            }
        }
        return list;
    }
}
