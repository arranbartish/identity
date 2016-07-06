package com.solvedbysunrise.identity.data.dto;

import com.solvedbysunrise.bean.ReflectiveBean;

public class Hello extends ReflectiveBean {

    private String who;

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }
}
