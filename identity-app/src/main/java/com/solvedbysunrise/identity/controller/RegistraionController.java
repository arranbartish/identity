package com.solvedbysunrise.identity.controller;

import com.solvedbysunrise.identity.service.RegistrationService;
import com.solvedbysunrise.identity.service.dtto.Account;
import com.solvedbysunrise.identity.service.dtto.PersonalRegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriTemplate;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.servlet.view.UrlBasedViewResolver.REDIRECT_URL_PREFIX;

@RestController
@RequestMapping(value = "/register",
        //consumes = APPLICATION_JSON_UTF8_VALUE,
        produces = APPLICATION_JSON_UTF8_VALUE)
public class RegistraionController {

    private final RegistrationService registrationService;

    @Autowired
    public RegistraionController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @RequestMapping(method = POST)
    public void registerAccount(HttpServletResponse response, @RequestBody PersonalRegistrationRequest registrationRequest) throws IOException {
        Account account = registrationService.createAccount(registrationRequest);
        UriTemplate uri = new UriTemplate("/account/{id}");
        response.sendRedirect(uri.expand(account.getId()).toString());
    }
}
