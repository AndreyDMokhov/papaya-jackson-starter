package com.papaya.startertest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FieldTemplate {

    //field name
    private String label;
    //type should be enum
    private String type;
    //should be designed
    private Validation validation;
    private String defaultValue;
    private String description;
    private String comment;
    //Map key is label
    Map<String, FieldTemplate> nestedFieldsTemplates;
}
