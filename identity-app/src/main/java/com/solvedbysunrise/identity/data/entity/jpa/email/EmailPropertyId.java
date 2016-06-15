package com.solvedbysunrise.identity.data.entity.jpa.email;

import com.solvedbysunrise.identity.data.entity.jpa.ReflectiveEntity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class EmailPropertyId extends ReflectiveEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "EMAIL_TYPE", nullable = false, updatable = false)
    private EmailType type;

    @Column(name = "NAME", nullable = false, updatable = false)
    private String name;

    public EmailType getType() {
        return type;
    }

    public void setType(EmailType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
