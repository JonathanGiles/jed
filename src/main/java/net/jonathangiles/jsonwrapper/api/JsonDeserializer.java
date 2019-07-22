package net.jonathangiles.jsonwrapper.api;

import java.util.List;

public interface JsonDeserializer {

    void configure(Config key, boolean value);

    <T> void registerCustomDeserializer(CustomJsonDeserializer<T> deserializer);

    <T> T readString(String json, Class<? extends T> cls);

    <T> T readString(String json, Type<T> type);

    <T> List<T> readStringToList(String json, Type<List<T>> type);
}