package gateway;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * @program: group_0173
 * @description: UserAdapter is a Gson Adapter for serializing User Class and its sub Classes
 * @example: {"CLASSNAME":"entity.Attendee","DATA":{"signedUpEvent":[13,15],"myWaitList":[],"uid":1,"username":"attendee1","password":"password"}}
 * @note: This can only be used to determine the type in the attributes in one class. Such as serialize the User in UserBean's attribute. It cannot
 * serialize the User class and know what type it is.
 * @reference: https://technology.finra.org/code/serialize-deserialize-interfaces-in-java.html
 * @reference: https://medium.com/@ezralazuardy/gson-custom-interface-adapter-3f3b863e063d
 * @create: 2020-11-27 11:09
 **/
public class GenericAdapter implements JsonSerializer, JsonDeserializer{

    private static final String CLASSNAME = "CLASSNAME";
    private static final String DATA = "DATA";

    /**
    * @Description: Deserialize the element with its type
    * @Param: [jsonElement, type, jsonDeserializationContext]
    * @return: java.lang.Object
    */
    public Object deserialize(JsonElement jsonElement, Type type,
                              JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonPrimitive prim = (JsonPrimitive) jsonObject.get(CLASSNAME);
        String className = prim.getAsString();
        Class klass = getObjectClass(className);
        return jsonDeserializationContext.deserialize(jsonObject.get(DATA), klass);
    }

    /**
    * @Description: Serialize the object and saving its type in the json file
    * @Param: [jsonElement, type, jsonSerializationContext]
    * @return: com.google.gson.JsonElement
    * @Date: 2020-12-10
    */
    public JsonElement serialize(Object jsonElement, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(CLASSNAME, jsonElement.getClass().getName());
        jsonObject.add(DATA, jsonSerializationContext.serialize(jsonElement));
        return jsonObject;
    }

    /**
    * @Description: Helper function of getting class of object
    * @Param: [className]
    * @return: java.lang.Class
    */
    public Class getObjectClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e.getMessage());
        }
    }
}
