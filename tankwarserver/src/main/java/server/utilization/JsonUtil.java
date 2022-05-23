package server.utilization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonUtil {

    private static Gson gson = new Gson();

    public static String toJson(Object object) {
        GsonBuilder builder = new GsonBuilder().serializeNulls();
        gson = builder.create();
        return gson.toJson(object);
    }

    public static <T> T fromJson(String json, Class<T> tClass) {
        GsonBuilder builder = new GsonBuilder();
        gson = builder.create();
        return gson.fromJson(json, tClass);
    }

    public static <T> List<T> fromListJson(String jsonList) {
        GsonBuilder builder = new GsonBuilder();
        gson = builder.create();

        Type listType = new TypeToken<ArrayList<T>>() {}.getType();
        return gson.fromJson(jsonList, listType);
    }

}
