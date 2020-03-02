package net.jonathangiles.tools.jed.avro;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import net.jonathangiles.tools.jed.avro.api.AvroApi;
import net.jonathangiles.tools.jed.avro.api.AvroRecord;
import net.jonathangiles.tools.jed.avro.model.Employee;
import org.apache.avro.generic.GenericData.Record;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 *  Unit tests for testing avro serialization and deserialization of a custom record type containing another
 *  custom record type.
 */
public abstract class AvroNestedRecordTest {
  protected AvroApi avroApi;
  protected File serializedFile;

  /**
   * Disabled as the nested record is of apache type instead of AvroRecord type.
   * @throws IOException
   */
  @Disabled
  @Test
  public void genericDeserializeNestedRecord() throws IOException {
    File inputFile = serializedFile;
    Iterator<AvroRecord> it = avroApi.read(inputFile);
    int count = 2;
    AvroRecord record = it.next();
    while (count >= 0) {
      assertEquals("John " + count, record.get("firstName").toString());
      assertEquals("Doe " + count, record.get("lastName").toString());
      assertEquals(count, record.get("age"));
      count--;
      // TODO: This should also be AvroRecord type
      Record manager = (Record) record.get("manager");
    }
    assertFalse(it.hasNext());
  }

  @Test
  public void customDeserializeNestedRecord() throws IOException {
    File inputFile = serializedFile;
    Iterator<Employee> it = avroApi.read(inputFile, Employee.class);
    Employee user = it.next();
    assertEquals("John " + 1, user.getFirstName().toString());
    assertEquals("Doe " + 1, user.getLastName().toString());
    assertEquals(1, user.getAge());

    Employee manager = user.getManager();
    assertEquals("John " + 0, manager.getFirstName().toString());
    assertEquals("Doe " + 0, manager.getLastName().toString());
    assertEquals(0, manager.getAge());
    assertFalse(it.hasNext());
  }

}
