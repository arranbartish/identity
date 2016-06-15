package com.solvedbysunrise.identity.data.entity.jpa.email;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

import static com.solvedbysunrise.identity.data.entity.jpa.email.ActivationResultType.PENDING;

@Entity
@Table(name = Email.EMAIL_TABLE)
@SecondaryTables({
        @SecondaryTable(name=ActivationEmail.EMAIL_ACTIVATION_TABLE, pkJoinColumns={
                @PrimaryKeyJoinColumn(name=BasicEmail.EMAIL_ID, referencedColumnName=Email.ID) }),
        @SecondaryTable(name=Email.EMAIL_CONTENT_TABLE, pkJoinColumns={
                @PrimaryKeyJoinColumn(name=BasicEmail.EMAIL_ID, referencedColumnName=Email.ID) })
})
public class ActivationEmail extends Email {

    private static final long serialVersionUID = -127635485667L;

    static final String EMAIL_ACTIVATION_TABLE = "EMAIL_ACTIVATION";

    static final String ACTIVATION_GUID = "GUID";
    static final String RESULT = "RESULT";
    static final String RESULT_DATE = "RESULT_DATE";

    @Column(name = ACTIVATION_GUID, table = EMAIL_ACTIVATION_TABLE, nullable = false, updatable = false)
    private String activationGuid;

    @Enumerated(EnumType.STRING)
    @Column(name = RESULT, table = EMAIL_ACTIVATION_TABLE, nullable = false)
    private ActivationResultType result = ActivationResultType.PENDING;

    @Column(name = RESULT_DATE, table = EMAIL_ACTIVATION_TABLE)
    private Timestamp resultDate;

    @Basic(fetch=FetchType.LAZY)
    @Lob
    @Column(name = TEXT_PAYLOAD, table = EMAIL_CONTENT_TABLE, nullable = false, updatable = false)
    private String textPayload;

    @Basic(fetch=FetchType.LAZY)
    @Lob
    @Column(name = HTML_PAYLOAD, table = EMAIL_CONTENT_TABLE, nullable = false, updatable = false)
    private String htmlPayload;

    public String getActivationGuid() {
        return activationGuid;
    }

    public void setActivationGuid(String activationGuid) {
        this.activationGuid = activationGuid;
    }

    public ActivationResultType getResult() {
        return result;
    }

    public void setResult(ActivationResultType result) {
        this.result = result;
    }

    public Timestamp getResultDate() {
        return resultDate;
    }

    public void setResultDate(Timestamp resultDate) {
        this.resultDate = resultDate;
    }

    @Override
    public String getTextPayload() {
        return textPayload;
    }

    @Override
    public void setTextPayload(String textPayload) {
        this.textPayload = textPayload;
    }

    @Override
    public String getHtmlPayload() {
        return htmlPayload;
    }

    @Override
    public void setHtmlPayload(String htmlPayload) {
        this.htmlPayload = htmlPayload;
    }

    public boolean isPending() {
        return (getResult() == PENDING);
    }

    public boolean isNotPending() {
        return !isPending();
    }
}
