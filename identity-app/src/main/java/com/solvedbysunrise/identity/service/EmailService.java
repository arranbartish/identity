package com.solvedbysunrise.identity.service;

import com.solvedbysunrise.identity.data.dto.EntitySummary;
import com.solvedbysunrise.identity.data.entity.jpa.email.EmailType;
import com.solvedbysunrise.identity.service.dtto.EmailContent;

import java.util.Date;

public interface EmailService {
    void prepareBasicEmailForSending(String guid, String htmlContent, String textContent, EmailType emailType, EntitySummary entitySummary);

    void prepareActivationEmailForSending(String guid, String activationGuid, String htmlContent, String textContent, EmailType emailType, EntitySummary entitySummary);

    void prepareResetPasswordEmailForSending(String guid, String passwordResetGuid, String htmlContent, String textContent, EntitySummary entitySummary);

    void sendEmail(String emailGuid);

    EmailContent getEmailContent(String emailGuid);

    void handleEmailEvent(String guid, String confirmationid, String event, Date eventTimestamp);
}
