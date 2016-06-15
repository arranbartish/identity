package com.solvedbysunrise.identity.controller;

import com.solvedbysunrise.identity.data.dto.TimeSlice;
import com.solvedbysunrise.identity.service.TimeSliceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "/time-slices",
        //consumes = APPLICATION_JSON_UTF8_VALUE,
        produces = APPLICATION_JSON_UTF8_VALUE)
public class WastedTimeSlicesController {

    private final TimeSliceService timeSliceService;

    @Autowired
    public WastedTimeSlicesController(final TimeSliceService timeSliceService) {
        this.timeSliceService = timeSliceService;
    }

    @RequestMapping(method = GET)
    public TimeSlice[] wastedTimeSlices() {
        return timeSliceService.getTimeSlices();
    }
}
