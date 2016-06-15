package com.solvedbysunrise.identity.controller;

import com.solvedbysunrise.identity.data.dto.WastedTime;
import com.solvedbysunrise.identity.service.WastedTimeService;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

import static com.solvedbysunrise.identity.controller.media.WastedMediaType.APPLICATION_CSV_VALUE;
import static java.lang.String.format;
import static java.time.chrono.Chronology.ofLocale;
import static java.time.format.DateTimeFormatterBuilder.getLocalizedDateTimePattern;
import static java.time.format.FormatStyle.*;
import static java.util.Locale.CANADA;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/wasted",
        //consumes = APPLICATION_JSON_UTF8_VALUE,
        produces = APPLICATION_JSON_UTF8_VALUE)
public class WastedController {

    private final WastedTimeService wastedTimeService;

    @Autowired
    public WastedController(final WastedTimeService wastedTimeService) {
        this.wastedTimeService = wastedTimeService;
    }

    @RequestMapping(method = POST)
    public Collection<WastedTime> wastedTime(@Valid @RequestBody WastedTime wastedTime) {
        return wastedTimeService.recordWastedTime(wastedTime);
    }

    @RequestMapping(method = GET)
    public Collection<WastedTime> wastedTime() {
        return wastedTimeService.getAllWastedTime();
    }


    @RequestMapping(method = GET,
            produces = APPLICATION_CSV_VALUE,
            params = "type=csv")
    public String wastedTimeInCSVFormat(@ModelAttribute CSVFormatter CSVFormatter) {
        StringBuilder builder = new StringBuilder();
        org.joda.time.format.DateTimeFormatterBuilder dateTimeFormatterBuilder = new org.joda.time.format.DateTimeFormatterBuilder();

        DateTimeFormatter dateTimeFormatter = dateTimeFormatterBuilder
                .appendPattern(getLocalizedDateTimePattern(MEDIUM, null, ofLocale(CANADA), CANADA))
                .toFormatter();

        wastedTimeService.getAllWastedTime().stream()
                .map(wastedTime -> format("%s,%s,%s,%s", wastedTime.getWho(), wastedTime.getActivity(), wastedTime.getDuration(), dateTimeFormatter.print(wastedTime.getDate())))
                .forEach(string -> builder.append(string).append('\n'));
        return builder.toString();
    }

    @RequestMapping(value = "/activities", method = GET)
    public Collection<String> wastedTimeActivities() {
        return wastedTimeService.getAllWastedTimeActivities();
    }


}
