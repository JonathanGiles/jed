package net.jonathangiles.jsonwrapper.jackson;

import net.jonathangiles.jsonwrapper.api.JsonApi;
import net.jonathangiles.jsonwrapper.spi.JsonPlugin;

public class JacksonPlugin implements JsonPlugin {

    @Override
    public Class<? extends JsonApi> getType() {
        return JacksonDeserializer.class;
    }

    @Override
    public JsonApi newInstance() {
        return new JacksonDeserializer();
    }
}

