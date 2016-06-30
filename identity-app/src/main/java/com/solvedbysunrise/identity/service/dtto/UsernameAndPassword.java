package com.solvedbysunrise.identity.service.dtto;

import com.solvedbysunrise.bean.ReflectiveBean;

public class UsernameAndPassword extends ReflectiveBean {

    private final String username;
    private final String password;

    public UsernameAndPassword(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
