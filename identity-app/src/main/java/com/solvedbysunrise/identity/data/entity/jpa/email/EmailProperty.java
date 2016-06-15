package com.solvedbysunrise.identity.data.entity.jpa.email;

import com.solvedbysunrise.identity.data.entity.jpa.ReflectiveEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "EMAIL_PROPERTY")
public class EmailProperty extends ReflectiveEntity{

    @EmbeddedId
    private EmailPropertyId emailPropertyId;

    @Column(name = "VALUE", nullable = false, updatable = false)
    private String value;

    public EmailType getType() {
        return emailPropertyId.getType();
    }

    public String getName() {
        return emailPropertyId.getName();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public EmailPropertyId getEmailPropertyId() {
        return emailPropertyId;
    }

    public void setEmailPropertyId(EmailPropertyId emailPropertyId) {
        this.emailPropertyId = emailPropertyId;
    }
}
