package net.jonathangiles.tools.jed.json.gson;

import com.google.gson.Gson;
import net.jonathangiles.tools.jed.json.api.JsonApi;
import net.jonathangiles.tools.jed.json.api.Config;
import net.jonathangiles.tools.jed.json.api.JsonDeserializer;
import net.jonathangiles.tools.jed.json.api.Type;

import java.util.List;

public class GsonDeserializer implements JsonApi {

    private Gson gson = new Gson();

    @Override
    public void configure(Config key, boolean value) {
        // TODO
    }

    @Override
    public <T> void registerCustomDeserializer(JsonDeserializer<T> deserializer) {
        // TODO this overwrites the Gson with a new instance, with the adapter registered.
        gson = gson.newBuilder().registerTypeAdapter(deserializer.getRawType(),
                (com.google.gson.JsonDeserializer<T>) (node, type, ctx) -> deserializer.deserialize(new GsonNode(node))).create();
    }

    public <T> T readString(final String json, final Class<? extends T> cls) {
        return gson.fromJson(json, cls);
    }

    @Override
    public <T> T readString(String json, Type<T> type) {
        return gson.fromJson(json, type.getJavaType());
    }

    @Override
    public <T> List<T> readStringToList(String json, Type<List<T>> type) {
        return gson.fromJson(json, type.getJavaType());
    }
}
