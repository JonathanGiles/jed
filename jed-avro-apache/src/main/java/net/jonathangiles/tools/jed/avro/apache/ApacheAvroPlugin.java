package net.jonathangiles.tools.jed.avro.apache;

import net.jonathangiles.tools.jed.avro.api.AvroApi;
import net.jonathangiles.tools.jed.avro.spi.AvroPlugin;

public class ApacheAvroPlugin implements AvroPlugin {

    @Override
    public Class<? extends AvroApi> getType() {
        return ApacheAvroDeserializer.class;
    }

    @Override
    public AvroApi newInstance() {
        return new ApacheAvroDeserializer();
    }
}

