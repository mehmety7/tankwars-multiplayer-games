package server.utilization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
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

    public static <T> List<T> fromListJson(String jsonList, Class<T[]> classList) {
        GsonBuilder builder = new GsonBuilder();
        gson = builder.create();

        final T[] jsonToObject = new Gson().fromJson(jsonList, classList);

        return Arrays.asList(jsonToObject);
    }

}
