package com.solvedbysunrise.identity.controller;

import com.solvedbysunrise.identity.WastedtimeApplication;
import com.solvedbysunrise.identity.config.TestConfiguration;
import com.solvedbysunrise.identity.data.dao.WastedTimeDao;
import com.solvedbysunrise.identity.data.dto.WastedTime;
import com.solvedbysunrise.identity.service.WastedTimeService;
import org.joda.time.Duration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.net.URI;

import static com.solvedbysunrise.identity.util.UriUtil.getFullyQualifiedUriPattern;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.hasItemInArray;
import static org.joda.time.DateTime.now;
import static org.joda.time.Duration.standardMinutes;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {WastedtimeApplication.class, TestConfiguration.class})
@WebIntegrationTest
public class WastedControllerIntegrationTest {

    private static final WastedTime WASTED = new WastedTime("Arran", standardMinutes(30), "Tracking time", now());

    private static final String WASTED_RESOURCE = "wasted";
    private static final String WASTED_ACTIVITES_RESOURCE = "wasted/activities";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private String wastedTimeBaseUrl;

    @Autowired
    private WastedTimeService wastedTimeService;

    @Autowired
    private WastedTimeDao wastedTimeDao;

    private URI wastedUri;

    private URI wastedActivitiesUri;

    @Before
    public void setup(){
        wastedUri = new UriTemplate(getFullyQualifiedUriPattern(wastedTimeBaseUrl, WASTED_RESOURCE)).expand();
        wastedActivitiesUri = new UriTemplate(getFullyQualifiedUriPattern(wastedTimeBaseUrl, WASTED_ACTIVITES_RESOURCE)).expand();
    }

    @Test
    public void wasted_time_will_be_ok_with_payload() throws Exception {
        ResponseEntity<WastedTime[]> exchange = restTemplate.exchange(postRequestForWastedTime(), WastedTime[].class);
        assertThat(exchange.getStatusCode(), is(OK));
    }

    @Test
    public void wasted_time_will_return_collection_including_item_when_posted_with_item() throws Exception {
        ResponseEntity<WastedTime[]> wastedTimeResponseEntity = restTemplate.exchange(postRequestForWastedTime(), WastedTime[].class);
        assertThat(wastedTimeResponseEntity.getBody(), arrayContaining(WASTED));
    }


    @Test(expected = HttpClientErrorException.class)
    public void wasted_time_will_fail_validation_when_who_is_null() throws Exception {
        WastedTime wastedToFail = new WastedTime(null, WASTED.getDuration(), WASTED.getActivity(), WASTED.getDate());
        restTemplate.exchange(postRequestForWastedTime(wastedToFail), String.class);
    }

    @Test(expected = HttpClientErrorException.class)
    public void wasted_time_will_fail_validation_when_date_is_null() throws Exception {
        WastedTime wastedToFail = new WastedTime(WASTED.getWho(), WASTED.getDuration(), WASTED.getActivity(), null);
        restTemplate.exchange(postRequestForWastedTime(wastedToFail), String.class);
    }

    @Test(expected = HttpClientErrorException.class)
    public void wasted_time_will_fail_validation_when_duration_is_null() throws Exception {
        WastedTime wastedToFail = new WastedTime(WASTED.getWho(), null, WASTED.getActivity(), WASTED.getDate());
        restTemplate.exchange(postRequestForWastedTime(wastedToFail), String.class);
    }

    @Test(expected = HttpClientErrorException.class)
    public void wasted_time_will_fail_validation_when_duration_is_strange_time_slice() throws Exception {
        WastedTime wastedToFail = new WastedTime(WASTED.getWho(), Duration.standardMinutes(6), WASTED.getActivity(), WASTED.getDate());
        restTemplate.exchange(postRequestForWastedTime(wastedToFail), String.class);
    }


    @Test(expected = HttpClientErrorException.class)
    public void wasted_time_will_fail_validation_when_activity_is_blank() throws Exception {
        WastedTime wastedToFail = new WastedTime(WASTED.getWho(), WASTED.getDuration(), "", WASTED.getDate());
        restTemplate.exchange(postRequestForWastedTime(wastedToFail), String.class);
    }


    @Test
    public void wasted_time_will_be_ok_on_get() throws Exception {
        ResponseEntity<WastedTime[]> exchange = restTemplate.exchange(getRequestForWastedTime(), WastedTime[].class);
        assertThat(exchange.getStatusCode(), is(OK));
    }

    @Test
    public void wasted_time_activities_will_all_be_returned_on_get() throws Exception {
        wastedTimeService.recordWastedTime(new WastedTime("Harry", standardMinutes(15), "Something else", now()));
        wastedTimeService.recordWastedTime(new WastedTime("Larry", standardMinutes(15), "Another thing", now()));

        ResponseEntity<String[]> exchange = restTemplate.exchange(getRequestForURI(wastedActivitiesUri), String[].class);
        assertThat(exchange.getBody(), arrayContaining("Another Thing", "Something Else"));
    }


    @Test
    public void wasted_time_will_return_wasted_items_on_get() throws Exception {
        wastedTimeService.recordWastedTime(WASTED);

        ResponseEntity<WastedTime[]> exchange = restTemplate.exchange(getRequestForWastedTime(), WastedTime[].class);
        assertThat(exchange.getBody(), arrayContaining(WASTED));
    }

    private RequestEntity<WastedTime> getRequestForWastedTime() {
        return getRequestForURI(wastedUri);
    }

    private RequestEntity<WastedTime> getRequestForURI(URI uri) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON_UTF8);
        return new RequestEntity<>(headers, GET, uri);
    }

    private RequestEntity<WastedTime> postRequestForWastedTime() {
        return postRequestForWastedTime(WASTED);
    }

    private RequestEntity<WastedTime> postRequestForWastedTime(WastedTime wastedTime) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON_UTF8);
        return new RequestEntity<>(wastedTime, headers, POST, wastedUri);
    }

    @After
    public void deleteAll(){
        wastedTimeDao.deleteAll();
    }
}