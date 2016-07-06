package com.solvedbysunrise.identity.service;

import com.google.common.collect.Maps;
import com.solvedbysunrise.identity.data.dao.account.RegisteredEntityDao;
import com.solvedbysunrise.identity.data.dao.email.BasicEmailDao;
import com.solvedbysunrise.identity.data.dao.email.ResetPasswordEmailDao;
import com.solvedbysunrise.identity.data.dto.ApplicationProperties;
import com.solvedbysunrise.identity.data.dto.EntitySummary;
import com.solvedbysunrise.identity.data.entity.jpa.account.RegisteredEntity;
import com.solvedbysunrise.identity.data.entity.jpa.email.BasicEmail;
import com.solvedbysunrise.identity.data.entity.jpa.email.PasswordResetResultType;
import com.solvedbysunrise.identity.data.entity.jpa.email.ResetPasswordEmail;
import com.solvedbysunrise.identity.service.dtto.ContactUsRequest;
import com.solvedbysunrise.identity.service.id.UniqueIdentifierService;
import com.solvedbysunrise.identity.service.properties.ApplicationPropertiesService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static com.google.common.collect.Maps.newHashMap;
import static com.solvedbysunrise.identity.data.dto.EntitySummary.createEntitySummary;
import static com.solvedbysunrise.identity.data.entity.jpa.email.EmailType.CONTACT_US;
import static com.solvedbysunrise.identity.data.entity.jpa.email.EmailType.REGISTRATION_ACTIVATION;
import static com.solvedbysunrise.identity.service.ContentKey.*;
import static com.solvedbysunrise.identity.service.dtto.ContentType.HTML;
import static com.solvedbysunrise.identity.service.dtto.ContentType.TEXT;
import static java.lang.String.format;
import static org.joda.time.DateTime.now;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.data.domain.Sort.Direction.ASC;

@Service
@Transactional
public class AsyncronousSendEmailManager implements SendEmailManager {

    static final Logger LOGGER = getLogger(AsyncronousSendEmailManager.class);
    private static final int FIRST_PAGE = 0;
    private static final boolean PARALLEL = true;

    private final EmailService emailService;

    private final ContentManager contentManager;

    private final UniqueIdentifierService uniqueIdentifierService;

    private final ApplicationPropertiesService applicationPropertiesService;

    private final BasicEmailDao basicEmailDao;

    private final ResetPasswordEmailDao resetPasswordEmailDao;

    private final RegisteredEntityDao registeredEntityDao;

    @Autowired
    public AsyncronousSendEmailManager(final EmailService emailService,
                                       final ContentManager contentManager,
                                       final UniqueIdentifierService uniqueIdentifierService,
                                       final ApplicationPropertiesService applicationPropertiesService,
                                       final BasicEmailDao basicEmailDao,
                                       final ResetPasswordEmailDao resetPasswordEmailDao,
                                       final RegisteredEntityDao registeredEntityDao) {
        this.emailService = emailService;
        this.contentManager = contentManager;
        this.uniqueIdentifierService = uniqueIdentifierService;
        this.applicationPropertiesService = applicationPropertiesService;
        this.basicEmailDao = basicEmailDao;
        this.resetPasswordEmailDao = resetPasswordEmailDao;
        this.registeredEntityDao = registeredEntityDao;
    }

    @Override
    @Async
    public void sendPasswordReset(final Long accountId) {

        RegisteredEntity registeredUser = registeredEntityDao.findOne(accountId);
        expireAnyPreviousResetEmails(registeredUser.getPrimaryEmail());

        String emailGuid = uniqueIdentifierService.generateUniqueIdentifier();
        String resetPasswordGuid = uniqueIdentifierService.generateUniqueIdentifier();

        Map<String, Object> values = newHashMap();
        values = prepareStandardEmailTemplateProperties(values, emailGuid, registeredUser);
        values = prepareResetPasswordEmailTemplateProperties(values, resetPasswordGuid);

        final String htmlContent = contentManager.generateContent(values, RESET_PASSWORD_EMAIL_CONTENT, HTML, registeredUser.getLanguageCode());
        final String textContent = contentManager.generateContent(values, RESET_PASSWORD_EMAIL_CONTENT, TEXT, registeredUser.getLanguageCode());

        final EntitySummary
                entitySummary = createEntitySummary(registeredUser.getPrimaryEmail(),
                registeredUser.getId(),
                registeredUser.getLocale());

        emailService.prepareResetPasswordEmailForSending(emailGuid, resetPasswordGuid,
                htmlContent, textContent, entitySummary);
        emailService.sendEmail(emailGuid);
    }

    @Override
    @Async
    public void sendRegistrationActivation(final Long accountId) {
        RegisteredEntity registeredUser = registeredEntityDao.findOne(accountId);
        final String emailGuid = uniqueIdentifierService.generateUniqueIdentifier();
        final String activationGuid = uniqueIdentifierService.generateUniqueIdentifier();

        Map<String, Object> values = Maps.newHashMap();
        values = prepareStandardEmailTemplateProperties(values, emailGuid, registeredUser);
        values = prepareActivationEmailTemplateProperties(values, activationGuid);

        final String htmlContent = contentManager.generateContent(values, ACTIVATION_EMAIL_CONTENT, HTML, registeredUser.getLanguageCode());
        final String textContent = contentManager.generateContent(values, ACTIVATION_EMAIL_CONTENT, TEXT, registeredUser.getLanguageCode());

        final EntitySummary entitySummary = createEntitySummary(registeredUser.getPrimaryEmail(),
                registeredUser.getId(),
                registeredUser.getLocale());

        emailService.prepareActivationEmailForSending(emailGuid, activationGuid,
                htmlContent, textContent, REGISTRATION_ACTIVATION, entitySummary);

        emailService.sendEmail(emailGuid);
    }

    @Override
    @Async
    public void resendAnyOldEmailsThatHaveNotBeenSent() {
        LOGGER.debug("Starting: resendAnyOldEmailsThatHaveNotBeenSent");

        ApplicationProperties applicationProperties = applicationPropertiesService.getApplicationProperties();
        Date emailsProbablyNotSent = now().minusSeconds(applicationProperties.getEmailResendTimeInSeconds()).toDate();

        Pageable pageable = new PageRequest(FIRST_PAGE, applicationProperties.getEmailResendBatchSize(), ASC, "createDate");
        Page<BasicEmail> unsentEmails = basicEmailDao.findUnsentEmails(pageable);


        try (Stream<BasicEmail> stream = StreamSupport.stream(unsentEmails.spliterator(), PARALLEL)) {
            stream
                    .filter(basicEmail -> emailsProbablyNotSent.after(basicEmail.getCreateDate()))
                    .forEach(basicEmail -> emailService.sendEmail(basicEmail.getGuid()));
        }
        LOGGER.debug("Completed: resendAnyOldEmailsThatHaveNotBeenSent");
    }

    @Override
    @Async
    public void sendContactUsEmail(final ContactUsRequest contactUsRequest) {
        final String emailGuid = uniqueIdentifierService.generateUniqueIdentifier();
        ApplicationProperties applicationProperties = applicationPropertiesService.getApplicationProperties();
        RegisteredEntity registeredUser = registeredEntityDao.findOne(applicationProperties.getRootAccountId());

        Map<String, Object> values = Maps.newHashMap();
        values.put("name", contactUsRequest.getName());
        values.put("email", contactUsRequest.getEmail());
        values.put("message", contactUsRequest.getMessage());
        values = prepareStandardEmailTemplateProperties(values, emailGuid, registeredUser);

        final String htmlContent = contentManager.generateContent(values, CONTACT_US_EMAIL_CONTENT, HTML, registeredUser.getLanguageCode());
        final String textContent = contentManager.generateContent(values, CONTACT_US_EMAIL_CONTENT, TEXT, registeredUser.getLanguageCode());

        final EntitySummary entitySummary = createEntitySummary(registeredUser.getPrimaryEmail(),
                registeredUser.getId(),
                registeredUser.getLocale());

        emailService.prepareBasicEmailForSending(emailGuid, htmlContent, textContent, CONTACT_US, entitySummary);
        emailService.sendEmail(emailGuid);
    }

    private void expireAnyPreviousResetEmails(final String toAddress) {
        Collection<ResetPasswordEmail> currentlyActiveResetEmails = resetPasswordEmailDao.findByToAddressAndResult(toAddress, PasswordResetResultType.PENDING);
        currentlyActiveResetEmails.parallelStream().forEach(ResetPasswordEmail::invalidate);
        resetPasswordEmailDao.save(currentlyActiveResetEmails);
    }

    private Map<String, Object> prepareStandardEmailTemplateProperties(final Map<String, Object> values,
                                                                       final String emailGuid,
                                                                       final RegisteredEntity registeredUser) {
        ApplicationProperties applicationProperties = applicationPropertiesService.getApplicationProperties();
        final String viewInBrowserLink = format(applicationProperties.getEmailInBrowserLinkStringTemplate(), emailGuid);
        values.put("viewInBrowserLink", viewInBrowserLink);
        values.put("displayName", registeredUser.getDisplayName());
        return values;
    }

    private Map<String, Object> prepareActivationEmailTemplateProperties(final Map<String, Object> values,
                                                                         final String activationGuid) {
        ApplicationProperties applicationProperties = applicationPropertiesService.getApplicationProperties();
        final String activationLink = format(applicationProperties.getEmailActivationLinkStringTemplate(), activationGuid);
        final String deactivationLink = format(applicationProperties.getEmailDeactivationLinkStringTemplate(), activationGuid);
        values.put("activationLink", activationLink);
        values.put("notMeLink", deactivationLink);
        return values;
    }

    private Map<String, Object> prepareResetPasswordEmailTemplateProperties(final Map<String, Object> values,
                                                                            final String resetPasswordGuid) {
        ApplicationProperties applicationProperties = applicationPropertiesService.getApplicationProperties();
        final String resetPasswordLink = format(applicationProperties.getEmailPasswordResetLinkStringTemplate(), resetPasswordGuid);
        values.put("resetPasswordLink", resetPasswordLink);
        return values;
    }
}
