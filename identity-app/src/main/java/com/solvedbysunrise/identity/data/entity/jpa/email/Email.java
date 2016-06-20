package com.solvedbysunrise.identity.data.entity.jpa.email;


import com.solvedbysunrise.identity.data.entity.jpa.ReflectiveEntity;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.solvedbysunrise.identity.internationalization.LocaleUtil.convertToLocale;
import static javax.persistence.GenerationType.IDENTITY;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Email extends ReflectiveEntity {

    static final String EMAIL_TABLE = "EMAIL";
    static final String EMAIL_CONTENT_TABLE = "EMAIL_CONTENT";

    static final String ID = "ID";
    static final String ENTITY_ID = "ENTITY_ID";
    static final String GUID = "GUID";
    static final String TYPE = "TYPE";
    static final String TO_ADDRESS = "TO_ADDRESS";
    static final String CONFIRMATION_ID = "CONFIRMATION_ID";
    static final String SENT_DATE = "SENT_DATE";
    static final String TEXT_PAYLOAD = "TEXT_PAYLOAD";
    static final String HTML_PAYLOAD = "HTML_PAYLOAD";
    static final String LANGUAGE_CODE = "LANGUAGE_CODE";
    static final String COUNTRY_CODE = "COUNTRY_CODE";

    private static final long serialVersionUID = -8836773247883L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = ID)
    private Long id;

    @Column(name = ENTITY_ID, nullable = false, updatable = false)
    private Long entityId;

    @Column(name = GUID, nullable = false, updatable = false)
    private String guid;

    @Enumerated(EnumType.STRING)
    @Column(name = TYPE, nullable = false, updatable = false)
    private EmailType type;

    @Column(name = TO_ADDRESS, nullable = false, updatable = false)
    private String toAddress;

    @Column(name = CONFIRMATION_ID, nullable = true, updatable = true)
    private String confirmationId;

    @Column(name = SENT_DATE, nullable = true, updatable = true)
    private Date sentDate;

    @Column(name = LANGUAGE_CODE, nullable = true, updatable = true)
    private String languageCode;

    @Column(name = COUNTRY_CODE, nullable = true, updatable = true)
    private String countryCode;

    @Column(name = "CREATE_DATE", nullable = false, updatable = false)
    @CreatedDate
    private Calendar createDate;

    @Column(name = "UPDATE_DATE")
    @LastModifiedDate
    private Calendar updateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public EmailType getType() {
        return type;
    }

    public void setType(EmailType type) {
        this.type = type;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public String getConfirmationId() {
        return confirmationId;
    }

    public void setConfirmationId(String confirmationId) {
        this.confirmationId = confirmationId;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Calendar getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Calendar createDate) {
        this.createDate = createDate;
    }

    public Calendar getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Calendar updateDate) {
        this.updateDate = updateDate;
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

    abstract public void setTextPayload(final String textPayload);

    abstract public String getTextPayload();

    abstract public void setHtmlPayload(final String htmlPayload);

    abstract public String getHtmlPayload();

}
