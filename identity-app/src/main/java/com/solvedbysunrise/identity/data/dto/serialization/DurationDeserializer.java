package com.solvedbysunrise.identity.data.dto.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.joda.time.Duration;

import java.io.IOException;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class DurationDeserializer extends JsonDeserializer<Duration> {

    @Override
    public Duration deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (isBlank(p.getValueAsString())) {
            return null;
        }
        long valueAsLong = p.getValueAsLong();
        return Duration.millis(valueAsLong);
    }
}
