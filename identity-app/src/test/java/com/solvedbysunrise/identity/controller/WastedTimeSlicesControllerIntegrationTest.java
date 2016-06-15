package com.solvedbysunrise.identity.controller;

import com.solvedbysunrise.identity.WastedtimeApplication;
import com.solvedbysunrise.identity.config.TestConfiguration;
import com.solvedbysunrise.identity.data.dto.TimeSlice;
import com.solvedbysunrise.identity.service.TimeSliceService;
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
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {WastedtimeApplication.class, TestConfiguration.class})
@WebIntegrationTest
public class WastedTimeSlicesControllerIntegrationTest {

    private static final String TIME_SLICE_RESOURCE = "/time-slices";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private String wastedTimeBaseUrl;

    @Autowired
    private TimeSliceService timeSliceService;

    private URI timeSlicesUri;

    @Before
    public void setup(){
        timeSlicesUri = new UriTemplate(getFullyQualifiedUriPattern(wastedTimeBaseUrl, TIME_SLICE_RESOURCE)).expand();
    }


    @Test
    public void getTimeSlices_will_return_all_slices() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON_UTF8);
        RequestEntity<Object> request = new RequestEntity<>(headers, GET, timeSlicesUri);

        ResponseEntity<TimeSlice[]> exchange = restTemplate.exchange(request, TimeSlice[].class);

        assertThat(exchange.getBody(), arrayContaining(timeSliceService.getTimeSlices()));
    }

    @Test
    public void getTimeSlices_will_return_OK() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON_UTF8);
        RequestEntity<Object> request = new RequestEntity<>(headers, GET, timeSlicesUri);

        ResponseEntity<TimeSlice[]> exchange = restTemplate.exchange(request, TimeSlice[].class);

        assertThat(exchange.getStatusCode(), is(OK));
    }
}