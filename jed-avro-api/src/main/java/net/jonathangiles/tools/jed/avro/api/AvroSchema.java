package net.jonathangiles.tools.jed.avro.api;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class AvroSchema {
    private final InputStream avroSchema;

    public AvroSchema(InputStream inputStream) {
        this.avroSchema = inputStream;
    }

    public AvroSchema(byte[] bytes) {
        this.avroSchema = new ByteArrayInputStream(bytes);
    }

    public AvroSchema(File file) throws IOException {
        this.avroSchema = new FileInputStream(file);
    }

    public AvroSchema(String string) {
        this(string.getBytes());
    }

    public InputStream getAvroSchema() {
        return avroSchema;
    }
}
