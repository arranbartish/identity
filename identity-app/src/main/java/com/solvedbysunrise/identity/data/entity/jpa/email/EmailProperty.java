package com.solvedbysunrise.identity.data.entity.jpa.email;

import com.solvedbysunrise.identity.data.entity.jpa.ReflectiveEntity;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "EMAIL_PROPERTY")
public class EmailProperty extends ReflectiveEntity{

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "EMAIL_TYPE", nullable = false, updatable = false)
    private EmailType type;

    @Column(name = "NAME", nullable = false, updatable = false)
    private String name;

    @Column(name = "VALUE", nullable = false, updatable = false)
    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
