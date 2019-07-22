package net.jonathangiles.jsonwrapper.gson;

import net.jonathangiles.jsonwrapper.JsonDeserializationTests;
import org.junit.jupiter.api.BeforeEach;

public class GsonDeserializationTests extends JsonDeserializationTests {

    @BeforeEach
    public void createDeserializer() {
        jsonDeserializer = new GsonDeserializer();
    }
}
