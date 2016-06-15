package com.solvedbysunrise.identity.config;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.util.Collection;

import static com.google.common.collect.Lists.newArrayList;
import static org.apache.commons.lang3.tuple.Pair.of;

public class ProductionConfiguration implements WastedTimeConfiguration {

    @Value("${baseUrl:http://localhost:8080/}")
    private String baseUrl;

    @Value("${DATABASE_URL:did-not-resolve}")
    private String databaseUrl;

    @Value("${testValue:also-did-not-work}")
    private String testValue;

    @Value("${duration.wasted.in.hours:8}")
    private Integer totalDurationInHours;

    @Value("${interval.in.minutes:15}")
    private Integer intervalInMinutes;

    @Override
    @Bean(name = "wastedTimeBaseUrl")
    public String wastedTimeBaseUrl() {
        return baseUrl;
    }

    @Override
    @Bean(name = "wastedTimeUrl")
    public String wastedTimeUrl() {
        return databaseUrl;
    }

    @Override
    @Bean(name = "testValue")
    public String testValue() {
        return testValue;
    }

    @Override
    @Bean(name = "totalDurationInHours")
    public Integer totalDurationInHours() {
        return totalDurationInHours;
    }

    @Override
    @Bean(name = "intervalInMinutes")
    public Integer intervalInMinutes() {
        return intervalInMinutes;
    }

    @Override
    @Bean(name = "config")
    public Collection<Pair<String, String>> config() {
        return newArrayList(
                of("database", wastedTimeUrl()),
                of("url", wastedTimeBaseUrl()),
                of("test", testValue()));
    }

}
