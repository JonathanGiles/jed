package net.jonathangiles.tools.jed.avro;

import net.jonathangiles.tools.jed.avro.api.AvroApi;
import net.jonathangiles.tools.jed.avro.spi.AvroPlugin;

import java.util.Iterator;
import java.util.ServiceLoader;

public class AvroWrapper {

    private static ServiceLoader<AvroPlugin> pluginLoader = ServiceLoader.load(AvroPlugin.class);

    public static AvroApi newInstance() {
        return newInstance((Class) null);
    }

    public static AvroApi newInstance(String type) {
        try {
            Class<? extends AvroApi> cls = (Class<? extends AvroApi>) Class.forName(type);
            return newInstance(cls);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static AvroApi newInstance(Class<? extends AvroApi> type) {
        Iterator<AvroPlugin> it = pluginLoader.iterator();
        while (it.hasNext()) {
            AvroPlugin plugin = it.next();
            if (type == null) {
                return plugin.newInstance();
            }
            if (plugin.getType().equals(type)) {
                return plugin.newInstance();
            }
        }
        return null;
    }
}
