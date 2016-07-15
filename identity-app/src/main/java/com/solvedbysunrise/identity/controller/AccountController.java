package com.solvedbysunrise.identity.controller;


import com.solvedbysunrise.identity.service.RegistrationService;
import com.solvedbysunrise.identity.service.dtto.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "/account",
        //consumes = APPLICATION_JSON_UTF8_VALUE,
        produces = APPLICATION_JSON_UTF8_VALUE)
public class AccountController {

    private final RegistrationService registrationService;

    @Autowired
    public AccountController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @RequestMapping(value = "/{accountId}", method = GET)
    public @ResponseBody Account lookupAccount(@PathVariable Long accountId) {
        return registrationService.getAccount(accountId);
    }
}
