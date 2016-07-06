package com.solvedbysunrise.identity.service.geoip;


import com.solvedbysunrise.identity.data.dto.ApplicationProperties;
import com.solvedbysunrise.identity.service.geoip.dto.GeoLocationContext;
import com.solvedbysunrise.identity.service.properties.ApplicationPropertiesService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static com.solvedbysunrise.identity.data.dto.ApplicationProperties.Key.GEO_IP_URL_PATTERN;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FreeGeoIPDotNetLocationServiceTest {


    private static final String IP_ADDRESS = "184.144.220.211";
    private static final String URL_PATTERN = "http://someaddress/%s";

    private static final GeoLocationContext EXPECTED_CONTEXT = buildResponse();

    @InjectMocks
    private FreeGeoIPDotNetLocationService freeGeoIPDotNetLocationService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ApplicationPropertiesService applicationPropertiesService;

    @Mock
    private ResponseEntity responseEntity;

    @Test
    public void getGeoLocationContext_Will_Return_A_Context_When_Given_Json() throws Exception {
        when(applicationPropertiesService.getApplicationProperties()).thenReturn(buildApplicationProperties());
        when(restTemplate.exchange(any(), Mockito.<Class<?>>any())).thenReturn(responseEntity);
        when(responseEntity.getBody()).thenReturn(buildResponse());
        GeoLocationContext geoLocationContext = freeGeoIPDotNetLocationService.getGeoLocationContext(IP_ADDRESS);

        assertThat(geoLocationContext, is(EXPECTED_CONTEXT));


    }

    private ApplicationProperties buildApplicationProperties() {
        ApplicationProperties applicationProperties = new ApplicationProperties();
        applicationProperties.put(GEO_IP_URL_PATTERN.getKey(), URL_PATTERN);
        return applicationProperties;
    }

    private static GeoLocationContext buildResponse(){
        GeoLocationContext context = new GeoLocationContext();
        context.setIpAddress("184.144.220.211");
        context.setCountryCode("CA");
        context.setCountryName("Canada");
        context.setRegionCode("QC");
        context.setRegionName("Québec");
        context.setCity("Terrebonne");
        context.setZipcode("4983");
        context.setLatitude("45.6833");
        context.setLongitude("-73.6333");
        context.setMetroCode("somewhere");
        context.setAreacode("else");
        return context;
    }
}
