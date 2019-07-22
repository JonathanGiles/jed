package net.jonathangiles.jsonwrapper.jackson;

import net.jonathangiles.jsonwrapper.JsonDeserializationTests;
import org.junit.jupiter.api.BeforeEach;

public class JacksonDeserializationTests extends JsonDeserializationTests {

    @BeforeEach
    public void createDeserializer() {
        jsonDeserializer = new JacksonDeserializer();
    }
}
