package net.jonathangiles.jsonwrapper;

import net.jonathangiles.jsonwrapper.api.JsonApi;
import net.jonathangiles.jsonwrapper.spi.JsonPlugin;

import java.util.Iterator;
import java.util.ServiceLoader;

public class JsonWrapper {

    private static ServiceLoader<JsonPlugin> pluginLoader = ServiceLoader.load(JsonPlugin.class);

    public static JsonApi newInstance() {
        return newInstance((Class) null);
    }

    public static JsonApi newInstance(String type) {
        try {
            Class<? extends JsonApi> cls = (Class<? extends JsonApi>) Class.forName(type);
            return newInstance(cls);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JsonApi newInstance(Class<? extends JsonApi> type) {
        Iterator<JsonPlugin> it = pluginLoader.iterator();
        while (it.hasNext()) {
            JsonPlugin plugin = it.next();
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
