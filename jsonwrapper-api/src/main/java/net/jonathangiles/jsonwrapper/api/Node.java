package net.jonathangiles.jsonwrapper.api;

public interface Node {

    Node get(String name);

    String asString();

    int asInt();

    double asDouble();

    boolean asBoolean();

    boolean isJsonArray();

    boolean isJsonObject();

    boolean isJsonPrimitive();
}
