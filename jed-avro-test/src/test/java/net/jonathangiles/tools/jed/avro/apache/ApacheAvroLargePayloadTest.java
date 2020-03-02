package net.jonathangiles.tools.jed.avro.apache;

import java.io.File;
import java.nio.file.Files;
import net.jonathangiles.tools.jed.avro.AvroLargePayloadTest;
import net.jonathangiles.tools.jed.avro.AvroWrapper;
import net.jonathangiles.tools.jed.avro.model.Student;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

public class ApacheAvroLargePayloadTest extends AvroLargePayloadTest {

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
      DatumWriter<Student> studentDatumWriter = new SpecificDatumWriter<>(Student.class);
      DataFileWriter<Student> studentDataFileWriter = new DataFileWriter<>(studentDatumWriter);
      File studentsAvroFile = Files.createTempFile("students", ".avro").toFile();
      studentDataFileWriter.create(Student.getClassSchema(), studentsAvroFile);
      for (int i = 0; i < numberOfRecords; i++) {
        Student student = new Student();
        student.setAge(i);
        student.setFirstName("John " + i);
        student.setLastName("Doe " + i);
        student.setSchoolName("Foo school " + i);
        studentDataFileWriter.append(student);
      }
      studentDataFileWriter.close();
      return studentsAvroFile;
    } catch (Exception ex) {

    }
    return null;
  }
}
