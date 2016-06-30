package com.solvedbysunrise.identity.util;

import com.solvedbysunrise.bean.ReflectiveBean;

public class SomeBean extends ReflectiveBean {

    private String value;

    public SomeBean() {
        this(null);
    }

    public SomeBean(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
