package net.jonathangiles.tools.jed.avro.apache;

import net.jonathangiles.tools.jed.avro.AvroDeserializationTests;
import net.jonathangiles.tools.jed.avro.AvroWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

public class ApacheAvroDeserializatoinTests extends AvroDeserializationTests {

    @BeforeEach
    public void createDeserializer() {
        avroApi = AvroWrapper.newInstance("net.jonathangiles.tools.jed.avro.apache.ApacheAvroDeserializer");
        Assertions.assertNotNull(avroApi, "Couldn't create instance of deserializer");
    }

}
