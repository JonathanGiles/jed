package net.jonathangiles.tools.jed.avro.api;

public interface AvroRecord {
    Object get(String key);
    Object get(int i);
}
