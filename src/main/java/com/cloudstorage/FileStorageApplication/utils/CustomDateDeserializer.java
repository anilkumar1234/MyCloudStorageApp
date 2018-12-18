package com.cloudstorage.FileStorageApplication.utils;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@JsonComponent
public class CustomDateDeserializer extends JsonDeserializer<LocalDateTime> {

    private static DateTimeFormatter dateFormat=DateTimeFormatter.ofPattern("dd/mm/yyyy");

    @Override
    public LocalDateTime deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        String cdate=jp.getText();
        return LocalDateTime.parse(cdate,dateFormat);
    }
}
