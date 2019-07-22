package net.jonathangiles.jsonwrapper.gson;

import net.jonathangiles.jsonwrapper.JsonDeserializationTests;
import net.jonathangiles.jsonwrapper.JsonWrapper;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class GsonDeserializationTests extends JsonDeserializationTests {

    @BeforeEach
    public void createDeserializer() {
        jsonApi = JsonWrapper.newInstance("net.jonathangiles.jsonwrapper.gson.GsonDeserializer");
        assertNotNull(jsonApi, "Couldn't create instance of deserializer");
    }
}
