package com.solvedbysunrise.identity.service;

import com.solvedbysunrise.identity.data.dto.TimeSlice;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class EventDurationGeneratingTimeSliceService implements TimeSliceService {

    private final PeriodFormatter displayFormatter = new PeriodFormatterBuilder()
            .appendDays()
            .appendSeparator(":")
            .printZeroAlways()
            .appendHours()
            .appendSeparator(":")
            .minimumPrintedDigits(2)
            .appendMinutes()
            .toFormatter();
    private final TimeSlice[] timeSlices;

    @Autowired
    public EventDurationGeneratingTimeSliceService(final int totalDurationInHours, final int intervalInMinutes) {

        Period minutesInterval = Period.minutes(intervalInMinutes);
        int totalMinutes = Period.hours(totalDurationInHours).toStandardMinutes().getMinutes();

        Collection<TimeSlice> slices = new ArrayList<>();
        Period currentMinutes = Period.ZERO;
        do {
            currentMinutes = currentMinutes.plus(minutesInterval);
            slices.add(new TimeSlice(currentMinutes.toStandardDuration(), displayFormatter.print(currentMinutes.normalizedStandard())));
        } while (currentMinutes.getMinutes() < totalMinutes);
        timeSlices = slices.toArray(new TimeSlice[slices.size()]);
    }


    @Override
    public TimeSlice[] getTimeSlices() {
        return timeSlices;
    }
}
