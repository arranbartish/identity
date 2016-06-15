package com.solvedbysunrise.identity.data.entity.jpa.user;

import com.solvedbysunrise.identity.data.entity.jpa.account.RegisteredEntity;

import javax.persistence.*;
import java.util.Date;

import static com.solvedbysunrise.identity.data.entity.jpa.account.RegisteredEntity.REGISTERED_ENTITY_TABLE;

@Entity
@Table(name = REGISTERED_ENTITY_TABLE)
@SecondaryTables({
        @SecondaryTable(name= RegisteredUser.REGISTERED_USER_TABLE, pkJoinColumns={
                @PrimaryKeyJoinColumn(name="ENTITY_ID", referencedColumnName="ID") })
})
public class RegisteredUser extends RegisteredEntity {

	private static final long serialVersionUID = -219413267147873772L;

    static final String REGISTERED_USER_TABLE = "REGISTERED_USER";
	
	@Column(name = "GIVEN_NAMES", table = REGISTERED_USER_TABLE, nullable = false)
	private String givenNames;
	
	@Column(name = "FAMILY_NAME", table = REGISTERED_USER_TABLE, nullable = false)
	private String familyName;
	
	@Column(name = "DATE_OF_BIRTH", table = REGISTERED_USER_TABLE, nullable = false)
	private Date dateOfBirth;
	
	@Column(name = "PRIMARY_PHONE", table = REGISTERED_USER_TABLE)
	private String primaryPhone;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "PRIMARY_PHONE_TYPE", table = REGISTERED_USER_TABLE)
	private PhoneType primaryPhoneType;
	
	@Column(name = "RESIDENCE_COUNTRY_CODE", table = REGISTERED_USER_TABLE, nullable = false)
	private String residenceCountryCode;
	
	public String getGivenNames() {
		return givenNames;
	}

	public void setGivenNames(String givenNames) {
		this.givenNames = givenNames;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPrimaryPhone() {
		return primaryPhone;
	}

	public void setPrimaryPhone(String primaryPhone) {
		this.primaryPhone = primaryPhone;
	}

	public PhoneType getPrimaryPhoneType() {
		return primaryPhoneType;
	}

	public void setPrimaryPhoneType(PhoneType primaryPhoneType) {
		this.primaryPhoneType = primaryPhoneType;
	}

	public String getResidenceCountryCode() {
		return residenceCountryCode;
	}

	public void setResidenceCountryCode(String residenceCountryCode) {
		this.residenceCountryCode = residenceCountryCode;
	}

}
