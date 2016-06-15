package com.solvedbysunrise.identity.data.entity.jpa.user;

import com.solvedbysunrise.identity.data.entity.jpa.ReflectiveEntity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AuthorityRoleId extends ReflectiveEntity {

    @Column(name = "ROLE", nullable = false)
    private String role;

    @Column(name = "AUTHORITY", nullable = false)
    private String authority;


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
