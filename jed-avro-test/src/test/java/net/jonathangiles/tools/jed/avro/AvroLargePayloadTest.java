package net.jonathangiles.tools.jed.avro;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import net.jonathangiles.tools.jed.avro.api.AvroApi;
import net.jonathangiles.tools.jed.avro.api.AvroRecord;
import net.jonathangiles.tools.jed.avro.model.Student;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for testing avro serialization and deserialization of a large payload containing an array of records.
 */
public abstract class AvroLargePayloadTest {

  protected long numberOfRecords = 100000000;
  protected AvroApi avroApi;
  protected File serializedFile;

  @Test
  public void genericDeserializeLargePayload() throws IOException {
    File inputFile = serializedFile;
    Iterator<AvroRecord> it = avroApi.read(inputFile);
    int count = 0;
    while (it.hasNext() && count < numberOfRecords) {
      AvroRecord user = it.next();
      assertEquals("John " + count, user.get("firstName").toString());
      assertEquals("Doe " + count, user.get("lastName").toString());
      assertEquals("Foo school " + count, user.get("schoolName").toString());
      assertEquals(count, user.get("age"));
      count++;
    }
    assertFalse(it.hasNext());
  }

  @Test
  public void customDeserializeLargePayload() throws IOException {
    File inputFile = serializedFile;
    Iterator<Student> it = avroApi.read(inputFile, Student.class);
    int count = 0;
    while (it.hasNext() && count < 100000000) {
      Student user = it.next();
      assertEquals("John " + count, user.getFirstName().toString());
      assertEquals("Doe " + count, user.getLastName().toString());
      assertEquals("Foo school " + count, user.getSchoolName().toString());
      assertEquals(count, user.getAge());
      count++;
    }
    assertFalse(it.hasNext());
  }

}
