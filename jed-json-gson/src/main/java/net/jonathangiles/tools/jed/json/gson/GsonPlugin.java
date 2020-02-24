package net.jonathangiles.tools.jed.json.gson;

import net.jonathangiles.tools.jed.json.api.JsonApi;
import net.jonathangiles.tools.jed.json.spi.JsonPlugin;

public class GsonPlugin implements JsonPlugin {

    @Override
    public Class<? extends JsonApi> getType() {
        return GsonDeserializer.class;
    }

    @Override
    public JsonApi newInstance() {
        return new GsonDeserializer();
    }
}

