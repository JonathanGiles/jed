package net.jonathangiles.jsonwrapper;

import net.jonathangiles.jsonwrapper.api.Config;
import net.jonathangiles.jsonwrapper.api.CustomJsonDeserializer;
import net.jonathangiles.jsonwrapper.api.JsonDeserializer;
import net.jonathangiles.jsonwrapper.api.Node;
import net.jonathangiles.jsonwrapper.api.Type;
import net.jonathangiles.jsonwrapper.model.Car;
import net.jonathangiles.jsonwrapper.model.Foo;
import net.jonathangiles.jsonwrapper.model.FooWithInner;
import net.jonathangiles.jsonwrapper.model.GenericFoo;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 *
 * Some tests from the following websites:
 * - https://www.baeldung.com/gson-deserialization-guide
 */
public abstract class JsonDeserializationTests {

    protected JsonDeserializer jsonDeserializer;

    @Test
    public void deserializeString() throws Exception {
        String json = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";
        Car car = jsonDeserializer.readString(json, Car.class);
        assertNotNull(car);
        assertEquals("Black", car.getColor());
        assertEquals("BMW", car.getType());
    }

    @Test
    public void deserializeListString() throws Exception {
        String json = "[{ \"color\" : \"Black\", \"type\" : \"BMW\" }, { \"color\" : \"Red\", \"type\" : \"FIAT\" }]";
        List<Car> cars = jsonDeserializer.readStringToList(json, new Type<List<Car>>() { });
        assertNotNull(cars);
        assertEquals(2, cars.size());
        assertEquals("Black", cars.get(0).getColor());
        assertEquals("BMW", cars.get(0).getType());
        assertEquals("Red", cars.get(1).getColor());
        assertEquals("FIAT", cars.get(1).getType());
    }

    @Test
    public void whenDeserializingToSimpleObject_thenCorrect() throws Exception {
        String json = "{\"intValue\":1,\"stringValue\":\"one\"}";

        Foo targetObject = jsonDeserializer.readString(json, Foo.class);

        assertEquals(targetObject.getIntValue(), 1);
        assertEquals(targetObject.getStringValue(), "one");
    }

    @Test
    public void whenDeserializingToGenericObject_thenCorrect() throws Exception {
        String json = "{\"theValue\":1}";

        GenericFoo<Integer> targetObject = jsonDeserializer.readString(json, new Type<GenericFoo<Integer>>() { });

        assertEquals(targetObject.theValue, new Integer(1));
    }

    @Test
    public void givenJsonHasExtraValues_whenDeserializing_thenCorrect() {
        jsonDeserializer.configure(Config.FAIL_ON_UNKNOWN_PROPERTIES, false);

        String json = "{\"intValue\":1,\"stringValue\":\"one\",\"extraString\":\"two\",\"extraFloat\":2.2}";
        Foo targetObject = jsonDeserializer.readString(json, Foo.class);

        assertEquals(targetObject.getIntValue(), 1);
        assertEquals(targetObject.getStringValue(), "one");
    }

    @Test
    public void givenJsonHasNonMatchingFields_whenDeserializingWithCustomDeserializer_thenCorrect() {
        String json = "{\"valueInt\":7,\"valueString\":\"seven\"}";

        jsonDeserializer.registerCustomDeserializer(new CustomJsonDeserializer<Foo>(Foo.class) {
            @Override public Foo deserialize(Node node) {
                int intValue = node.get("valueInt").asInt();
                String stringValue = node.get("valueString").asString();
                return new Foo(intValue, stringValue);
            }
        });
        Foo targetObject = jsonDeserializer.readString(json, Foo.class);

        assertEquals(targetObject.getIntValue(), 7);
        assertEquals(targetObject.getStringValue(), "seven");
    }

    @Test
    public void customDeserializeCar() {
        String json = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";

        jsonDeserializer.registerCustomDeserializer(new CustomJsonDeserializer<Car>(Car.class) {
            @Override public Car deserialize(Node node) {
                Car car = new Car();
                Node colorNode = node.get("color");
                String color = colorNode.asString();
                car.setColor(color);
                return car;
            }
        });

        Car car = jsonDeserializer.readString(json, Car.class);
        assertEquals(car.getColor(), "Black");
        assertNull(car.getType());
    }

    @Test
    public void givenJsonArrayOfFoos_whenDeserializingToArray_thenCorrect() {
        String json = "[{\"intValue\":1,\"stringValue\":\"one\"}," +
                        "{\"intValue\":2,\"stringValue\":\"two\"}]";
        Foo[] targetArray = jsonDeserializer.readString(json, Foo[].class);

        assertEquals(2, targetArray.length);
        assertEquals(new Foo(1, "one"), targetArray[0]);
        assertEquals(new Foo(2, "two"), targetArray[1]);
    }


    @Test
    public void givenJsonArrayOfFoos_whenDeserializingCollection_thenCorrect() {
        String json =
                "[{\"intValue\":1,\"stringValue\":\"one\"},{\"intValue\":2,\"stringValue\":\"two\"}]";
        Type targetClassType = new Type<List<Foo>>() { };

        List<Foo> targetList = jsonDeserializer.readStringToList(json, targetClassType);
        assertEquals(2, targetList.size());
        assertEquals(new Foo(1, "one"), targetList.get(0));
        assertEquals(new Foo(2, "two"), targetList.get(1));
    }

    @Test
    public void whenDeserializingToNestedObjects_thenCorrect() throws Exception {
        String json = "{\"intValue\":1,\"stringValue\":\"one\",\"innerFoo\":{\"name\":\"inner\"}}";

        FooWithInner targetObject = jsonDeserializer.readString(json, FooWithInner.class);

        assertEquals(targetObject.intValue, 1);
        assertEquals(targetObject.stringValue, "one");
        assertEquals(targetObject.innerFoo.name, "inner");
    }
}