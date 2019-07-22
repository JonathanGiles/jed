package net.jonathangiles.jsonwrapper.gson;

import net.jonathangiles.jsonwrapper.api.JsonApi;
import net.jonathangiles.jsonwrapper.spi.JsonPlugin;

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

