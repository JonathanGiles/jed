package net.jonathangiles.jsonwrapper.jackson;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import net.jonathangiles.jsonwrapper.api.Config;
import net.jonathangiles.jsonwrapper.api.Deserializer;
import net.jonathangiles.jsonwrapper.api.JsonApi;
import net.jonathangiles.jsonwrapper.api.Type;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JacksonDeserializer implements JsonApi {

    private static final Map<Config, DeserializationFeature> configMap;
    static {
        configMap = new HashMap<>();

        // TODO fill out full list of supported config properties
        configMap.put(Config.FAIL_ON_NULL_FOR_PRIMITIVES, DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES);
        configMap.put(Config.FAIL_ON_NUMBERS_FOR_ENUM, DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS);
        configMap.put(Config.FAIL_ON_UNKNOWN_PROPERTIES, DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    private ObjectMapper objectMapper = new ObjectMapper();
    private TypeFactory typeFactory = objectMapper.getTypeFactory();

    @Override
    public void configure(Config key, boolean value) {
        DeserializationFeature feature = configMap.get(key);
        if (feature == null) {
            // TODO handle
            return;
        }
        this.objectMapper = objectMapper.configure(feature, value);
    }

    @Override
    public <T> void registerCustomDeserializer(final Deserializer<T> deserializer) {
        SimpleModule module = new SimpleModule("deserializer", new Version(1, 0, 0, null, null, null));
        module.addDeserializer(deserializer.getRawType(), new com.fasterxml.jackson.databind.JsonDeserializer<T>() {
            @Override
            public T deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
                ObjectCodec codec = parser.getCodec();
                JsonNode node = codec.readTree(parser);
                return deserializer.deserialize(new JacksonNode(node));
            }
        });
        objectMapper.registerModule(module);
    }

    public <T> T readString(final String json, final Class<? extends T> cls) {
        try {
            return objectMapper.readValue(json, cls);
        } catch (JsonParseException | JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public <T> T readString(String json, Type<T> type) {
        assert type.isListType();

        try {
            return objectMapper.readValue(json, typeFactory.constructType(type.getJavaType()));
        } catch (JsonParseException | JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public <T> List<T> readStringToList(String json, Type<List<T>> type) {
        assert type.isListType();

        try {
            return objectMapper.readValue(json, listTypeReference(type));
        } catch (JsonParseException | JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private <T> CollectionType listTypeReference(Type<T> type) {
        return typeFactory.constructCollectionType(List.class, typeFactory.constructType(type.getListType()));
    }
}
