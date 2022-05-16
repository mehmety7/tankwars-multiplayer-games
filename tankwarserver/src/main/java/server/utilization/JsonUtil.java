package server.utilization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtil {

    private static Gson gson = new Gson();

    public static String toJson(Object object) {
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        gson = builder.create();
        return gson.toJson(object);
    }

    public static <T> T fromJson(String json, Class<T> tClass) {
        GsonBuilder builder = new GsonBuilder();
        gson = builder.create();
        return gson.fromJson(json, tClass);
    }

}
