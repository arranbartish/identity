package com.solvedbysunrise.identity.service.geoip;


import com.receiptdrop.service.geoip.dto.GeoLocationContext;
import com.receiptdrop.service.properties.ApplicationPropertiesService;
import com.receiptdrop.service.properties.dto.ApplicationProperties;
import com.solvedbysunrise.identity.service.geoip.dto.GeoLocationContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.integration.Message;
import org.springframework.integration.core.MessagingOperations;
import org.springframework.integration.support.MessageBuilder;

import static com.receiptdrop.service.properties.dto.ApplicationProperties.GEO_IP_URL_PATTERN;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class FreeGeoIPDotNetLocationServiceTest {


    private static final String JSON_RESPONSE = "{\"ip\":\"184.144.220.211\",\"country_code\":\"CA\",\"country_name\":\"Canada\",\"region_code\":\"QC\",\"region_name\":\"Québec\",\"city\":\"Terrebonne\",\"zipcode\":\"4983\",\"latitude\":45.6833,\"longitude\":-73.6333,\"metro_code\":\"somewhere\",\"areacode\":\"else\"}";
    private static final String IP_ADDRESS = "184.144.220.211";
    private static final String URL_PATTERN = "http://someaddress/%s";

    @InjectMocks
    private FreeGeoIPDotNetLocationService freeGeoIPDotNetLocationService;

    @Mock
    private MessagingOperations messagingOperations;

    @Mock
    private ApplicationPropertiesService applicationPropertiesService;

    @Test
    public void getGeoLocationContext_Will_Return_A_Context_When_Given_Json() throws Exception {
        Mockito.when(applicationPropertiesService.getApplicationProperties()).thenReturn(buildApplicationProperties());
        Mockito.when(messagingOperations.sendAndReceive(Mockito.any(Message.class))).thenReturn((Message)buildResponse());
        GeoLocationContext geoLocationContext = freeGeoIPDotNetLocationService.getGeoLocationContext(IP_ADDRESS);

        assertThat(geoLocationContext, is(notNullValue()));


    }

    private ApplicationProperties buildApplicationProperties() {
        ApplicationProperties applicationProperties = new ApplicationProperties();
        applicationProperties.put(GEO_IP_URL_PATTERN, URL_PATTERN);
        return applicationProperties;
    }

    private Message<String> buildResponse(){
        Message<String> response = MessageBuilder.withPayload(JSON_RESPONSE).build();
        return response;
    }
}
