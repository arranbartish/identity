package com.solvedbysunrise.identity.mock.controller;

import com.solvedbysunrise.identity.mock.service.MailGunMessageCaptor;
import com.solvedbysunrise.identity.service.dtto.IdOnly;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(path = "/v2",
        //consumes = APPLICATION_JSON_UTF8_VALUE,
        produces = APPLICATION_JSON_UTF8_VALUE)
public class MockMailGunController {

    private final MailGunMessageCaptor captor;

    @Autowired
    public MockMailGunController(MailGunMessageCaptor captor) {
        this.captor = captor;
    }

    @RequestMapping(path = "/{domain}/messages", method = POST)
    public @ResponseBody IdOnly handleRequest(@PathVariable String domain, @RequestBody MultiValueMap<String, Object> paramters) {
        paramters.add("DOMAIN", domain);
        captor.storeMessage(paramters);
        return new IdOnly(UUID.randomUUID().toString());
    }

}
