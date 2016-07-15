package com.solvedbysunrise.identity.service.dtto;

import com.solvedbysunrise.bean.ReflectiveBean;

public class Account extends ReflectiveBean {

    private Long id;

    private String displayName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String[] excludedFields() {
        return new String[] {"id"};
    }
}
