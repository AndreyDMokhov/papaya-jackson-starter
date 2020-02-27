package com.papaya.papayagraphqlspqrpoc.elasticformstarter.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.papaya.startertest.model.FieldValue;
import lombok.SneakyThrows;
import org.reflections.ReflectionUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

public class DynamicObjectSerializer<T> extends StdSerializer<T> {
    public DynamicObjectSerializer(Class<T> t) {
        super(t);
    }

    public DynamicObjectSerializer() {
        this(null);
    }

    @Override
    @SneakyThrows
    @SuppressWarnings("unchecked")
    public void serialize(T value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.getOutputContext().typeDesc();

        gen.writeStartObject();


        Set<Field> fields = ReflectionUtils.getAllFields(value.getClass());
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.get(value) != null && Map.class.isAssignableFrom(field.getType())) {
                writeDynamicValue((Map<String, FieldValue>) field.get(value), gen);
            } else if (field.get(value) != null) {
                writeSimpleValue(field, value, gen);
            }
        }
        gen.writeEndObject();
    }

    private void writeSimpleValue(Field field, Object value, JsonGenerator gen) throws IOException, IllegalAccessException {


        if (field.getType() == Boolean.TYPE || field.getType() == Boolean.class) {
            gen.writeBooleanField(field.getName(), field.getBoolean(value));
        } else if (field.getType() == Character.TYPE || field.getType() == Character.class) {
            gen.writeStringField(field.getName(), String.valueOf(field.getChar(value)));
        } else if (field.getType() == Byte.TYPE || field.getType() == Byte.class) {
            gen.writeNumberField(field.getName(), field.getByte(value));
        } else if (field.getType() == Short.TYPE || field.getType() == Short.class) {
            gen.writeNumberField(field.getName(), field.getShort(value));
        } else if (field.getType() == Integer.TYPE || field.getType() == Integer.class) {
            gen.writeNumberField(field.getName(), field.getInt(value));
        } else if (field.getType() == Long.TYPE || field.getType() == Long.class) {
            gen.writeNumberField(field.getName(), field.getLong(value));
        } else if (field.getType() == Float.TYPE || field.getType() == Float.class) {
            gen.writeNumberField(field.getName(), field.getFloat(value));
        } else if (field.getType() == Double.TYPE || field.getType() == Double.class) {
            gen.writeNumberField(field.getName(), field.getDouble(value));
        } else {
            gen.writeStringField(field.getName(), (String) field.get(value));
        }

    }

    private void writeDynamicValue(Map<String, FieldValue> nestedFields, JsonGenerator gen) throws IOException {
        for (Map.Entry<String, FieldValue> fieldValueMap : nestedFields.entrySet()) {
            FieldValue fieldValue = fieldValueMap.getValue();
            if (fieldValue.getValue() != null) {

                writeValueByType(fieldValue, gen);

            } else if (fieldValue.getNestedFields() != null) {
                gen.writeObjectFieldStart(fieldValue.getFieldTemplate().getLabel());
                writeDynamicValue(fieldValue.getNestedFields(), gen);
                gen.writeEndObject();
            }

        }
    }

    private void writeValueByType(FieldValue fieldValue, JsonGenerator gen) throws IOException {

        String type = fieldValue.getFieldTemplate().getType();

        if (type.equals(BasicJavaTypes.INT_TYPE.getValue().getName())) {
            gen.writeNumberField(fieldValue.getFieldTemplate().getLabel(), Integer.parseInt(fieldValue.getValue()));
        } else if (type.equals(BasicJavaTypes.LONG_TYPE.getValue().getName())) {
            gen.writeNumberField(fieldValue.getFieldTemplate().getLabel(), Long.parseLong(fieldValue.getValue()));
        } else if (type.equals(BasicJavaTypes.DOUBLE_TYPE.getValue().getName())) {
            gen.writeNumberField(fieldValue.getFieldTemplate().getLabel(), Double.parseDouble(fieldValue.getValue()));
        } else if (type.equals(BasicJavaTypes.BOOLEAN_TYPE.getValue().getName())) {
            gen.writeBooleanField(fieldValue.getFieldTemplate().getLabel(), Boolean.parseBoolean(fieldValue.getValue()));
        } else {
            gen.writeStringField(fieldValue.getFieldTemplate().getLabel(), fieldValue.getValue());
        }
    }
}
