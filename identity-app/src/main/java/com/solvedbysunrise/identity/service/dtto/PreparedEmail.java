package com.solvedbysunrise.identity.service.dtto;

import com.solvedbysunrise.bean.ReflectiveBean;
import com.solvedbysunrise.identity.data.entity.jpa.email.EmailType;

public class PreparedEmail extends ReflectiveBean {

    private final String guid;   // this may need to be a map of parameters one day

    private final String htmlContent;

    private final String textContent;

    private final EmailType type;

    private final String toAddress;

    public PreparedEmail(final String guid, final String htmlContent, final String textContent, final EmailType type, final String toAddress) {
        this.htmlContent = htmlContent;
        this.textContent = textContent;
        this.type = type;
        this.toAddress = toAddress;
        this.guid = guid;
    }

    public EmailType getType() {
        return type;
    }

    public String getToAddress() {
        return toAddress;
    }

    public String getGuid() {
        return guid;
    }

    public String getTextContent() {
        return textContent;
    }

    public String getHtmlContent() {
        return htmlContent;
    }
}
