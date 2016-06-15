package com.solvedbysunrise.identity.data.entity.jpa.user;

import com.solvedbysunrise.identity.data.entity.jpa.ReflectiveEntity;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "AUTHORITY_ROLE")
public class AuthorityRole extends ReflectiveEntity {

	private static final long serialVersionUID = -633318194474279648L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	@Column(name = "IS_ACTIVE", nullable = false)
	private Boolean isActive;

	@Column(name = "ROLE", nullable = false)
	private String role;

	@Column(name = "AUTHORITY", nullable = false)
	private String authority;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Boolean isActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
}
