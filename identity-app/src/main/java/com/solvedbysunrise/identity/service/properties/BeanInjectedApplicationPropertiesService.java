package com.solvedbysunrise.identity.service.properties;

import com.solvedbysunrise.identity.data.dto.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BeanInjectedApplicationPropertiesService implements ApplicationPropertiesService{

    private final ApplicationProperties applicationProperties;

    @Autowired
    public BeanInjectedApplicationPropertiesService(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @Override
    public ApplicationProperties getApplicationProperties() {
        return applicationProperties;
    }
}
