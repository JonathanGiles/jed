package net.jonathangiles.jsonwrapper.jackson;

import com.fasterxml.jackson.databind.JsonNode;
import net.jonathangiles.jsonwrapper.api.Node;

public class JacksonNode implements Node {
    private final JsonNode node;

    public JacksonNode(JsonNode node) {
        this.node = node;
    }

    @Override
    public Node get(String name) {
        return new JacksonNode(node.get(name));
    }

    @Override
    public String asString() {
        return node.asText();
    }

    @Override
    public int asInt() {
        return node.asInt();
    }

    @Override
    public double asDouble() {
        return node.asDouble();
    }

    @Override
    public boolean asBoolean() {
        return node.asBoolean();
    }

    @Override
    public boolean isJsonArray() {
        return node.isArray();
    }

    @Override
    public boolean isJsonObject() {
        return node.isObject();
    }

    @Override
    public boolean isJsonPrimitive() {
        return node.isBoolean() || node.isDouble() || node.isFloat() || node.isInt() || node.isLong() || node.isShort();
    }
}
