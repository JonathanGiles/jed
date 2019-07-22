package net.jonathangiles.jsonwrapper.api;

public abstract class CustomJsonDeserializer<T> {
    private final Class<T> rawType;

    public CustomJsonDeserializer(Class<T> rawType) {
        this.rawType = rawType;
    }

    public Class<T> getRawType() {
        return rawType;
    }

    public abstract T deserialize(Node node);
}
