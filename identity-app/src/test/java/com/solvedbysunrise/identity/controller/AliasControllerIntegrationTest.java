package com.solvedbysunrise.identity.controller;

import com.solvedbysunrise.identity.WastedtimeApplication;
import com.solvedbysunrise.identity.config.TestConfiguration;
import com.solvedbysunrise.identity.data.dao.WastedTimeDao;
import com.solvedbysunrise.identity.data.dto.WastedTime;
import com.solvedbysunrise.identity.service.WastedTimeService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.net.URI;

import static com.solvedbysunrise.identity.util.UriUtil.getFullyQualifiedUriPattern;
import static org.hamcrest.Matchers.arrayContaining;
import static org.joda.time.DateTime.now;
import static org.joda.time.Duration.standardMinutes;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {WastedtimeApplication.class, TestConfiguration.class})
@WebIntegrationTest
public class AliasControllerIntegrationTest {

    private static final String ALIAS_RESOURCE = "/alias";

    @Autowired
    private WastedTimeDao wastedTimeDao;

    @Autowired
    private WastedTimeService wastedTimeService;

    @Autowired
    private String wastedTimeBaseUrl;

    @Autowired
    private RestTemplate restTemplate;

    private URI aliasUri;

    @Before
    public void setup(){
        wastedTimeService.recordWastedTime(new WastedTime("Arran", standardMinutes(30), "This App", now()));
        aliasUri = new UriTemplate(getFullyQualifiedUriPattern(wastedTimeBaseUrl, ALIAS_RESOURCE)).expand();
    }

    @Test
    public void alias_will_return_all_time_wasters() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON_UTF8);
        RequestEntity<Object> request = new RequestEntity<>(headers, GET, aliasUri);

        ResponseEntity<String[]> exchange = restTemplate.exchange(request, String[].class);

        assertThat(exchange.getBody(), arrayContaining("Arran"));
    }

    @After
    public void deleteAll(){
        wastedTimeDao.deleteAll();
    }
}