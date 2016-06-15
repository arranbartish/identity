package com.solvedbysunrise.identity.data.entity.jpa.email;

import javax.persistence.*;

@Entity
@Table(name = BasicEmail.EMAIL_TABLE)
@SecondaryTables({
        @SecondaryTable(name=Email.EMAIL_CONTENT_TABLE, pkJoinColumns={
                @PrimaryKeyJoinColumn(name=BasicEmail.EMAIL_ID, referencedColumnName=Email.ID) })
})
public class BasicEmail extends Email {

    private static final long serialVersionUID = -127635485667L;

    static final String EMAIL_ID = "EMAIL_ID";

    @Basic(fetch=FetchType.LAZY)
    @Lob
    @Column(name = TEXT_PAYLOAD, table = EMAIL_CONTENT_TABLE, nullable = false, updatable = false)
    private String textPayload;

    @Basic(fetch=FetchType.LAZY)
    @Lob
    @Column(name = HTML_PAYLOAD, table = EMAIL_CONTENT_TABLE, nullable = false, updatable = false)
    private String htmlPayload;

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
}
