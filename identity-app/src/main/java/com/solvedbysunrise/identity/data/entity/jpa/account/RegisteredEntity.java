package com.solvedbysunrise.identity.data.entity.jpa.account;

import com.solvedbysunrise.identity.data.dao.account.ActivationState;
import com.solvedbysunrise.identity.data.entity.jpa.ReflectiveEntity;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.Locale;

import static com.solvedbysunrise.identity.data.dao.account.ActivationState.*;
import static com.solvedbysunrise.identity.internationalization.LocaleUtil.convertToLocale;
import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.TIMESTAMP;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class RegisteredEntity extends ReflectiveEntity {

    public static final String REGISTERED_ENTITY_TABLE = "REGISTERED_ENTITY";

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USERNAME", nullable = false)
    private String username;

    @Column(name = "PRIMARY_EMAIL", nullable = false)
    private String primaryEmail;

    @Column(name = "DISPLAY_NAME")
    private String displayName;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "PASSWORD_VERSION", nullable = false)
    private String passwordVersion;

    @Column(name = "LANGUAGE_CODE", nullable = false)
    private String languageCode;

    @Column(name = "COUNTRY_CODE", nullable = false)
    private String countryCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "ACTIVATION_STATE", nullable = false)
    private ActivationState activationState = PENDING_ACTIVATION;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPrimaryEmail() {
        return primaryEmail;
    }

    public void setPrimaryEmail(String primaryEmail) {
        this.primaryEmail = primaryEmail;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordVersion() {
        return passwordVersion;
    }

    public void setPasswordVersion(String passwordVersion) {
        this.passwordVersion = passwordVersion;
    }

    public ActivationState getActivationState() {
        return activationState;
    }

    public void setActivationState(ActivationState activationState) {
        this.activationState = activationState;
    }

    public void activate(){
        setActivationState(ACTIVATED);
    }

    public void deactivate() {
        setActivationState(DEACTIVATED);
    }

    public void inactive() {
        setActivationState(INACTIVE);
    }

    public boolean isPendingActivation(){
        return (PENDING_ACTIVATION == getActivationState());
    }

    public boolean isActive(){
        return (ACTIVATED == getActivationState());
    }

    public boolean isNotActive(){
        return !isActive();
    }

    public boolean isNotPendingActivation(){
        return !isPendingActivation();
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    @Transient
    public void setLocale(final Locale locale) {
        setLanguageCode(locale.getLanguage());
        setCountryCode(locale.getCountry());
    }

    @Transient
    public Locale getLocale() {
        return convertToLocale(getLanguageCode(), getCountryCode());
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
