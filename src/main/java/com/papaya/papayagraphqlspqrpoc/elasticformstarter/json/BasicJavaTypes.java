package com.papaya.papayagraphqlspqrpoc.elasticformstarter.json;

public enum BasicJavaTypes {
    BYTE_TYPE(byte.class),
    SHORT_TYPE(short.class),
    INT_TYPE(int.class),
    LONG_TYPE(long.class),
    FLOAT_TYPE(float.class),
    DOUBLE_TYPE(double.class),
    CHAR_TYPE(char.class),
    BOOLEAN_TYPE(boolean.class),
    STRING_TYPE(String.class),
    OBJECT_TYPE(Object.class);

    private final Class<?> type;

    BasicJavaTypes(Class<?> type) {
        this.type = type;
    }

    public Class<?> getValue() {
        return type;
    }
}
