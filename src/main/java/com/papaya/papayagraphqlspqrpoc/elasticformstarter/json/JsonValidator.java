package com.papaya.papayagraphqlspqrpoc.elasticformstarter.json;

import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;

public class JsonValidator {
    public static void validate(String json, String jsonSchema) {
        Schema schema = SchemaLoader.load(new JSONObject(jsonSchema));
        JSONObject object = new JSONObject(json);
        schema.validate(object);
    }
}
