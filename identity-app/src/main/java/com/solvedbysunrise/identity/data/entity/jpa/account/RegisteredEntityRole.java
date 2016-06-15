package com.solvedbysunrise.identity.data.entity.jpa.account;

import com.solvedbysunrise.identity.data.entity.jpa.ReflectiveEntity;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "REGISTERED_ENTITY_ROLE")
public class RegisteredEntityRole extends ReflectiveEntity {

	private static final long serialVersionUID = -8730253660111049487L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID")
	private Long id;
	
    @ManyToOne
    @JoinColumn(name = "ENTITY_ID", nullable = false)
	private BasicRegisteredEntity registeredEntity;
	
	@Column(name = "ROLE_NAME", nullable = false)
	private String roleName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    public BasicRegisteredEntity getRegisteredEntity() {
        return registeredEntity;
    }

    public void setRegisteredEntity(BasicRegisteredEntity registeredEntity) {
        this.registeredEntity = registeredEntity;
    }

    public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}