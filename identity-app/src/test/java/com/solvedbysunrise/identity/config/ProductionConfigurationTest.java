package com.solvedbysunrise.identity.config;

import com.solvedbysunrise.identity.config.exception.IncompleteConfiguration;
import com.solvedbysunrise.identity.data.dto.EmailProperties;
import com.solvedbysunrise.identity.data.entity.jpa.email.EmailType;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.Map;

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
    public void emailPropertiesMap_will_return_map() throws Exception {
        //Map<String, EmailProperties> emailPropertiesMap = productionConfiguration.emailPropertiesMap();
        //assertThat(emailPropertiesMap, is(notNullValue()));
    }
}