package com.github.wubuku.sui.bean;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class SuiDataTypeSerializer extends JsonSerializer<SuiDataType> {
    @Override
    public void serialize(SuiDataType value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.getCode());
    }
}
