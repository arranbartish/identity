package com.solvedbysunrise.identity.service.dtto;

import com.solvedbysunrise.bean.ReflectiveBean;

import java.util.Locale;

public class EmailContent extends ReflectiveBean {

    private final String content;
    private final ContentType contentType;
    private final Locale locale;

    public EmailContent(final String content, final ContentType contentType, final Locale locale) {
        this.content = content;
        this.contentType = contentType;
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public String getContent() {
        return content;
    }
}
