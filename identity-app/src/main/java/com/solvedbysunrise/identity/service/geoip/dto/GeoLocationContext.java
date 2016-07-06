package com.solvedbysunrise.identity.service.geoip.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.solvedbysunrise.bean.ReflectiveBean;

public class GeoLocationContext extends ReflectiveBean {

    @JsonProperty(value = "ip")
    private String ipAddress;

    @JsonProperty(value = "country_code")
    private String countryCode;

    @JsonProperty(value = "country_name")
    private String countryName;

    @JsonProperty(value = "region_name")
    private String regionName;

    @JsonProperty(value = "region_code")
    private String regionCode;

    private String city;

    private String zipcode;

    private String latitude;

    private String longitude;

    @JsonProperty(value = "metro_code")
    private String metroCode;

    private String areacode;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getMetroCode() {
        return metroCode;
    }

    public void setMetroCode(String metroCode) {
        this.metroCode = metroCode;
    }

    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }
}
