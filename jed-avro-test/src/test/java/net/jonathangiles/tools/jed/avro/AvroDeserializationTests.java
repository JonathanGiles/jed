package net.jonathangiles.tools.jed.avro;

import net.jonathangiles.tools.jed.avro.api.AvroApi;
import net.jonathangiles.tools.jed.avro.api.AvroRecord;
import net.jonathangiles.tools.jed.avro.model.User;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public abstract class AvroDeserializationTests {

    protected AvroApi avroApi;

    @Test
    public void genericDeserializeUser() throws IOException {
        File inputFile = new File(AvroDeserializationTests.class.getClassLoader().getResource("users.avro").getFile());

        Iterator<AvroRecord> it = avroApi.read(inputFile);
        int count = 0;
        while (it.hasNext()) {
            AvroRecord user = it.next();

            switch (count) {
                case 0: {
                    assertEquals("Bob", user.get("firstName").toString());
                    assertEquals("Downs", user.get("lastName").toString());
                    assertEquals(39, user.get("age"));
                    break;
                }
                case 1: {
                    assertEquals("Jimmy", user.get("firstName").toString());
                    assertEquals("Swift", user.get("lastName").toString());
                    assertEquals(11, user.get("age"));
                    break;
                }
                case 2: {
                    assertEquals("Rebecca", user.get("firstName").toString());
                    assertEquals("Michaels", user.get("lastName").toString());
                    assertEquals(51, user.get("age"));
                    break;
                }
                default: fail("There should be three results only!");
            }
            count++;
        }
    }

    @Test
    public void customDeserializeUser() throws IOException {
        File inputFile = new File(AvroDeserializationTests.class.getClassLoader().getResource("users.avro").getFile());

        Iterator<User> it = avroApi.read(inputFile, User.class);
        int count = 0;
        while (it.hasNext()) {
            User user = it.next();

            switch (count) {
                case 0: {
                    assertEquals("Bob", user.getFirstName().toString());
                    assertEquals("Downs", user.getLastName().toString());
                    assertEquals(39, user.getAge());
                    break;
                }
                case 1: {
                    assertEquals("Jimmy", user.getFirstName().toString());
                    assertEquals("Swift", user.getLastName().toString());
                    assertEquals(11, user.getAge());
                    break;
                }
                case 2: {
                    assertEquals("Rebecca", user.getFirstName().toString());
                    assertEquals("Michaels", user.getLastName().toString());
                    assertEquals(51, user.getAge());
                    break;
                }
                default: fail("There should be three results only!");
            }
            count++;
        }
    }
}
