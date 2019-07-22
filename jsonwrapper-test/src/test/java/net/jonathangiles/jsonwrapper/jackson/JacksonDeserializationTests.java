package net.jonathangiles.jsonwrapper.jackson;

import net.jonathangiles.jsonwrapper.JsonDeserializationTests;
import net.jonathangiles.jsonwrapper.JsonWrapper;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class JacksonDeserializationTests extends JsonDeserializationTests {

    @BeforeEach
    public void createDeserializer() {
        jsonApi = JsonWrapper.newInstance("net.jonathangiles.jsonwrapper.jackson.JacksonDeserializer");
        assertNotNull(jsonApi, "Couldn't create instance of deserializer");
    }
}
