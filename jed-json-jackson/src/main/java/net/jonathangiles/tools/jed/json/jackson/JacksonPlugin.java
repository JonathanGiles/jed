package net.jonathangiles.tools.jed.json.jackson;

import net.jonathangiles.tools.jed.json.api.JsonApi;
import net.jonathangiles.tools.jed.json.spi.JsonPlugin;

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

