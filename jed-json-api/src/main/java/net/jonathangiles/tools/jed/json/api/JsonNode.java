package net.jonathangiles.tools.jed.json.api;

public interface JsonNode {

    JsonNode get(String name);

    String asString();

    int asInt();

    double asDouble();

    boolean asBoolean();

    boolean isJsonArray();

    boolean isJsonObject();

    boolean isJsonPrimitive();
}
