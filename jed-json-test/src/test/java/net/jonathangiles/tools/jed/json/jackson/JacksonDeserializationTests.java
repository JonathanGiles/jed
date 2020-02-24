package net.jonathangiles.tools.jed.json.jackson;

import net.jonathangiles.tools.jed.json.JsonDeserializationTests;
import net.jonathangiles.tools.jed.json.JsonWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

public class JacksonDeserializationTests extends JsonDeserializationTests {

    @BeforeEach
    public void createDeserializer() {
        jsonApi = JsonWrapper.newInstance("net.jonathangiles.tools.jed.json.jackson.JacksonDeserializer");
        Assertions.assertNotNull(jsonApi, "Couldn't create instance of deserializer");
    }
}
