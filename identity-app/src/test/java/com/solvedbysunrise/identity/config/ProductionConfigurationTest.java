package com.solvedbysunrise.identity.config;

import com.solvedbysunrise.identity.config.exception.IncompleteConfiguration;
import com.solvedbysunrise.identity.service.EmailPropertiesService;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class ProductionConfigurationTest {

    private final ProductionConfiguration productionConfiguration = new ProductionConfiguration();

    @Test(expected = IncompleteConfiguration.class)
    public void applicationProperties_will_throw_exception_because_it_is_not_initialized() throws Exception {
        productionConfiguration.applicationProperties();

    }

    @Test
    public void emailPropertiesService_will_return_service() throws Exception {
        EmailPropertiesService emailPropertiesService = productionConfiguration.emailPropertiesService();
        assertThat(emailPropertiesService, is(notNullValue()));
    }
}