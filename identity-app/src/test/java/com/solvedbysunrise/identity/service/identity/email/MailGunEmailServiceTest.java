package com.solvedbysunrise.identity.service.identity.email;

import com.solvedbysunrise.identity.data.dto.ApplicationProperties;
import com.solvedbysunrise.identity.data.dto.EmailProperties;
import com.solvedbysunrise.identity.service.EmailPropertiesService;
import com.solvedbysunrise.identity.service.MailGunEmailService;
import com.solvedbysunrise.identity.service.dtto.IdOnly;
import com.solvedbysunrise.identity.service.dtto.PreparedEmail;
import com.solvedbysunrise.identity.service.properties.ApplicationPropertiesService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static com.solvedbysunrise.identity.data.dto.ApplicationProperties.Key.MAIL_GUN_API_KEY;
import static com.solvedbysunrise.identity.data.dto.ApplicationProperties.Key.MAIL_GUN_API_URL_PATTERN;
import static com.solvedbysunrise.identity.data.entity.jpa.email.EmailType.REGISTRATION_ACTIVATION;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MailGunEmailServiceTest {

    private static final String ADDRESS = "ADDRESS";
    private static final String CONTENT = "CONTENT";
    private static final String VALUE = "VALUE";
    private static final String URL_PATTERN = "http://someurl.com%s";
    private static final String EMAIL_GUID = "EMAIL_GUID";

    private static final String CONFIRMATION_ID = "CONFIRMATION_ID";


    private final ApplicationProperties applicationProperties = buildApplicationProperties();
    private final EmailProperties emailProperties = buildEmailProperties();

    @Mock
    private EmailPropertiesService emailPropertiesService;

    @Mock
    private ApplicationPropertiesService applicationPropertiesService;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private MailGunEmailService mailGunEmailService;

    @Captor
    private ArgumentCaptor<RequestEntity> requestCaptor;

    @Mock
    private ResponseEntity responseEntity;

    @Test
    public void sendMail_Will_Send_A_Message_With_The_Required_Values_When_Given_An_Email() throws Exception {
        when(emailPropertiesService.getEmailProperties(REGISTRATION_ACTIVATION)).thenReturn(emailProperties);
        when(applicationPropertiesService.getApplicationProperties()).thenReturn(applicationProperties);

        when(restTemplate.exchange(any(), Mockito.<Class<?>>any())).thenReturn(responseEntity);
        when(responseEntity.getBody()).thenReturn(buildResponse());

        String confirmationId = mailGunEmailService.sendMail(buildPreparedEmail());

        verify(restTemplate).exchange(requestCaptor.capture(), Mockito.<Class<?>>any());

        RequestEntity request = requestCaptor.getValue();

        @SuppressWarnings("unchecked")
        Map<String, String> payload = (Map<String, String>)request.getBody();
        HttpHeaders headers = request.getHeaders();


        assertThat(confirmationId, is(CONFIRMATION_ID));
        assertThat(payload, allOf(
                hasEntry(MailGunEmailService.CAMPAIGN, VALUE),
                hasEntry(MailGunEmailService.MAILGUN_FROM, VALUE),
                hasEntry(MailGunEmailService.MAILGUN_SUBJECT, VALUE),
                hasEntry(MailGunEmailService.MAILGUN_TO, ADDRESS),
                hasEntry(MailGunEmailService.MAILGUN_HTML, CONTENT),
                hasEntry(is(MailGunEmailService.TRACKING), is(notNullValue())),
                hasEntry(is(MailGunEmailService.TRACKING), is(notNullValue())),
                hasEntry(is(MailGunEmailService.TRACKING_OPEN), is(notNullValue())),
                hasEntry(is(MailGunEmailService.TRACKING_CLICK), is(notNullValue()))
                ));

        assertThat(headers, allOf(
                hasEntry(is(MailGunEmailService.REQUEST_URL_HEADER), is(notNullValue())),
                hasEntry(is(MailGunEmailService.CONTENT_TYPE_HEADER), is(notNullValue())),
                hasEntry(is(MailGunEmailService.AUTHORIZATION_HEADER), is(notNullValue()))
        ));

    }


    public PreparedEmail buildPreparedEmail(){
        return new PreparedEmail(EMAIL_GUID , CONTENT, CONTENT, REGISTRATION_ACTIVATION, ADDRESS);
    }

    private EmailProperties buildEmailProperties(){
        EmailProperties emailProperties = new EmailProperties();
        emailProperties.put(EmailProperties.CAMPAIGN, VALUE);
        emailProperties.put(EmailProperties.FROM_ADDRESS_PATTERN, VALUE);
        emailProperties.put(EmailProperties.FROM_ADDRESS_DOMAIN, VALUE);
        emailProperties.put(EmailProperties.FROM_ADDRESS_USER, VALUE);
        emailProperties.put(EmailProperties.SUBJECT, VALUE);
        emailProperties.put(EmailProperties.IS_TRACKING_ENABLED, VALUE);
        emailProperties.put(EmailProperties.IS_CLICK_TRACKING_ENABLED, VALUE);
        emailProperties.put(EmailProperties.IS_OPEN_TRACKING_ENABLED, VALUE);
        emailProperties.put(EmailProperties.TAG_ONE, VALUE);
        emailProperties.put(EmailProperties.TAG_TWO, VALUE);
        emailProperties.put(EmailProperties.TAG_THREE, VALUE);

        return emailProperties;
    }

     private ApplicationProperties buildApplicationProperties() {
         ApplicationProperties applicationProperties = new ApplicationProperties();

         applicationProperties.put(MAIL_GUN_API_KEY.getKey(), VALUE);
         applicationProperties.put(MAIL_GUN_API_URL_PATTERN.getKey(), URL_PATTERN);
         return applicationProperties;
     }

    private IdOnly buildResponse(){
        IdOnly id = new IdOnly();
        id.setId(CONFIRMATION_ID);
        return id;
    }
}
