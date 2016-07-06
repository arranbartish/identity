package com.solvedbysunrise.identity.service.dtto;

import com.solvedbysunrise.bean.ReflectiveBean;

public class Address extends ReflectiveBean {

    private String countryCode;

    private String street;

    private String city;

    private String stateOrProvice;

    private String postOrZipCode;

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateOrProvice() {
        return stateOrProvice;
    }

    public void setStateOrProvice(String stateOrProvice) {
        this.stateOrProvice = stateOrProvice;
    }

    public String getPostOrZipCode() {
        return postOrZipCode;
    }

    public void setPostOrZipCode(String postOrZipCode) {
        this.postOrZipCode = postOrZipCode;
    }
}
