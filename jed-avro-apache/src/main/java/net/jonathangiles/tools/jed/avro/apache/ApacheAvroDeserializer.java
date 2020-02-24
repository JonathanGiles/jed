package net.jonathangiles.tools.jed.avro.apache;

import net.jonathangiles.tools.jed.avro.api.AvroApi;
import net.jonathangiles.tools.jed.avro.api.AvroRecord;
import net.jonathangiles.tools.jed.avro.api.AvroSchema;
import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.FileReader;
import org.apache.avro.file.SeekableByteArrayInput;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.specific.SpecificDatumReader;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

public class ApacheAvroDeserializer implements AvroApi {

    @Override
    public Iterator<AvroRecord> read(byte[] content) throws IOException {
        return read(null, content);
    }

    @Override
    public Iterator<AvroRecord> read(File file) throws IOException {
        return read(null, file);
    }

    @Override
    public Iterator<AvroRecord> read(final AvroSchema schema, final byte[] content) throws IOException {
        FileReader<GenericRecord> fileReader = null;

        try {
            fileReader = makeFileReader(schema, content);
            return mapIteratorTypes(fileReader);
        } finally {
            close(fileReader);
        }
    }

    @Override
    public Iterator<AvroRecord> read(final AvroSchema schema, final File file) throws IOException {
        FileReader<GenericRecord> fileReader = null;

        try {
            fileReader = makeFileReader(schema, file);
            return mapIteratorTypes(fileReader);
        } finally {
            close(fileReader);
        }
    }

    @Override
    public <T> Iterator<T> read(final File file, final Class<T> cls) throws IOException {
        FileReader<T> fileReader = null;

        try {
            fileReader = makeFileReader(file, cls);
            return fileReader.iterator();
        } finally {
            close(fileReader);
        }
    }

    @Override
    public <T> Iterator<T> read(byte[] content, Class<T> cls) throws IOException {
        FileReader<T> fileReader = null;

        try {
            fileReader = makeFileReader(content, cls);
            return fileReader.iterator();
        } finally {
            close(fileReader);
        }
    }

    private void close(FileReader<?> fileReader) throws IOException {
        // FIXME having this enabled results in 'org.apache.avro.AvroRuntimeException: java.nio.channels.ClosedChannelException' in the tests
//        if (fileReader != null) {
//            fileReader.close();
//        }
    }

    private FileReader<GenericRecord> makeFileReader(AvroSchema schema, byte[] content) throws IOException {
        DatumReader<GenericRecord> datumReader = new GenericDatumReader<>(toApacheAvroSchema(schema));
        return new DataFileReader<>(new SeekableByteArrayInput(content), datumReader);
    }

    private FileReader<GenericRecord> makeFileReader(AvroSchema schema, File file) throws IOException {
        final DatumReader<GenericRecord> datumReader = new GenericDatumReader<>(toApacheAvroSchema(schema));
        return new DataFileReader<>(file, datumReader);
    }

    private <T> FileReader<T> makeFileReader(byte[] content, Class<T> cls) throws IOException {
        final DatumReader<T> datumReader = new SpecificDatumReader<>(cls);
        return new DataFileReader<>(new SeekableByteArrayInput(content), datumReader);
    }

    private <T> FileReader<T> makeFileReader(File file, Class<T> cls) throws IOException {
        final DatumReader<T> datumReader = new SpecificDatumReader<>(cls);
        return new DataFileReader<>(file, datumReader);
    }

    private Iterator<AvroRecord> mapIteratorTypes(FileReader<GenericRecord> fileReader) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(fileReader.iterator(), Spliterator.ORDERED), false)
                       .map(record -> (AvroRecord) new ApacheAvroRecord(record))
                       .iterator();
    }

    private Schema toApacheAvroSchema(AvroSchema schema) throws IOException {
        return schema == null ? null : new Schema.Parser().parse(schema.getAvroSchema());
    }
}
