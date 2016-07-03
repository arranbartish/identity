package com.solvedbysunrise.identity.service.identity.email;

import com.solvedbysunrise.identity.data.dto.EmailProperties;
import com.solvedbysunrise.identity.data.entity.jpa.email.EmailType;
import com.solvedbysunrise.identity.service.SpringInjectedEmailPropertiesService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static com.solvedbysunrise.identity.data.dto.EmailProperties.NO;
import static com.solvedbysunrise.identity.data.entity.jpa.email.EmailType.REGISTRATION_ACTIVATION;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class SpringInjectedEmailPropertiesServiceTest {

    private final Map<EmailType, EmailProperties> emailPropertiesMap = buildEmailPropertiesMap();

    private SpringInjectedEmailPropertiesService emailPropertiesService;

    @Before
    public void setup() {
        emailPropertiesService = new SpringInjectedEmailPropertiesService(emailPropertiesMap);
    }

    @Test
    public void getEmailProperties_Will_Return_The_Correct_Properties_When_Called() {
        EmailProperties emailProperties = emailPropertiesService.getEmailProperties(REGISTRATION_ACTIVATION);

        assertThat(emailProperties, allOf(
                hasEntry(EmailProperties.SUBJECT, EmailProperties.SUBJECT),
                hasEntry(EmailProperties.FROM_ADDRESS_PATTERN, EmailProperties.FROM_ADDRESS_PATTERN),
                hasEntry(EmailProperties.FROM_ADDRESS_DOMAIN, EmailProperties.FROM_ADDRESS_DOMAIN),
                hasEntry(EmailProperties.FROM_ADDRESS_USER, EmailProperties.FROM_ADDRESS_USER),
                hasEntry(EmailProperties.CAMPAIGN, EmailProperties.CAMPAIGN),
                hasEntry(EmailProperties.IS_TRACKING_ENABLED, EmailProperties.IS_TRACKING_ENABLED),
                hasEntry(EmailProperties.IS_OPEN_TRACKING_ENABLED, EmailProperties.IS_OPEN_TRACKING_ENABLED),
                hasEntry(EmailProperties.IS_CLICK_TRACKING_ENABLED, EmailProperties.IS_CLICK_TRACKING_ENABLED)));
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
