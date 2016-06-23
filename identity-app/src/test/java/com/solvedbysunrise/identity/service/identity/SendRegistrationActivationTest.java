package com.solvedbysunrise.identity.service.identity;


import org.junit.Test;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SendRegistrationActivationTest {

    public final static String URL = "https://api.mailgun.net/v2/receiptdrop.mailgun.org/messages";
    public final static String EXPECTED_URL = "https://user:password@api.mailgun.net/v2/receiptdrop.mailgun.org/messages";

    @Test
    public void uriComponentsExperiment(){
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(URL).userInfo("user:password").build();

        String url = uriComponents.toUriString();

        assertThat(url, is(EXPECTED_URL));

    }
}
