package com.solvedbysunrise.identity.data.dto;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class WastedTimeTest {

    private static final Duration DURATION = Duration.standardMinutes(30);
    private static final String ACTIVITY = "This app";
    private static final DateTime SIX_HOURS_IN = DateTime.now().withTimeAtStartOfDay().plusHours(6);
    private static final DateTime SEVEN_HOURS_IN = DateTime.now().withTimeAtStartOfDay().plusHours(7);

    private static final String WHO = "Arran";

    private static final WastedTime EXPECTED_WASTED_TIME = new WastedTime(WHO, DURATION, ACTIVITY, SEVEN_HOURS_IN );

    @Test
    public void WastedTime_is_the_same() throws Exception {
        final WastedTime wastedTime = new WastedTime(WHO, DURATION, ACTIVITY, SEVEN_HOURS_IN );
        assertThat(wastedTime, is(EXPECTED_WASTED_TIME));
    }

    @Test
    public void WastedTime_at_different_times_is_the_same() throws Exception {
        final WastedTime wastedTime = new WastedTime(WHO, DURATION, ACTIVITY, SIX_HOURS_IN );
        assertThat(wastedTime, is(EXPECTED_WASTED_TIME));
    }
}