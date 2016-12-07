package io.chesslave.app;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class JsonEventConverter implements Converter<Event> {

    private final ObjectMapper mapper;

    public JsonEventConverter() {
        this.mapper =   new ObjectMapper();
        this.mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    @Override
    public String asString(Event event) throws IOException {
        return mapper.writeValueAsString(event);
    }

    @Override
    public Event fromString(String text) throws IOException {
        return mapper.readValue(text, Event.class);
    }
}
