package com.solvedbysunrise.identity.data.entity.jpa.account;

import com.solvedbysunrise.identity.data.entity.jpa.ReflectiveEntity;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "REGISTERED_ENTITY_TERMS_AND_CONDITIONS")
public class RegisteredEntityTermsAndConditions extends ReflectiveEntity {

	private static final long serialVersionUID = 7905346269567875257L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID")
	private Long id;
	
    @ManyToOne
    @JoinColumn(name = "ENTITY_ID", nullable = false)
	private BasicRegisteredEntity registeredEntity;
	
	@Column(name = "VERSION", nullable = false)
	private Integer version;

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

    public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}
