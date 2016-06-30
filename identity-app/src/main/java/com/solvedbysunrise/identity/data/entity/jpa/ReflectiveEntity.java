package com.solvedbysunrise.identity.data.entity.jpa;

import com.solvedbysunrise.bean.ReflectiveBean;


public abstract class ReflectiveEntity extends ReflectiveBean {

    @Override
    public String[] excludedFields() {
        return new String[] {"id"};
    }


}
