package net.jonathangiles.tools.jed.avro.apache;

import net.jonathangiles.tools.jed.avro.api.AvroRecord;
import org.apache.avro.generic.GenericRecord;

public class ApacheAvroRecord implements AvroRecord {
    private final GenericRecord record;

    public ApacheAvroRecord(final GenericRecord record) {
        this.record = record;
    }

    @Override
    public Object get(final String key) {
        return record.get(key);
    }

    @Override
    public Object get(int i) {
        return record.get(i);
    }

    @Override
    public String toString() {
        return record.toString();
    }
}
