package com.solvedbysunrise.identity.controller;

import com.solvedbysunrise.identity.service.WastedTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "/alias",
        //consumes = APPLICATION_JSON_UTF8_VALUE,
        produces = APPLICATION_JSON_UTF8_VALUE)
public class AliasController {

    private final WastedTimeService wastedTimeService;

    @Autowired
    public AliasController(final WastedTimeService wastedTimeService) {
        this.wastedTimeService = wastedTimeService;
    }

    @RequestMapping(method = GET)
    public Collection<String> getAliasList() {
        return wastedTimeService.getEveryoneWhoHasWastedTime();
    }
}
