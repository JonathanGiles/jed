package net.jonathangiles.tools.jed.json.model;

public class FooWithInner {
    public int intValue;
    public String stringValue;
    public InnerFoo innerFoo;
 
    public class InnerFoo {
        public String name;
    }
}