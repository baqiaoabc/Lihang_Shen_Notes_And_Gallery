package gateway.serializationStrategy;

import com.google.gson.Gson;

/**
 * @program: group_0173
 * @description:
 * @create: 2020-12-10 09:31
 **/
public interface SerializationStrategy {

    /**
    * @Description: deserialize the data with
    * @Param: [data]
    * @return: java.lang.Object
    */
    Object deserialize(String data, Gson gson);

    /**
    * @Description: deserialize the given data
    * @Param: [obj]
    * @return: java.lang.String
    */
    String serialize (Object obj, Gson gson);

}
