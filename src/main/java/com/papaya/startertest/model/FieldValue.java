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
public class FieldValue {
    private String name;
    private String value;
    private Map<String, FieldValue> nestedFields;
    private FieldTemplate fieldTemplate;
}
