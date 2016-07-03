package com.solvedbysunrise.identity.config;

import com.google.common.collect.Lists;
import com.solvedbysunrise.identity.config.exception.IncompleteConfiguration;
import com.solvedbysunrise.identity.data.dto.ApplicationProperties;
import com.solvedbysunrise.identity.data.dto.ApplicationProperties.Key;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.collect.Lists.newArrayList;
import static com.solvedbysunrise.identity.data.dto.ApplicationProperties.Key.values;
import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isBlank;
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

    @Bean(name = "applicationProperties")
    public ApplicationProperties applicationProperties() {
        ApplicationProperties applicationProperties = new ApplicationProperties();
        List<Key> unpamppedKeys = new ArrayList<>();
        try ( Stream<Key> keys = stream(values()) ) {
            unpamppedKeys.addAll(
                    keys
                            .parallel()
                            .filter(key -> isBlank(applicationProperties.get(key.getKey())))
                            .collect(toList()));
        }
        if ( isNotEmpty(unpamppedKeys) ) {
            StringBuilder builder = new StringBuilder();
            unpamppedKeys.stream().forEach(unmappedKey -> builder.append("- ").append(unmappedKey.getKey()).append("\n"));
            throw new IncompleteConfiguration(format("[%s] configuration items are missing\n%s", unpamppedKeys.size(), builder.toString()));
        }
        return applicationProperties;
    }

}
