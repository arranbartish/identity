package com.solvedbysunrise.identity.config;

import org.springframework.context.annotation.Bean;

import static com.google.common.collect.Lists.newArrayList;

public class TestConfiguration extends ProductionConfiguration {

    private static final String BASE_URL = "http://localhost:8080/";

    @Override
    @Bean(name = "wastedTimeBaseUrl")
    public String wastedTimeBaseUrl(){
        return BASE_URL;
    }

}
