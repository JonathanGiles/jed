package net.jonathangiles.tools.jed.avro;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.jonathangiles.tools.jed.avro.api.AvroApi;
import net.jonathangiles.tools.jed.avro.api.AvroRecord;
import net.jonathangiles.tools.jed.avro.model.Message;
import net.jonathangiles.tools.jed.avro.model.SourceType;
import org.apache.avro.generic.GenericData;
import org.apache.avro.util.Utf8;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for testing avro serialization and deserialization of all datatypes.
 */
public abstract class AvroAllTypesTest {
  protected AvroApi avroApi;
  protected File serializedFile;
  protected ByteBuffer data;

  @Test
  public void genericDeserializeAllTypesTest() throws IOException {
    File inputFile = serializedFile;
    Iterator<AvroRecord> it = avroApi.read(inputFile);
    AvroRecord record = it.next();
    assertTrue(Boolean.parseBoolean(record.get("isEmpty").toString()));
    assertEquals(25, Integer.parseInt(record.get("sizeInBytes").toString()));
    assertEquals(1L, Long.parseLong(record.get("sequenceNumber").toString()));
    assertEquals(0.50, Float.parseFloat(record.get("cost").toString()));
    assertEquals(0.3945, Double.parseDouble(record.get("compressionRatio").toString()));
    ByteBuffer deserializedData = (ByteBuffer)record.get("data");
    assertEquals(100, deserializedData.remaining());
    assertEquals(data.rewind(), deserializedData.rewind());
    assertEquals("uuid", record.get("messageId").toString());
    assertEquals(SourceType.EVENTHUBS, SourceType.valueOf(record.get("source").toString()));

    // TODO: Use non-apache types instead of GenericData.Array
    GenericData.Array groups = (GenericData.Array) record.get("groups");
    assertEquals(2, groups.size());
    assertEquals("test", groups.get(0).toString());
    assertEquals("list", groups.get(1).toString());

    // TODO: Use non-apache types instead of Utf8
    Map<Utf8, Utf8> properties = ((HashMap) record.get("properties"));
    assertEquals(1, properties.size());
    assertEquals("value", properties.get(new Utf8("key")).toString());
    assertTrue(System.currentTimeMillis() > (long) record.get("creationTime"));
    assertEquals(60, Long.parseLong(record.get("timeToLiveInSeconds").toString()));
    assertFalse(it.hasNext());
  }

  @Test
  public void customDeserializeAllTypesTest() throws IOException {
    File inputFile = serializedFile;
    Iterator<Message> it = avroApi.read(inputFile, Message.class);
    Message message = it.next();
    assertTrue(message.getIsEmpty());
    assertEquals(25, message.getSizeInBytes());
    assertEquals(1L, message.getSequenceNumber());
    assertEquals(0.50, message.getCost());
    assertEquals(0.3945, message.getCompressionRatio());
    assertEquals(100, message.getData().remaining());
    assertEquals(data.rewind(), message.getData().rewind());
    assertEquals("uuid", message.getMessageId().toString());
    assertEquals(SourceType.EVENTHUBS, message.getSource());
    assertEquals(2, message.getGroups().size());
    assertEquals("test", message.getGroups().get(0).toString());
    assertEquals("list", message.getGroups().get(1).toString());
    assertEquals(1, message.getProperties().size());
    // Map.get(charsequence) doesn't work as the type in map
    // is Utf8 (a special type in Apache Avro)
    assertTrue(message.getProperties().entrySet().stream()
        .allMatch(entry -> entry.getKey().toString().equals("key")
                        && entry.getValue().toString().equals("value")));
    assertTrue(message.getCreationTime().isBefore(Instant.now()));
    assertEquals(60, message.getTimeToLiveInSeconds());
    assertFalse(it.hasNext());
  }
}
