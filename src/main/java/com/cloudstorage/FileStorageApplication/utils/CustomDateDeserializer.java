package com.cloudstorage.FileStorageApplication.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@JsonComponent
public class CustomDateDeserializer extends JsonDeserializer<LocalDateTime> {

    private static DateTimeFormatter dateFormat=DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");//2018-12-20T06:56:54Z

    @Override
    public LocalDateTime deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String cdate=jp.getText();
        if(cdate==null || cdate.isEmpty()){
            return null;
        }
        return LocalDateTime.parse(cdate,dateFormat);
    }
}
