package net.jonathangiles.jsonwrapper.gson;

import com.google.gson.JsonElement;
import net.jonathangiles.jsonwrapper.api.Node;

public class GsonNode implements Node {
    private final JsonElement node;

    public GsonNode(JsonElement node) {
        this.node = node;
    }

    @Override
    public Node get(String name) {
        assert isJsonObject();
        return new GsonNode(node.getAsJsonObject().get(name));
    }

    @Override
    public String asString() {
        return node.getAsString();
    }

    @Override
    public int asInt() {
        return node.getAsInt();
    }

    @Override
    public double asDouble() {
        return node.getAsDouble();
    }

    @Override
    public boolean asBoolean() {
        return node.getAsBoolean();
    }

    @Override
    public boolean isJsonArray() {
        return node.isJsonArray();
    }

    @Override
    public boolean isJsonObject() {
        return node.isJsonObject();
    }

    @Override
    public boolean isJsonPrimitive() {
        return node.isJsonPrimitive();
    }
}
