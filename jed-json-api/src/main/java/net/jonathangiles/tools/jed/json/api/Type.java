package net.jonathangiles.tools.jed.json.api;

import java.lang.reflect.ParameterizedType;

public abstract class Type<T> {
    private final java.lang.reflect.Type javaType;

    public Type() {
        java.lang.reflect.Type superClass = this.getClass().getGenericSuperclass();
        if (superClass instanceof Class) {
            throw new IllegalArgumentException("Internal error: TypeReference constructed without actual type information");
        } else {
            this.javaType = ((ParameterizedType)superClass).getActualTypeArguments()[0];
        }
    }

    public java.lang.reflect.Type getJavaType() {
        return javaType;
    }

    public boolean isListType() {
        if (! (javaType instanceof ParameterizedType)) return false;

        return true;
    }

    public java.lang.reflect.Type getListType() {
        assert isListType();
        ParameterizedType parameterizedType = (ParameterizedType)javaType;
        return parameterizedType.getActualTypeArguments()[0];
    }
}
