package com.solvedbysunrise.identity.service.dtto;

import com.solvedbysunrise.bean.RefelctiveBean;

public class UsernameAndPassword extends RefelctiveBean {

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
