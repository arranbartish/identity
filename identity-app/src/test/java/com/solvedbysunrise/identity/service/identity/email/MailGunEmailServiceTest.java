package com.solvedbysunrise.identity.service.identity.email;

import com.receiptdrop.identity.dto.EmailProperties;
import com.receiptdrop.service.properties.ApplicationPropertiesService;
import com.receiptdrop.service.properties.dto.ApplicationProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.integration.Message;
import org.springframework.integration.MessageHeaders;
import org.springframework.integration.core.MessagingOperations;

import java.util.Map;

import static com.receiptdrop.identity.domain.email.EmailType.REGISTRATION_ACTIVATION;
import static com.receiptdrop.identity.dto.EmailProperties.*;
import static com.receiptdrop.service.properties.dto.ApplicationProperties.MAIL_GUN_API_URL_PATTERN;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.integration.support.MessageBuilder.withPayload;

@RunWith(MockitoJUnitRunner.class)
public class MailGunEmailServiceTest {

    private static final String ADDRESS = "ADDRESS";
    private static final String CONTENT = "CONTENT";
    private static final String VALUE = "VALUE";
    private static final String URL_PATTERN = "http://someurl.com%s";
    private static final String EMAIL_GUID = "EMAIL_GUID";

    private static final String CONFIRMATION_ID = "CONFIRMATION_ID";

    private static final Message HAPPY_RESPONSE = withPayload("{\"id\" : \"CONFIRMATION_ID\"}").build();

    @Mock
    private MessagingOperations messagingOperations;

    @Mock
    private EmailPropertiesService emailPropertiesService;

    @Mock
    private ApplicationPropertiesService applicationPropertiesService;

    @Mock
    private Message<?> response;

    @InjectMocks
    private MailGunEmailService mailGunEmailService;

    @Captor
    private ArgumentCaptor<Message<Map<String, String>>> messageCaptor;

    @Test
    public void sendMail_Will_Send_A_Message_With_The_Required_Values_When_Given_An_Email() throws Exception {
        final ApplicationProperties applicationProperties = buildApplicationProperties();
        EmailProperties emailProperties = buildEmailProperties();

        when(emailPropertiesService.getEmailProperties(REGISTRATION_ACTIVATION)).thenReturn(emailProperties);
        when(applicationPropertiesService.getApplicationProperties()).thenReturn(applicationProperties);

        when(messagingOperations.sendAndReceive(any(Message.class))).thenReturn(HAPPY_RESPONSE);

        String confirmationId = mailGunEmailService.sendMail(buildPreparedEmail());
        assertThat(confirmationId, is(CONFIRMATION_ID));

        verify(messagingOperations).sendAndReceive(messageCaptor.capture());

        Message<Map<String, String>> message = messageCaptor.getValue();

        Map<String, String> payload = message.getPayload();
        MessageHeaders headers = message.getHeaders();

        assertThat(payload.get(MailGunEmailService.CAMPAIGN), is(VALUE));
        assertThat(payload.get(MailGunEmailService.TRACKING), is(notNullValue()));
        assertThat(payload.get(MailGunEmailService.TRACKING_OPEN), is(notNullValue()));
        assertThat(payload.get(MailGunEmailService.TRACKING_CLICK), is(notNullValue()));

        assertThat(payload.get(MailGunEmailService.MAILGUN_FROM), is(VALUE));
        assertThat(payload.get(MailGunEmailService.MAILGUN_SUBJECT), is(VALUE));
        assertThat(payload.get(MailGunEmailService.MAILGUN_TO), is(ADDRESS));
        assertThat(payload.get(MailGunEmailService.MAILGUN_HTML), is(CONTENT));

        assertThat(headers.get(MailGunEmailService.REQUEST_URL_HEADER), is(notNullValue()));
        assertThat(headers.get(MailGunEmailService.CONTENT_TYPE_HEADER), is(notNullValue()));
        assertThat(headers.get(MailGunEmailService.AUTHORIZATION_HEADER), is(notNullValue()));

    }


    public PreparedEmail buildPreparedEmail(){
        return new PreparedEmail(EMAIL_GUID , CONTENT, CONTENT, REGISTRATION_ACTIVATION, ADDRESS);
    }

    private EmailProperties buildEmailProperties(){
        EmailProperties emailProperties = new EmailProperties();
        emailProperties.put(CAMPAIGN, VALUE);
        emailProperties.put(FROM_ADDRESS_PATTERN, VALUE);
        emailProperties.put(FROM_ADDRESS_DOMAIN, VALUE);
        emailProperties.put(FROM_ADDRESS_USER, VALUE);
        emailProperties.put(SUBJECT, VALUE);
        emailProperties.put(IS_TRACKING_ENABLED, VALUE);
        emailProperties.put(IS_CLICK_TRACKING_ENABLED, VALUE);
        emailProperties.put(IS_OPEN_TRACKING_ENABLED, VALUE);
        emailProperties.put(EmailProperties.TAG_ONE, VALUE);
        emailProperties.put(EmailProperties.TAG_TWO, VALUE);
        emailProperties.put(EmailProperties.TAG_THREE, VALUE);

        return emailProperties;
    }

     private ApplicationProperties buildApplicationProperties() {
         ApplicationProperties applicationProperties = new ApplicationProperties();

         applicationProperties.put(ApplicationProperties.MAIL_GUN_API_KEY, VALUE);
         applicationProperties.put(MAIL_GUN_API_URL_PATTERN, URL_PATTERN);
         return applicationProperties;
     }
}
