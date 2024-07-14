import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Jedis_Directly_Connect {
    public static void main(String[] args) {

        //先设置连接参数
        String DATABASE_URL = "192.168.56.128";
        // DB port number
        int DATABASE_PORT = 6379;
        // DB password; should be set in redis.conf file
        final String DATABASE_PASSWORD = "csc207";
        JedisPool jedisPool;

        try {
            // borrow jedis from jedis pool
            Jedis jedis = new Jedis(DATABASE_URL, DATABASE_PORT);
            jedis.auth(DATABASE_PASSWORD);
            // Manipulate DB2
            jedis.select(2);

            // add hashmap content to Redis
            Map<String,String> map = new HashMap<>();
            for(int i=0;i<10;i++){
                map.put("key"+i, i+"_value");
            }
            jedis.hmset("useHM",map);

            // add content not use HashMap
            jedis.hset("notUseHM", "Jim", "999");
            jedis.hset("notUseHM", "Jimmy1", "Hi");
            jedis.hset("notUseHM", "Jimmy2", "Hi");

            System.out.println(jedis.hsetnx("notUseHM", "Jim", "1"));
            System.out.println((jedis.hsetnx("notUseHM", "newJim", "1")));
            System.out.println("=============================================");

            // update already exist data
            jedis.hset("useHM", "key0", "999");

            // increase increment to all value in hashmap
            jedis.hincrBy("notUseHM","Jim",2);
            jedis.hincrByFloat("notUseHM","Jim",0.3);

            // delete specified fields in respective hashtable
            jedis.hdel("notUseHM","Jimmy1", "Jimmy2");

            // check whether specified field exist in hashtable
            System.out.println(jedis.hexists("notUseHM", "Jimmy1"));

            // get data from Jedis
            String str0 = jedis.hget("useHM","key1");
            List<String> list1 = jedis.hmget("useHM","key1","key2","key0");
            Map<String, String> Map2 = jedis.hgetAll("notUseHM");
            System.out.println(str0);
            System.out.println("=============================================");
            System.out.println(list1.toString());
            System.out.println("=============================================");
            System.out.println(Map2.toString());

            // 归还 Jedis 给线程池
            jedis.close();
            System.out.println("Gateway: Jedis Pool has been terminated");
        } catch (Exception e){
            System.err.println("Gateway: Fail to get jedis from jedis pool\n");
            e.printStackTrace();
        }
    }
}