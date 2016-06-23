package com.solvedbysunrise.identity.service.geoip;

import com.solvedbysunrise.identity.service.geoip.dto.GeoLocationContext;

public interface GeoLocationService {

    GeoLocationContext getGeoLocationContext(final String ipaddress);
}
