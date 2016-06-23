package com.solvedbysunrise.identity.service.security;

import org.springframework.stereotype.Service;

@Service
public class JenkinsHashService implements HashService {

    private final JenkinsHash jenkinsHash = new JenkinsHash();

    public String hash(String value) {
        byte[] valueBytes = value.getBytes();
        return Long.toString(jenkinsHash.hash(valueBytes));
    }
}
