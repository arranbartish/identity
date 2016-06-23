package com.solvedbysunrise.identity.service.geoip;

import com.solvedbysunrise.identity.data.dto.ApplicationProperties;
import com.solvedbysunrise.identity.service.geoip.dto.GeoLocationContext;
import com.solvedbysunrise.identity.service.properties.ApplicationPropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;

import java.net.URI;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@Service
public class FreeGeoIPDotNetLocationService implements GeoLocationService {

    private final RestTemplate restTemplate;
    private final ApplicationPropertiesService applicationPropertiesService;

    @Autowired
    public FreeGeoIPDotNetLocationService(
            final RestTemplate restTemplate,
            final ApplicationPropertiesService applicationPropertiesService) {
        this.restTemplate = restTemplate;
        this.applicationPropertiesService = applicationPropertiesService;
    }

    @Override
    public GeoLocationContext getGeoLocationContext(String ipaddress) {
        ApplicationProperties applicationProperties = applicationPropertiesService.getApplicationProperties();

        String geoIpUrlWithIP = String.format(applicationProperties.getGeoIpURLPattern(), ipaddress);
        URI uri = fromHttpUrl(geoIpUrlWithIP).build().toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        RequestEntity request = new RequestEntity(headers, GET, uri);

        ResponseEntity<GeoLocationContext> response = restTemplate.exchange(request, GeoLocationContext.class);

        return response.getBody();
    }
}
