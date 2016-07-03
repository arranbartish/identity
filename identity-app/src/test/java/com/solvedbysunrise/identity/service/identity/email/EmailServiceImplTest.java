package com.solvedbysunrise.identity.service.identity.email;

import com.solvedbysunrise.identity.data.dao.email.BasicEmailDao;
import com.solvedbysunrise.identity.data.dao.email.EmailActivationDao;
import com.solvedbysunrise.identity.data.dao.email.EmailEventDao;
import com.solvedbysunrise.identity.data.dto.EntitySummary;
import com.solvedbysunrise.identity.data.entity.jpa.email.ActivationEmail;
import com.solvedbysunrise.identity.data.entity.jpa.email.BasicEmail;
import com.solvedbysunrise.identity.data.entity.jpa.email.EmailEvent;
import com.solvedbysunrise.identity.service.EmailServiceImpl;
import com.solvedbysunrise.identity.service.SendEmailService;
import com.solvedbysunrise.identity.service.dtto.EmailContent;
import com.solvedbysunrise.identity.service.dtto.PreparedEmail;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Timestamp;

import static com.solvedbysunrise.identity.data.dto.EntitySummary.createEntitySummary;
import static com.solvedbysunrise.identity.data.entity.jpa.email.EmailType.REGISTRATION_ACTIVATION;
import static com.solvedbysunrise.identity.service.dtto.ContentType.HTML;
import static com.solvedbysunrise.identity.service.dtto.ContentType.TEXT;
import static java.util.Locale.CANADA_FRENCH;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.joda.time.DateTime.now;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceImplTest {

    private static final String SOME_ADDRESS = "SOME_ADDRESS";
    private static final String SOME_CONTENT = "SOME_CONTENT";
    private static final String SOME_GUID = "SOME_GUID";
    private static final String SOME_ACTIVATION_GUID = "SOME_ACTIVATION_GUID";
    private static final String CONFIRMATION_ID = "CONFIRMATION_ID";
    private static final String EVENT = "EVENT";
    private static final Long EMAIL_ID = 1L;
    private static final Long ACCOUNT_ID = 1L;

    @Mock
    private BasicEmailDao basicEmailDao;

    @Mock
    private EmailActivationDao activationEmailDao;

    @Mock
    private SendEmailService sendEmailService;

    @Mock
    private EmailEventDao emailEventDao;

    @InjectMocks
    private EmailServiceImpl emailService;

    @Captor
    private ArgumentCaptor<EmailEvent> emailEventCaptor;

    @Test
    public void prepareBasicEmailForSending_Will_Create_And_Save_BasicEmail_When_Called() throws Exception {
        emailService.prepareBasicEmailForSending(SOME_GUID, SOME_CONTENT,
                SOME_CONTENT, REGISTRATION_ACTIVATION, buildEntitySummary());

        verify(basicEmailDao).save(any(BasicEmail.class));
        verifyNoMoreInteractions(basicEmailDao, activationEmailDao, sendEmailService);
    }

    @Test
    public void prepareActivationEmailForSending_Will_Create_And_Save_ActivationEmail_When_Called() throws Exception {
        emailService.prepareActivationEmailForSending(SOME_GUID, SOME_ACTIVATION_GUID, SOME_CONTENT,
                SOME_CONTENT, REGISTRATION_ACTIVATION, buildEntitySummary());
        verify(activationEmailDao).save(any(ActivationEmail.class));
        verifyNoMoreInteractions(basicEmailDao, activationEmailDao, sendEmailService);
    }

    @Test
    public void sendEmail_Will_Delegate_And_Save_The_BasicEmail_When_Called() throws Exception {
        when(basicEmailDao.findByGuid(SOME_GUID)).thenReturn(buildBasicEmail());
        emailService.sendEmail(SOME_GUID);
        verify(basicEmailDao).findByGuid(SOME_GUID);
        verify(basicEmailDao).save(any(BasicEmail.class));
        verify(sendEmailService).sendMail(any(PreparedEmail.class));
        verifyNoMoreInteractions(basicEmailDao, activationEmailDao, sendEmailService, emailEventDao);
    }

    @Test
    public void handleEmailEvent_Will_Create_Event_And_Set_Missing_Values_When_The_Basic_Email_Values_Are_Missing() {
        DateTime now = now();
        BasicEmail basicEmail = buildBasicEmail();
        assertThat(basicEmail.getConfirmationId(), is(nullValue()));
        assertThat(basicEmail.getSentDate(), is(nullValue()));

        when(basicEmailDao.findByGuid(SOME_GUID)).thenReturn(basicEmail);

        emailService.handleEmailEvent(SOME_GUID, CONFIRMATION_ID, EVENT, now.toDate());

        verify(basicEmailDao).findByGuid(SOME_GUID);
        verify(emailEventDao).save(emailEventCaptor.capture());

        EmailEvent emailEvent = emailEventCaptor.getValue();

        assertThat(emailEvent.getEmailId(), is(EMAIL_ID));
        assertThat(emailEvent.getEventType(), is(EVENT));
        assertThat(emailEvent.getRemoteDate(), is(equalTo(new Timestamp(now.getMillis()))));

        assertThat(basicEmail.getConfirmationId(), is(CONFIRMATION_ID));
        assertThat(basicEmail.getSentDate(), is(equalTo(new Timestamp(now.getMillis()))));

        verifyNoMoreInteractions(basicEmailDao, activationEmailDao, sendEmailService, emailEventDao);
    }

    @Test
    public void handleEmailEvent_Will_Use_Confirmation_Id_When_Guid_Is_Not_Available() {
        DateTime now = now();
        BasicEmail basicEmail = buildBasicEmail();
        assertThat(basicEmail.getConfirmationId(), is(nullValue()));
        assertThat(basicEmail.getSentDate(), is(nullValue()));

        when(basicEmailDao.findByConfirmationId(CONFIRMATION_ID)).thenReturn(basicEmail);

        emailService.handleEmailEvent(null, CONFIRMATION_ID, EVENT, now.toDate());

        verify(basicEmailDao).findByConfirmationId(CONFIRMATION_ID);
        verify(emailEventDao).save(emailEventCaptor.capture());

        EmailEvent emailEvent = emailEventCaptor.getValue();

        assertThat(emailEvent.getEmailId(), is(EMAIL_ID));
        assertThat(emailEvent.getEventType(), is(EVENT));
        assertThat(emailEvent.getRemoteDate(), is(equalTo(new Timestamp(now.getMillis()))));

        assertThat(basicEmail.getConfirmationId(), is(CONFIRMATION_ID));
        assertThat(basicEmail.getSentDate(), is(equalTo(new Timestamp(now.getMillis()))));

        verifyNoMoreInteractions(basicEmailDao, activationEmailDao, sendEmailService, emailEventDao);
    }

    public void handleEmailEvent_Will_Not_Change_Values_When_Already_Set() {
        DateTime now = now();
        DateTime then = now.minusDays(5);
        BasicEmail basicEmail = buildBasicEmail();
        basicEmail.setConfirmationId(CONFIRMATION_ID);
        basicEmail.setSentDate(then.toDate());

        when(basicEmailDao.findByConfirmationId(CONFIRMATION_ID)).thenReturn(basicEmail);

        emailService.handleEmailEvent(null, CONFIRMATION_ID, EVENT, now.toDate());

        verify(basicEmailDao).findByConfirmationId(CONFIRMATION_ID);
        verify(emailEventDao).save(emailEventCaptor.capture());

        EmailEvent emailEvent = emailEventCaptor.getValue();

        assertThat(emailEvent.getEmailId(), is(EMAIL_ID));
        assertThat(emailEvent.getEventType(), is(EVENT));
        assertThat(emailEvent.getRemoteDate(), is(now));

        assertThat(basicEmail.getConfirmationId(), is(CONFIRMATION_ID));
        assertThat(basicEmail.getSentDate(), is(then));

        verifyNoMoreInteractions(basicEmailDao, activationEmailDao, sendEmailService, emailEventDao);
    }

    @Test
    public void getEmailContent_Will_Return_HTML_Content_When_Email_Has_HTML_Content(){
        when(basicEmailDao.findByGuid(SOME_GUID)).thenReturn(buildBasicEmail());
        EmailContent emailContent = emailService.getEmailContent(SOME_GUID);
        assertThat(emailContent.getContent(), is(SOME_CONTENT));
        assertThat(emailContent.getLocale(), is(CANADA_FRENCH));
        assertThat(emailContent.getContentType(), is(HTML));
    }

    @Test
    public void getEmailContent_Will_Return_TEXT_Content_When_Email_Has_NO_HTML_Content(){
        when(basicEmailDao.findByGuid(SOME_GUID)).thenReturn(buildBasicEmailWithOnlyText());
        EmailContent emailContent = emailService.getEmailContent(SOME_GUID);
        assertThat(emailContent.getContent(), is(SOME_CONTENT));
        assertThat(emailContent.getLocale(), is(CANADA_FRENCH));
        assertThat(emailContent.getContentType(), is(TEXT));
    }

    private BasicEmail buildBasicEmailWithOnlyText(){
        BasicEmail basicEmail = buildBasicEmail();
        basicEmail.setHtmlPayload(null);
        return basicEmail;
    }

    private BasicEmail buildBasicEmail() {
        BasicEmail basicEmail = new BasicEmail();
        basicEmail.setGuid(SOME_GUID);
        basicEmail.setToAddress(SOME_ADDRESS);
        basicEmail.setType(REGISTRATION_ACTIVATION);
        basicEmail.setId(EMAIL_ID);
        basicEmail.setHtmlPayload(SOME_CONTENT);
        basicEmail.setTextPayload(SOME_CONTENT);
        basicEmail.setLocale(CANADA_FRENCH);
        return basicEmail;
    }

    private EntitySummary buildEntitySummary() {
        return createEntitySummary(SOME_ADDRESS, ACCOUNT_ID, CANADA_FRENCH);
    }
}

