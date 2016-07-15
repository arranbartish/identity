package com.solvedbysunrise.identity.service.dtto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.solvedbysunrise.bean.ReflectiveBean;

public class IdOnly extends ReflectiveBean {

    private final String id;

    public String getId() {
        return id;
    }

    @JsonCreator
    public IdOnly(@JsonProperty("id") String id) {
        this.id = id;
    }
}
