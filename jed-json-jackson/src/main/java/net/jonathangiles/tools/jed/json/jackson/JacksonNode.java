package net.jonathangiles.tools.jed.json.jackson;

import net.jonathangiles.tools.jed.json.api.JsonNode;

public class JacksonNode implements JsonNode {
    private final com.fasterxml.jackson.databind.JsonNode node;

    public JacksonNode(com.fasterxml.jackson.databind.JsonNode node) {
        this.node = node;
    }

    @Override
    public JsonNode get(String name) {
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
