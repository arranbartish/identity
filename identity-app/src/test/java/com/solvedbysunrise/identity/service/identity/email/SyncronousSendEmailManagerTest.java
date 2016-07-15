package com.solvedbysunrise.identity.service.identity.email;

import com.solvedbysunrise.identity.data.dao.account.RegisteredEntityDao;
import com.solvedbysunrise.identity.data.dao.email.BasicEmailDao;
import com.solvedbysunrise.identity.data.dao.email.ResetPasswordEmailDao;
import com.solvedbysunrise.identity.data.dto.ApplicationProperties;
import com.solvedbysunrise.identity.data.entity.jpa.account.BasicRegisteredEntity;
import com.solvedbysunrise.identity.data.entity.jpa.email.BasicEmail;
import com.solvedbysunrise.identity.data.entity.jpa.email.ResetPasswordEmail;
import com.solvedbysunrise.identity.service.SyncronousSendEmailManager;
import com.solvedbysunrise.identity.service.ContentKey;
import com.solvedbysunrise.identity.service.ContentManager;
import com.solvedbysunrise.identity.service.EmailService;
import com.solvedbysunrise.identity.service.dtto.ContactUsRequest;
import com.solvedbysunrise.identity.service.dtto.ContentType;
import com.solvedbysunrise.identity.service.id.UniqueIdentifierService;
import com.solvedbysunrise.identity.service.properties.ApplicationPropertiesService;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.google.common.collect.Lists.newArrayList;
import static com.solvedbysunrise.identity.data.entity.jpa.email.PasswordResetResultType.PENDING;
import static java.util.Locale.CANADA_FRENCH;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SyncronousSendEmailManagerTest {

    private static final String AN_ADDRESS = "some@address";
    private static final String A_UUID = "A_UUID";
    private static final String SOME_CONTENT = "SOME_CONTENT";
    private static final Integer FIVE_MINUTES_IN_SECONDS = 300;
    private static final Long USER_ID = 1L;
    private static final String PASSWORD = "PASSWORD";
    private static final String CURRENT_PASSWORD_VERSION = "1";
    private static final Long ENTITY_ID = 1L;
    private static final String SOME_LINK_TEMPLATE = "a_%s_link";

    @Mock
    private EmailService emailService;

    @Mock
    private ContentManager contentManager;

    @Mock
    private UniqueIdentifierService uniqueIdentifierService;

    @Mock
    private ApplicationPropertiesService applicationPropertiesService;

    @Mock
    private BasicEmailDao basicEmailDao;

    @Mock
    private RegisteredEntityDao registeredUserDao;

    @Mock
    private ResetPasswordEmailDao resetPasswordEmailDao;

    @InjectMocks
    private SyncronousSendEmailManager syncronousSendEmailManager;

    @Captor
    private ArgumentCaptor<Date> dateCaptor;

    @Captor
    private ArgumentCaptor<String> guidCapture;

    @Mock
    private ApplicationProperties applicationProperties;

    @Mock
    private ResetPasswordEmail resetPasswordEmail;

    @Test
    public void sendRegistrationActivation_Will_Generate_A_Message_With_A_Guid_When_Given_An_Email() throws Exception {
        BasicRegisteredEntity registeredUser = getRegisteredUser();
        when(registeredUserDao.findOne(ENTITY_ID)).thenReturn(registeredUser);
        when(uniqueIdentifierService.generateUniqueIdentifier()).thenReturn(A_UUID);
        when(applicationPropertiesService.getApplicationProperties()).thenReturn(applicationProperties);
        when(applicationProperties.getEmailActivationLinkStringTemplate()).thenReturn(SOME_LINK_TEMPLATE);
        when(applicationProperties.getEmailDeactivationLinkStringTemplate()).thenReturn(SOME_LINK_TEMPLATE);
        when(applicationProperties.getEmailInBrowserLinkStringTemplate()).thenReturn(SOME_LINK_TEMPLATE);
        when(contentManager.generateContent(anyMap(), any(ContentKey.class), any(ContentType.class), any(Locale.class))).thenReturn(SOME_CONTENT);

        syncronousSendEmailManager.sendRegistrationActivation(ENTITY_ID);

        verify(emailService).sendEmail(guidCapture.capture());

        String guid = guidCapture.getValue();
        assertThat(guid, is(A_UUID));
    }

    @Test
    public void resendAnyOldEmailsThatHaveNotBeenSent_Will_not_send_mails_inside_of_resend_cutoff() throws Exception {
        when(applicationPropertiesService.getApplicationProperties()).thenReturn(applicationProperties);
        when(applicationProperties.getEmailResendTimeInSeconds()).thenReturn(FIVE_MINUTES_IN_SECONDS);
        when(applicationProperties.getEmailResendBatchSize()).thenReturn(10);


        BasicEmail basicEmail = new BasicEmail();
        basicEmail.setCreateDate(DateTime.now().toDate());
        List<BasicEmail> emails = newArrayList(basicEmail);

        when(basicEmailDao.findUnsentEmails((Pageable)notNull())).thenReturn(new PageImpl<>(emails));

        syncronousSendEmailManager.resendAnyOldEmailsThatHaveNotBeenSent();

        verify(emailService, times(0)).sendEmail(anyString());
    }


    @Test
    public void resendAnyOldEmailsThatHaveNotBeenSent_Will_send_mails_outside_of_resend_cutoff() throws Exception {
        when(applicationPropertiesService.getApplicationProperties()).thenReturn(applicationProperties);
        when(applicationProperties.getEmailResendTimeInSeconds()).thenReturn(FIVE_MINUTES_IN_SECONDS);
        when(applicationProperties.getEmailResendBatchSize()).thenReturn(10);

        BasicEmail basicEmail = new BasicEmail();
        basicEmail.setCreateDate(DateTime.now().minusDays(1).toDate());
        basicEmail.setGuid(A_UUID);
        List<BasicEmail> emails = newArrayList(basicEmail);

        when(basicEmailDao.findUnsentEmails((Pageable)notNull())).thenReturn(new PageImpl<>(emails));

        syncronousSendEmailManager.resendAnyOldEmailsThatHaveNotBeenSent();

        verify(emailService, times(1)).sendEmail(A_UUID);
    }

    @Test
    public void sendPasswordReset_Will_Generate_A_Message_With_A_Guid_When_Given_An_Email() throws Exception {
        BasicRegisteredEntity registeredUser = getRegisteredUser();
        when(registeredUserDao.findOne(ENTITY_ID)).thenReturn(registeredUser);
        when(resetPasswordEmailDao.findByToAddressAndResult(registeredUser.getPrimaryEmail(), PENDING)).thenReturn(getEmailList());
        when(uniqueIdentifierService.generateUniqueIdentifier()).thenReturn(A_UUID);
        when(applicationPropertiesService.getApplicationProperties()).thenReturn(applicationProperties);
        when(applicationProperties.getEmailPasswordResetLinkStringTemplate()).thenReturn(SOME_LINK_TEMPLATE);
        when(applicationProperties.getEmailInBrowserLinkStringTemplate()).thenReturn(SOME_LINK_TEMPLATE);
        when(contentManager.generateContent(anyMap(), any(ContentKey.class), any(ContentType.class), any(Locale.class))).thenReturn(SOME_CONTENT);

        syncronousSendEmailManager.sendPasswordReset(ENTITY_ID);

        verify(emailService).sendEmail(guidCapture.capture());
        verify(resetPasswordEmail).invalidate();

        String guid = guidCapture.getValue();
        assertThat(guid, is(A_UUID));
    }


    @Test
    public void sendContactUs_Will_Generate_A_Message_With_A_Guid_When_Given_An_Email() throws Exception {
        BasicRegisteredEntity registeredUser = getRegisteredUser();
        when(registeredUserDao.findOne(ENTITY_ID)).thenReturn(registeredUser);
        when(uniqueIdentifierService.generateUniqueIdentifier()).thenReturn(A_UUID);
        when(applicationPropertiesService.getApplicationProperties()).thenReturn(applicationProperties);
        when(applicationProperties.getEmailPasswordResetLinkStringTemplate()).thenReturn(SOME_LINK_TEMPLATE);
        when(applicationProperties.getEmailInBrowserLinkStringTemplate()).thenReturn(SOME_LINK_TEMPLATE);
        when(applicationProperties.getRootAccountId()).thenReturn(ENTITY_ID);
        when(contentManager.generateContent(anyMap(), any(ContentKey.class), any(ContentType.class), any(Locale.class))).thenReturn(SOME_CONTENT);

        ContactUsRequest contactUsRequest = new ContactUsRequest("Sagar", "sagarshah1983@gmail.com", "I have a question for you.");
        syncronousSendEmailManager.sendContactUsEmail(contactUsRequest);


        verify(emailService).sendEmail(guidCapture.capture());

        String guid = guidCapture.getValue();
        assertThat(guid, is(A_UUID));
    }

    private List<ResetPasswordEmail> getEmailList() {
        List<ResetPasswordEmail> resetPasswordEmails = newArrayList(resetPasswordEmail);
        return resetPasswordEmails;
    }

    private BasicRegisteredEntity getRegisteredUser(){
        BasicRegisteredEntity registeredUser = new BasicRegisteredEntity();
        registeredUser.setId(USER_ID);
        registeredUser.setPrimaryEmail(AN_ADDRESS);
        registeredUser.setPassword(PASSWORD);
        registeredUser.setPasswordVersion(CURRENT_PASSWORD_VERSION);
        registeredUser.setLocale(CANADA_FRENCH);
        return registeredUser;
    }

}
