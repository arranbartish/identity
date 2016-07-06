package com.solvedbysunrise.identity.service.security;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class JenkinsHashServiceTest {

    private final JenkinsHashService jenkinsHashService = new JenkinsHashService();

    private final String JSON_ONE = "{\"accountId\":\"1\"}";
    private final String JSON_TWO = "{\"accountId\":\"2\"}";

    @Test
    public void hash_Will_Return_The_Same_Hash_For_The_Same_Values() throws Exception {
        String firstResult = jenkinsHashService.hash(JSON_ONE);
        String secondResult = jenkinsHashService.hash(JSON_ONE);
        assertThat(firstResult, is(secondResult));
    }

    @Test
    public void hash_Will_Return_A_Different_Hash_For_Different_Same_Values() throws Exception {
        String firstResult = jenkinsHashService.hash(JSON_ONE);
        String secondDifferentResult = jenkinsHashService.hash(JSON_TWO);
        assertThat(secondDifferentResult, is(not(firstResult)));
    }
}
