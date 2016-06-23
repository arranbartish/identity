package com.solvedbysunrise.identity.service.properties;

import com.solvedbysunrise.identity.data.dto.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;

public class BeanInjectedApplicationProperties implements ApplicationPropertiesService{

    private final ApplicationProperties applicationProperties;

    @Autowired
    public BeanInjectedApplicationProperties(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @Override
    public ApplicationProperties getApplicationProperties() {
        return applicationProperties;
    }
}
