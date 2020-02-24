package net.jonathangiles.tools.jed.avro.spi;

import net.jonathangiles.tools.jed.avro.api.AvroApi;

/**
 * Interface to be implemented by all AVRO libraries that want to provide their services to the Avro Wrapper abstraction.
 */
public interface AvroPlugin {

    /**
     * The class type of the JSON Wrapper implementation
     */
    Class<? extends AvroApi> getType();

    /**
     * Returns a new instance of the {@link AvroApi} implementation.
     */
    AvroApi newInstance();
}
