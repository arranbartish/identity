package com.solvedbysunrise.identity.config;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.Map;

public interface WastedTimeConfiguration {

    String identityBaseUrl();

    String wastedTimeUrl();

    String wastedTimeBaseUrl();

    String testValue();

    Integer totalDurationInHours();

    Integer intervalInMinutes();

    Collection<Pair<String, String>> config();

}
