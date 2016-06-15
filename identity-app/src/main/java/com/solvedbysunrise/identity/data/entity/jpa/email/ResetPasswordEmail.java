package com.solvedbysunrise.identity.data.entity.jpa.email;

import javax.persistence.*;
import java.sql.Timestamp;

import static com.solvedbysunrise.identity.data.entity.jpa.email.PasswordResetResultType.EXPIRED;
import static org.joda.time.DateTime.now;

@Entity
@Table(name = Email.EMAIL_TABLE)
@SecondaryTables({
        @SecondaryTable(name=ResetPasswordEmail.EMAIL_RESET_PASSWORD_TABLE, pkJoinColumns={
                @PrimaryKeyJoinColumn(name=BasicEmail.EMAIL_ID, referencedColumnName=Email.ID) }),
        @SecondaryTable(name=Email.EMAIL_CONTENT_TABLE, pkJoinColumns={
                @PrimaryKeyJoinColumn(name=BasicEmail.EMAIL_ID, referencedColumnName=Email.ID) })
})
public class ResetPasswordEmail extends Email{

    private static final long serialVersionUID = -983723983749824L;

    static final String EMAIL_RESET_PASSWORD_TABLE = "EMAIL_RESET_PASSWORD";

    static final String RESET_PASSWORD_GUID = "GUID";
    static final String RESULT = "RESULT";
    static final String RESULT_DATE = "RESULT_DATE";

    @Column(name = RESET_PASSWORD_GUID, table = EMAIL_RESET_PASSWORD_TABLE, nullable = false, updatable = false)
    private String resetPasswordGuid;

    @Enumerated(EnumType.STRING)
    @Column(name = RESULT, table = EMAIL_RESET_PASSWORD_TABLE, nullable = false)
    private PasswordResetResultType result = PasswordResetResultType.PENDING;

    @Column(name = RESULT_DATE, table = EMAIL_RESET_PASSWORD_TABLE)
    private Timestamp resultDate;

    @Basic(fetch=FetchType.LAZY)
    @Lob
    @Column(name = TEXT_PAYLOAD, table = EMAIL_CONTENT_TABLE, nullable = false, updatable = false)
    private String textPayload;

    @Basic(fetch=FetchType.LAZY)
    @Lob
    @Column(name = HTML_PAYLOAD, table = EMAIL_CONTENT_TABLE, nullable = false, updatable = false)
    private String htmlPayload;

    public String getResetPasswordGuid() {
        return resetPasswordGuid;
    }

    public void setResetPasswordGuid(String resetPasswordGuid) {
        this.resetPasswordGuid = resetPasswordGuid;
    }

    public PasswordResetResultType getResult() {
        return result;
    }

    public void setResult(PasswordResetResultType result) {
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

    public void invalidate(){
        setResult(EXPIRED);
        setResultDate(new Timestamp(now().getMillis()));
    }
}
