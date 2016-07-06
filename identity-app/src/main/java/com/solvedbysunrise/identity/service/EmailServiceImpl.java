package com.solvedbysunrise.identity.service;


import com.solvedbysunrise.identity.data.dao.email.BasicEmailDao;
import com.solvedbysunrise.identity.data.dao.email.EmailActivationDao;
import com.solvedbysunrise.identity.data.dao.email.EmailEventDao;
import com.solvedbysunrise.identity.data.dao.email.ResetPasswordEmailDao;
import com.solvedbysunrise.identity.data.dto.EntitySummary;
import com.solvedbysunrise.identity.data.entity.jpa.email.*;
import com.solvedbysunrise.identity.service.dtto.EmailContent;
import com.solvedbysunrise.identity.service.dtto.PreparedEmail;
import com.solvedbysunrise.identity.service.exception.EmailCreationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Locale;

import static com.solvedbysunrise.identity.data.entity.jpa.email.EmailType.RESET_PASSWORD;
import static com.solvedbysunrise.identity.internationalization.LocaleUtil.convertToLocale;
import static com.solvedbysunrise.identity.service.dtto.ContentType.HTML;
import static com.solvedbysunrise.identity.service.dtto.ContentType.TEXT;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.joda.time.DateTime.now;

@Service
@Transactional
public class EmailServiceImpl implements EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

    private final BasicEmailDao basicEmailDao;
    private final EmailActivationDao activationEmailDao;
    private final ResetPasswordEmailDao resetPasswordEmailDao;
    private final SendEmailService sendEmailService;
    private final EmailEventDao emailEventDao;

    @Autowired
    public EmailServiceImpl(final BasicEmailDao basicEmailDao,
                            final EmailActivationDao activationEmailDao,
                            final ResetPasswordEmailDao resetPasswordEmailDao,
                            final SendEmailService sendEmailService,
                            final EmailEventDao emailEventDao) {
        this.basicEmailDao = basicEmailDao;
        this.activationEmailDao = activationEmailDao;
        this.resetPasswordEmailDao = resetPasswordEmailDao;
        this.sendEmailService = sendEmailService;
        this.emailEventDao = emailEventDao;
    }

    @Override
    public void prepareBasicEmailForSending(final String guid, final String htmlContent, final String textContent, final EmailType emailType, EntitySummary entitySummary) {
        BasicEmail basicEmail = createEmailAndApplyCommonEmailInfo(BasicEmail.class, guid, htmlContent, textContent, emailType, entitySummary);
        basicEmailDao.save(basicEmail);
    }

    @Override
    public void prepareActivationEmailForSending(final String guid, final String activationGuid, final String htmlContent, final String textContent, final EmailType emailType, EntitySummary entitySummary) {
        ActivationEmail activationEmail = createEmailAndApplyCommonEmailInfo(ActivationEmail.class, guid, htmlContent, textContent, emailType, entitySummary);
        activationEmail.setActivationGuid(activationGuid);
        activationEmailDao.save(activationEmail);
    }

    @Override
    public void prepareResetPasswordEmailForSending(final String guid, final String passwordResetGuid, final String htmlContent, final String textContent, EntitySummary entitySummary) {
        ResetPasswordEmail resetPasswordEmail = createEmailAndApplyCommonEmailInfo(ResetPasswordEmail.class, guid, htmlContent, textContent, RESET_PASSWORD, entitySummary);
        resetPasswordEmail.setResetPasswordGuid(passwordResetGuid);
        resetPasswordEmailDao.save(resetPasswordEmail);
    }

    @Override
    public void sendEmail(String emailGuid) {
        BasicEmail basicEmail = basicEmailDao.findByGuid(emailGuid);

        PreparedEmail preparedEmail = new PreparedEmail(basicEmail.getGuid(), basicEmail.getHtmlPayload(),
                basicEmail.getTextPayload(),
                basicEmail.getType(), basicEmail.getToAddress());
        basicEmail.setSentDate(now().toDate());
        String emailConfirmationId = sendEmailService.sendMail(preparedEmail);
        basicEmail.setConfirmationId(emailConfirmationId);
        basicEmailDao.save(basicEmail);
    }

    @Override
    public EmailContent getEmailContent(String emailGuid) {
        BasicEmail basicEmail = basicEmailDao.findByGuid(emailGuid);
        final boolean isHtmlContentAvailable = isNotBlank(basicEmail.getHtmlPayload());

        Locale locale = convertToLocale(basicEmail.getLanguageCode(), basicEmail.getCountryCode());
        return (isHtmlContentAvailable)? new EmailContent(basicEmail.getHtmlPayload(), HTML, locale):
                                         new EmailContent(basicEmail.getTextPayload(), TEXT, locale);
    }

    @Override
    public void handleEmailEvent(final String guid, final String confirmationid, final String event, final Date eventTimestamp) {
        BasicEmail basicEmail = null;
        if(isNotBlank(guid)){
            basicEmail = basicEmailDao.findByGuid(guid);
        } else if (isNotBlank(confirmationid)) {
            basicEmail = basicEmailDao.findByConfirmationId(confirmationid);
        }

        // we have a chance to fill in some data if we missed it for some reason on the way out
        if(isBlank(basicEmail.getConfirmationId())) {
            basicEmail.setConfirmationId(confirmationid);
        }
        if(basicEmail.getSentDate() == null) {
            basicEmail.setSentDate(eventTimestamp);
        }

        EmailEvent emailEvent = new EmailEvent();
        emailEvent.setRemoteDate(new Timestamp(eventTimestamp.getTime()));
        emailEvent.setEventType(event);
        emailEvent.setEmailId(basicEmail.getId());

        emailEventDao.save(emailEvent);
    }

    private <T extends Email> T createEmailAndApplyCommonEmailInfo(final Class<T> emailClass, final String guid, final String htmlContent, final String textContent, final EmailType emailType, final EntitySummary entitySummary) {
        T email = null;
        try {
            email = emailClass.newInstance();
            email.setGuid(guid);
            email.setToAddress(entitySummary.getPrimaryContactEmail());
            email.setHtmlPayload(htmlContent);
            email.setTextPayload(textContent);
            email.setType(emailType);
            email.setEntityId(entitySummary.getEntityId());
            email.setLocale(entitySummary.getLocale());
        } catch (Exception e) {
            String msg = format("Failed to create email to [%s]", entitySummary.getPrimaryContactEmail());
            LOGGER.error(msg, e);
            throw new EmailCreationException(msg, e);
        }
        return email;
    }

}
