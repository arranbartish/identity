package com.solvedbysunrise.identity.service.identity.email;

import com.solvedbysunrise.identity.data.dto.EmailProperties;
import com.solvedbysunrise.identity.data.entity.jpa.email.EmailType;
import com.solvedbysunrise.identity.service.SpringInjectedEmailPropertiesService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static com.solvedbysunrise.identity.data.dto.EmailProperties.NO;
import static com.solvedbysunrise.identity.data.entity.jpa.email.EmailType.REGISTRATION_ACTIVATION;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class SpringInjectedEmailPropertiesServiceTest {

    private static final String SOME_VALUE = "SOME_VALUE";

    private final Map<EmailType, EmailProperties> emailPropertiesMap = buildEmailPropertiesMap();

    @InjectMocks
    private SpringInjectedEmailPropertiesService emailPropertiesService;

    @Test
    public void getEmailProperties_Will_Return_The_Correct_Properties_When_Called() {


        EmailProperties emailProperties = emailPropertiesService.getEmailProperties(REGISTRATION_ACTIVATION);

        assertThat(emailProperties.getCampaign(), is(SOME_VALUE));
        assertThat(emailProperties.getFromAddressDomain(), is(SOME_VALUE));
        assertThat(emailProperties.getFromAddressUser(), is(SOME_VALUE));
        assertThat(emailProperties.getFromAddressPattern(), is(SOME_VALUE));
        assertThat(emailProperties.getSubject(), is(SOME_VALUE));
        assertThat(emailProperties.isTrackingEnabled(), is(NO));
        assertThat(emailProperties.isOpenTrackingEnabled(), is(NO));
        assertThat(emailProperties.isClickTrackingEnabled(), is(NO));
    }

    private Map<EmailType, EmailProperties> buildEmailPropertiesMap() {
        Map<EmailType, EmailProperties> map = new HashMap<>();
        EmailProperties emailProperties = new EmailProperties();

        emailProperties.put(EmailProperties.SUBJECT, EmailProperties.SUBJECT);
        emailProperties.put(EmailProperties.FROM_ADDRESS_PATTERN, EmailProperties.FROM_ADDRESS_PATTERN);
        emailProperties.put(EmailProperties.FROM_ADDRESS_DOMAIN, EmailProperties.FROM_ADDRESS_DOMAIN);
        emailProperties.put(EmailProperties.FROM_ADDRESS_USER, EmailProperties.FROM_ADDRESS_USER);
        emailProperties.put(EmailProperties.CAMPAIGN, EmailProperties.CAMPAIGN);
        emailProperties.put(EmailProperties.IS_TRACKING_ENABLED, EmailProperties.IS_TRACKING_ENABLED);
        emailProperties.put(EmailProperties.IS_OPEN_TRACKING_ENABLED, EmailProperties.IS_OPEN_TRACKING_ENABLED);
        emailProperties.put(EmailProperties.IS_CLICK_TRACKING_ENABLED, EmailProperties.IS_CLICK_TRACKING_ENABLED);

        map.put(REGISTRATION_ACTIVATION, emailProperties);
        return map;
    }

}
