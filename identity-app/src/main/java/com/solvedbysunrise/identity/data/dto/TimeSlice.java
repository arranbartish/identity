package com.solvedbysunrise.identity.data.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.solvedbysunrise.bean.RefelctiveBean;
import com.solvedbysunrise.identity.data.dto.serialization.DurationDeserializer;
import com.solvedbysunrise.identity.data.dto.serialization.DurationSerializer;
import org.joda.time.Duration;

public class TimeSlice extends RefelctiveBean {

    @JsonDeserialize(using = DurationDeserializer.class)
    @JsonSerialize(using = DurationSerializer.class)
    private final Duration slice;

    private final String display;

    @JsonCreator
    public TimeSlice(@JsonProperty("slice") Duration slice, @JsonProperty("display") String display) {
        this.slice = slice;
        this.display = display;
    }

    public Duration getSlice() {
        return slice;
    }

    public String getDisplay() {
        return display;
    }

    @Override
    public String[] excludedFields() {
        return new String[] {"slice"};
    }

    @Override
    public boolean equals(Object that) {
        return super.equals(that) && internalEquals((TimeSlice) that);
    }

    private boolean internalEquals(TimeSlice that) {
        return that.getSlice().isEqual(this.getSlice());
    }
}
