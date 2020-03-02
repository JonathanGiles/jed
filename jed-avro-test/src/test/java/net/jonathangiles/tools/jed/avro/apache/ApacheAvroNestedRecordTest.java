package net.jonathangiles.tools.jed.avro.apache;

import java.io.File;
import java.nio.file.Files;
import net.jonathangiles.tools.jed.avro.AvroNestedRecordTest;
import net.jonathangiles.tools.jed.avro.AvroWrapper;
import net.jonathangiles.tools.jed.avro.model.Employee;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

public class ApacheAvroNestedRecordTest extends AvroNestedRecordTest {

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
      DatumWriter<Employee> employeeDatumWriter = new SpecificDatumWriter<>(Employee.class);
      DataFileWriter<Employee> employeeDataFileWriter = new DataFileWriter<>(employeeDatumWriter);
      File employeeAvroFile = Files.createTempFile("nested", ".avro").toFile();
      employeeDataFileWriter.create(Employee.getClassSchema(), employeeAvroFile);
      Employee prevEmployee = new Employee();
      prevEmployee.setAge(0);
      prevEmployee.setFirstName("John " + 0);
      prevEmployee.setLastName("Doe " + 0);

      for (int i = 1; i < 2; i++) {
        Employee employee = new Employee();
        employee.setAge(i);
        employee.setFirstName("John " + i);
        employee.setLastName("Doe " + i);
        employee.setManager(prevEmployee);
        prevEmployee = employee;
      }
      employeeDataFileWriter.append(prevEmployee);
      employeeDataFileWriter.close();
      return employeeAvroFile;
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return null;
  }
}
