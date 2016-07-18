package io.chesslave.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class JsonEventConverter implements Converter<Event> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String asString(Event event) throws IOException {
        return mapper.writeValueAsString(event);
    }

    @Override
    public Event fromString(String text) throws IOException {
        return mapper.readValue(text, Event.class);
    }
}
