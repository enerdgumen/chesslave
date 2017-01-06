package io.chesslave.app

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper

class JsonEventConverter : Converter<Event> {

    private val mapper = ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

    override fun asString(event: Event): String = mapper.writeValueAsString(event)

    override fun fromString(text: String): Event = mapper.readValue(text, Event::class.java)
}
