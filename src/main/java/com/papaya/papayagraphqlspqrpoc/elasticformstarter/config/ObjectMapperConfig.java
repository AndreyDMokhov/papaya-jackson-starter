package com.papaya.papayagraphqlspqrpoc.elasticformstarter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.papaya.papayagraphqlspqrpoc.elasticformstarter.json.DynamicObjectDeserializer;
import com.papaya.papayagraphqlspqrpoc.elasticformstarter.json.DynamicObjectSerializer;
import com.papaya.startertest.model.DynamicField;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class ObjectMapperConfig {

    @Value("${dynamic.fields.package.model}")
    private String packageValue;

    @Bean
    public ObjectMapper objectMapperCustomConf(@Autowired Set<Class<? extends DynamicField>> dynamicClasses) {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();

        for (Class<? extends DynamicField> dynamicClass : dynamicClasses) {
            module.addDeserializer(dynamicClass, new DynamicObjectDeserializer<>(dynamicClass));
            module.addSerializer(dynamicClass, new DynamicObjectSerializer<>());
        }
        mapper.registerModule(module);
        return mapper;
    }

    @Bean
    public Set<Class<? extends DynamicField>> getClasses() {
        Reflections reflections = new Reflections(packageValue);
        return reflections.getSubTypesOf(DynamicField.class);
    }
}
