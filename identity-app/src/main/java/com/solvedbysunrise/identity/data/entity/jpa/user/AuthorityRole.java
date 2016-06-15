package com.solvedbysunrise.identity.data.entity.jpa.user;

import com.solvedbysunrise.identity.data.entity.jpa.ReflectiveEntity;

import javax.persistence.*;

@Entity
@Table(name = "AUTHORITY_ROLE")
public class AuthorityRole extends ReflectiveEntity {

	private static final long serialVersionUID = -633318194474279648L;

	@EmbeddedId
	private AuthorityRoleId authorityRoleId;

	@Column(name = "IS_ACTIVE", nullable = false)
	private Boolean isActive;

	public AuthorityRoleId getAuthorityRoleId() {
		return authorityRoleId;
	}

	public void setAuthorityRoleId(AuthorityRoleId authorityRoleId) {
		this.authorityRoleId = authorityRoleId;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
}
