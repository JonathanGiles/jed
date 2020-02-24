package net.jonathangiles.tools.jed.json.spi;

import net.jonathangiles.tools.jed.json.api.JsonApi;

/**
 * Interface to be implemented by all JSON libraries that want to provide their services to the JSON Wrapper abstraction.
 */
public interface JsonPlugin {

    /**
     * The class type of the JSON Wrapper implementation
     */
    Class<? extends JsonApi> getType();

    /**
     * Returns a new instance of the {@link JsonApi} implementation.
     */
    JsonApi newInstance();
}
