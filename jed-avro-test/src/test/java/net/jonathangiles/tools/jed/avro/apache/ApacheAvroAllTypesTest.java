package net.jonathangiles.tools.jed.avro.apache;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import net.jonathangiles.tools.jed.avro.AvroAllTypesTest;
import net.jonathangiles.tools.jed.avro.AvroWrapper;
import net.jonathangiles.tools.jed.avro.model.Message;
import net.jonathangiles.tools.jed.avro.model.SourceType;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

public class ApacheAvroAllTypesTest extends AvroAllTypesTest {

  @BeforeEach
  public void createDeserializer() {
    avroApi = AvroWrapper.newInstance("net.jonathangiles.tools.jed.avro.apache.ApacheAvroDeserializer");
    Assertions.assertNotNull(avroApi, "Couldn't create instance of deserializer");
    if (serializedFile == null) {
      serializedFile = getSerializedAvroFile();
    }
  }

  private File getSerializedAvroFile() {
    try {
      DatumWriter<Message> messageDatumWriter = new SpecificDatumWriter<>(Message.class);
      DataFileWriter<Message> messageDataFileWriter = new DataFileWriter<>(messageDatumWriter);
      File messageAvroFile = Files.createTempFile("message", ".avro").toFile();
      messageDataFileWriter.create(Message.getClassSchema(), messageAvroFile);
      Random random = new Random();
      byte[] dataBytes = new byte[100];
      random.nextBytes(dataBytes);
      data = ByteBuffer.wrap(dataBytes);
      Map<CharSequence, CharSequence> properties = new HashMap<>();
      properties.put("key", "value");
      Message message = Message.newBuilder()
          .setIsEmpty(true)
          .setCompressionRatio(0.3945)
          .setCost(0.50f)
          .setCreationTime(Instant.now())
          .setSequenceNumber(1L)
          .setSizeInBytes(25)
          .setSource(SourceType.EVENTHUBS)
          .setTimeToLiveInSeconds(60)
          .setData(data)
          .setGroups(Arrays.asList("test", "list"))
          .setMessageId("uuid")
          .setProperties(properties)
          .build();
      messageDataFileWriter.append(message);
      messageDataFileWriter.close();
      return messageAvroFile;
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return null;
  }

}
