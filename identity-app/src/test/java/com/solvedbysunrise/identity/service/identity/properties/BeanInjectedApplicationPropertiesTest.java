package com.solvedbysunrise.identity.service.identity.properties;

import com.solvedbysunrise.identity.data.dto.ApplicationProperties;
import com.solvedbysunrise.identity.service.properties.BeanInjectedApplicationProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static com.solvedbysunrise.identity.data.dto.ApplicationProperties.Key.MAIL_GUN_API_KEY;
import static com.solvedbysunrise.identity.data.dto.ApplicationProperties.Key.MAIL_GUN_API_URL_PATTERN;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class BeanInjectedApplicationPropertiesTest {

    private static final String SOME_VALUE = "SOME_VALUE";

    private ApplicationProperties applicationProperties = buildApplicationProperties();

    @InjectMocks
    private BeanInjectedApplicationProperties databaseApplicationPropertiesService;

    @Test
    public void getApplicationProperties_Will_Return_The_Correct_Properties_When_Called() {

        ApplicationProperties applicationProperties = databaseApplicationPropertiesService.getApplicationProperties();

        assertThat(applicationProperties.getMailGunApiKey(), is(SOME_VALUE));
        assertThat(applicationProperties.getMailGunApiURLPattern(), is(SOME_VALUE));
    }

    private static ApplicationProperties buildApplicationProperties() {
        ApplicationProperties applicationProperties = new ApplicationProperties();
        applicationProperties.put(MAIL_GUN_API_KEY.getKey(), SOME_VALUE);
        applicationProperties.put(MAIL_GUN_API_URL_PATTERN.getKey(), SOME_VALUE);
        return applicationProperties;
    }
}
