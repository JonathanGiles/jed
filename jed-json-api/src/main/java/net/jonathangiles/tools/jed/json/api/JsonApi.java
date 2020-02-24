package net.jonathangiles.tools.jed.json.api;

import java.util.List;

public interface JsonApi {

    void configure(Config key, boolean value);

    <T> void registerCustomDeserializer(JsonDeserializer<T> deserializer);

    <T> T readString(String json, Class<? extends T> cls);

    <T> T readString(String json, Type<T> type);

    <T> List<T> readStringToList(String json, Type<List<T>> type);
}
