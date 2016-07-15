package com.solvedbysunrise.identity.config;

import com.solvedbysunrise.identity.data.dto.ApplicationProperties;
import com.solvedbysunrise.identity.util.UriUtil;
import org.springframework.context.annotation.Bean;

import static com.google.common.collect.Lists.newArrayList;
import static com.solvedbysunrise.identity.data.dto.ApplicationProperties.Key.MAIL_GUN_API_URL_PATTERN;
import static com.solvedbysunrise.identity.util.UriUtil.getFullyQualifiedUriPattern;

public class TestConfiguration extends ProductionConfiguration {

    private static final String BASE_URL = "http://localhost:8080/";

    private static final String MOCK_MAIL_GUN_URI_PATTERN = "/v2/%s/messages";

    @Override
    @Bean(name = "wastedTimeBaseUrl")
    public String wastedTimeBaseUrl(){
        return BASE_URL;
    }

    @Override
    @Bean(name = "identityBaseUrl")
    public String identityBaseUrl(){
        return BASE_URL;
    }


    @Override
    public ApplicationProperties applicationProperties() {
        ApplicationProperties applicationProperties = super.applicationProperties();
        applicationProperties.put(MAIL_GUN_API_URL_PATTERN.getKey(),
                getFullyQualifiedUriPattern(identityBaseUrl(), MOCK_MAIL_GUN_URI_PATTERN));
        return applicationProperties;
    }
}
