package com.solvedbysunrise.identity.data.entity.jpa.account;

import com.solvedbysunrise.identity.data.entity.jpa.ReflectiveEntity;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
@Table(name = "REGISTERED_ENTITY_ROLE")
@EntityListeners(AuditingEntityListener.class)
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

	@Column(name = "CREATE_DATE", nullable = false, updatable = false)
	@CreatedDate
	@Temporal(TIMESTAMP)
	private Date createDate;

	@Column(name = "UPDATE_DATE")
	@LastModifiedDate
	@Temporal(TIMESTAMP)
	private Date updateDate;

	@Column(name = "UPDATE_BY", nullable = false)
	@CreatedBy
	@LastModifiedBy
	private String updateBy;

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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
}