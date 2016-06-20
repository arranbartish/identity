package com.solvedbysunrise.identity.data.entity.jpa;

import com.solvedbysunrise.bean.RefelctiveBean;


public abstract class ReflectiveEntity extends RefelctiveBean {

    @Override
    public String[] excludedFields() {
        return new String[] {"id"};
    }


}
