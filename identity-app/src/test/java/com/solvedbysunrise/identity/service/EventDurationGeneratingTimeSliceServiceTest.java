package com.solvedbysunrise.identity.service;

import com.solvedbysunrise.identity.data.dto.TimeSlice;
import org.hamcrest.Matchers;
import org.junit.Test;

import static org.joda.time.Duration.standardMinutes;
import static org.junit.Assert.*;

public class EventDurationGeneratingTimeSliceServiceTest {

    private final TimeSlice THIRTY_MINUTES = new TimeSlice(standardMinutes(30), "0:30");
    private final TimeSlice ONE_HOUR = new TimeSlice(standardMinutes(60), "1:00");
    private final TimeSlice ONE_HOUR_AND_THIRTY_MINUTES = new TimeSlice(standardMinutes(90), "1:30");
    private final TimeSlice TWO_HOURS = new TimeSlice(standardMinutes(120), "2:00");

    @Test
    public void getTimeSlices_will_return_just_thirty_minutes() throws Exception {
        assertThat(getSlices(0, 30), Matchers.arrayContaining(THIRTY_MINUTES));
    }


    @Test
    public void getTimeSlices_will_return_slices_of_one_hour() throws Exception {
        assertThat(getSlices(1, 30), Matchers.arrayContaining(THIRTY_MINUTES, ONE_HOUR));
    }

    @Test
    public void getTimeSlices_will_return_slices_of_two_hours() throws Exception {
        assertThat(getSlices(2, 30), Matchers.arrayContaining(THIRTY_MINUTES, ONE_HOUR, ONE_HOUR_AND_THIRTY_MINUTES, TWO_HOURS));
    }

    private TimeSlice[] getSlices(final int hours, final int intervalInMinutes) {
        TimeSliceService timeSliceService = new EventDurationGeneratingTimeSliceService(hours, intervalInMinutes);
        return timeSliceService.getTimeSlices();
    }
}