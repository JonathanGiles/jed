package net.jonathangiles.jsonwrapper;

import net.jonathangiles.jsonwrapper.api.JsonDeserializer;
import net.jonathangiles.jsonwrapper.jackson.JacksonDeserializer;

public class JsonWrapper {

    public static JsonDeserializer getDeserializer() {
        return new JacksonDeserializer();
    }
}
