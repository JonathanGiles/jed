package net.jonathangiles.tools.jed.avro.api;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public interface AvroApi {

    Iterator<AvroRecord> read(byte[] content) throws IOException;

    <T> Iterator<T> read(byte[] content, Class<T> cls) throws IOException;

    Iterator<AvroRecord> read(File file) throws IOException;

    <T> Iterator<T> read(File file, Class<T> cls) throws IOException;

    Iterator<AvroRecord> read(AvroSchema schema, byte[] content) throws IOException;

    Iterator<AvroRecord> read(AvroSchema schema, File file) throws IOException;

}
