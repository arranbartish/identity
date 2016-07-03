package com.solvedbysunrise.identity.config;

import com.solvedbysunrise.identity.config.exception.IncompleteConfiguration;
import org.junit.Test;

public class ProductionConfigurationTest {

    private final ProductionConfiguration productionConfiguration = new ProductionConfiguration();

    @Test(expected = IncompleteConfiguration.class)
    public void applicationProperties_will_throw_exception_because_it_is_not_initialized() throws Exception {
        productionConfiguration.applicationProperties();

    }
}