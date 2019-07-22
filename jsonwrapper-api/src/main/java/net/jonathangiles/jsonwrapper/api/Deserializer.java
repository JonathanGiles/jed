package net.jonathangiles.jsonwrapper.api;

/**
 * A custom deserialization API for JSON content that will return a specific Java type. A Deserializer is registered on
 * a specific instance of a {@link JsonApi} after it is retrieved from the {@link net.jonathangiles.jsonwrapper.JsonWrapper}
 * root class.
 *
 * For example, here is a custom deserializer that converts the JSON string {@code {"valueInt":7,"valueString":"seven"}}
 * into an instance of 'Foo':
 *
 * <pre>
 * jsonDeserializer.registerCustomDeserializer(new Deserializer&lt;Foo&gt;(Foo.class) {
 *     {@literal @}Override public Foo deserialize(Node node) {
 *         int intValue = node.get("valueInt").asInt();
 *         String stringValue = node.get("valueString").asString();
 *         return new Foo(intValue, stringValue);
 *     }
 * });
 * Foo targetObject = jsonDeserializer.readString(json, Foo.class);
 * </pre>
 *
 * @param <T> The type of the element that should be return by the {@link #deserialize(Node)} method following a
 * successful deserialization.
 */
public abstract class Deserializer<T> {
    private final Class<T> rawType;

    protected Deserializer(Class<T> rawType) {
        this.rawType = rawType;
    }

    public Class<T> getRawType() {
        return rawType;
    }

    public abstract T deserialize(Node node);
}
