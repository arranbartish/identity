package com.solvedbysunrise.identity.service.dtto;

import com.solvedbysunrise.bean.ReflectiveBean;

public class IdOnly extends ReflectiveBean {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
