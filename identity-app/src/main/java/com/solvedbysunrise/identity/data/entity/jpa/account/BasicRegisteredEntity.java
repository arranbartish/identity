package com.solvedbysunrise.identity.data.entity.jpa.account;


import org.hibernate.annotations.Cascade;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static com.solvedbysunrise.identity.data.entity.jpa.account.RegisteredEntity.REGISTERED_ENTITY_TABLE;
import static org.hibernate.annotations.CascadeType.SAVE_UPDATE;

@Entity
@Table(name = REGISTERED_ENTITY_TABLE)
public class BasicRegisteredEntity extends RegisteredEntity {

    @OneToMany(mappedBy = "registeredEntity")
    @Cascade({SAVE_UPDATE})
    @OrderBy("createDate")
    private Set<RegisteredEntityTermsAndConditions> termsAndConditions;

    @OneToMany(mappedBy = "registeredEntity")
    @Cascade({SAVE_UPDATE})
    @OrderBy("createDate")
    private Set<RegisteredEntityRole> roles;

    public Set<RegisteredEntityTermsAndConditions> getTermsAndConditions() {
        return termsAndConditions;
    }

    public void setTermsAndConditions(Set<RegisteredEntityTermsAndConditions> termsAndConditions) {
        this.termsAndConditions = termsAndConditions;
    }

    public void addTermsAndConditions(Set<RegisteredEntityTermsAndConditions> termsAndConditions) {
        for (RegisteredEntityTermsAndConditions termsAndConditionsInstance : termsAndConditions) {
            addTermsAndConditionsInstance(termsAndConditionsInstance);
        }
    }

    public void addTermsAndConditionsInstance(RegisteredEntityTermsAndConditions termsAndConditionsInstance) {
        if(termsAndConditions == null) {
            termsAndConditions = newHashSet();
        }
        termsAndConditionsInstance.setRegisteredEntity(this);
        termsAndConditions.add(termsAndConditionsInstance);
    }

    public Set<RegisteredEntityRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<RegisteredEntityRole> roles) {
        this.roles = roles;
    }

    public void addRoles(Set<RegisteredEntityRole> roles) {
        for (RegisteredEntityRole role : roles) {
            addRole(role);
        }
    }

    public void addRoles(String... roleNames) {
        for (String roleName : roleNames) {
            addRole(roleName);
        }
    }


    public void addRole(String roleName) {
        RegisteredEntityRole registeredEntityRole = new RegisteredEntityRole();
        registeredEntityRole.setRoleName(roleName);
        addRole(registeredEntityRole);
    }

    public void addRole(RegisteredEntityRole role) {
        if(roles == null) {
            roles = newHashSet();
        }
        role.setRegisteredEntity(this);
        roles.add(role);
    }
}
