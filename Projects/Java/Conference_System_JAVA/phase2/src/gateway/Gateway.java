package gateway;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.lang.reflect.Type;

/**
 * @program: group_0173
 * @description: Config is the configuration of database, please make sure setup it correctly and put it one the same package.
 * @create: 2020-12-03 17:56
 **/
public abstract class Gateway<T> {

    private static JedisPool jedisPool;

    static final String DATABASE_URL = Config.DATABASE_URL;
    static final int DATABASE_PORT = Config.DATABASE_PORT;
    static final String DATABASE_PASSWORD = Config.DATABASE_PASSWORD;

    private boolean genericEnable;
    private Gson gson;
    private Type type;

    /** Constructor */
    /**
    * @Description: Default constructor is only for testing
    * @Date: 2020-12-06
    */
    public Gateway() {
        initJedisPool();
    }

    /**
    * @Description: Constructor for usage
    * @Param: [type, genericEnable]
    * @return:
    */
    public Gateway(Type type, boolean genericEnable) {
        setAttributes(type, genericEnable);
        initJedisPool();
    }

    /** Constructor */
    /**
    * @Description: Helper function for setting attributes for constructor
    * @Param: [type, genericEnable]
    * @return: void
    * @Date: 2020-12-10
    */
    private void setAttributes (Type type, boolean genericEnable) {
        this.type = type;
        this.genericEnable = genericEnable;
        if (genericEnable) {
            this.gson = buildGson(type, new GenericAdapter());
        } else {
            this.gson = buildGson();
        }
    }

    /** Private Method - Jedis Pool */
    /**
    * @Description: Initialization of the Jedis Pool.
    * @Param: []
    * @return: void
    */
    private void initJedisPool() {
        if (jedisPool == null) {
            JedisPoolConfig poolConfig = new JedisPoolConfig();
            jedisPool = new JedisPool(poolConfig, DATABASE_URL, DATABASE_PORT, 10000, DATABASE_PASSWORD);
            shutDownHook(); // ShutdownHook added
            System.out.println("Gateway: " + jedisPool.toString() + " has been Established");
            ping();
        }
    }

    /**
    * @Description: Close the current jedis pool and release the memory.
    * @Param: []
    * @return: void
    */
    private void close() {
        if(jedisPool != null){
            jedisPool.destroy();
            System.out.println("Gateway: Jedis Pool has been terminated");
        }
    }

    /**
    * @Description: Setup a shutdown hook for close the jedis pool before termination of this program
    * @Param: []
    * @return: void
    */
    private void shutDownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> close()));
        System.out.println(getClass() + "Gateway: ShutdownHook has been Added");
    }

    /** Public Methods */
    /**
    * @Description: Get the jedis from jedis pool
    * @Param: []
    * @return: redis.clients.jedis.Jedis
    */
    public Jedis getJedis() {
        try{
            return jedisPool.getResource();
        }catch(Exception e) {
            System.err.println(getClass() + "Gateway: Fail to get jedis from jedis pool\n");
            e.printStackTrace();
            return null;
        }
    }

    /**
    * @Description: Close the Jedis to release the memory.
    * @Param: [jedis]
    * @return: void
    * @Date: 2020-12-10
    */
    public void closeJedis(Jedis jedis) {
        if(null != jedis) jedis.close();
    }

    /**
    * @Description: Display the current ping status
    * @Param: []
    * @return: void
    * @Date: 2020-12-10
    */
    public void ping() {
        try(Jedis jedis = jedisPool.getResource()){
            System.out.println("Gateway: Jedis" + (jedis.ping().equals("PONG") ? "is connected" : "is disconnected"));
        }
    }

    /** Private Methods - Gson Builder */
    /**
    * @Description: Return Gson with configuration
    * @Param: []
    * @return: com.google.gson.Gson
    */
    private Gson buildGson () {
        return new GsonBuilder().serializeNulls().create();
    }

    /**
    * @Description: Return Gson with configuration and specific type adapter
    * @Param: [type, typeAdapter]
    * @return: com.google.gson.Gson
    */
    private Gson buildGson (Type type, Object typeAdapter) {
        return new GsonBuilder().registerTypeAdapter(type, typeAdapter).serializeNulls().create();
    }

    /** Public Methods - Gson */
    /**
    * @Description: serialize the given data with its type. (SerializationStrategy help with type analysis)
    * @Param: [obj]
    * @return: java.lang.String
    */
    public String serialize (T obj) {
        if (genericEnable) {
            return new Serializer().serialize(obj, gson, type);
        } else {
            return gson.toJson(obj);
        }
    }

    /**
    * @Description: deserialize the given data. (SerializationStrategy help with type analysis)
    * @Param: [data]
    */
    public T deserialize (String data) {
        if (genericEnable) {
            return (T)new Serializer().deserialize(data, gson, type);
        } else {
            return (T)gson.fromJson(data, type);
        }
    }
}