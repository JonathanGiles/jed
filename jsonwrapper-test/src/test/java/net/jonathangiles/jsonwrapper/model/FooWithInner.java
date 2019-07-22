package net.jonathangiles.jsonwrapper.model;

public class FooWithInner {
    public int intValue;
    public String stringValue;
    public InnerFoo innerFoo;
 
    public class InnerFoo {
        public String name;
    }
}