package com.solvedbysunrise.identity.service.security;

import com.solvedbysunrise.identity.data.dto.ApplicationProperties;
import com.solvedbysunrise.identity.service.properties.ApplicationPropertiesService;
import com.solvedbysunrise.identity.service.security.dto.SignedPayload;
import com.solvedbysunrise.identity.service.security.exception.ExpiredException;
import com.solvedbysunrise.identity.service.security.exception.HashFailedException;
import com.solvedbysunrise.identity.service.security.exception.SignatureFailedException;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.solvedbysunrise.identity.data.dto.ApplicationProperties.Key.MAX_AGE_OF_MESSAGE_IN_MILLIS;
import static com.solvedbysunrise.identity.data.dto.ApplicationProperties.Key.ROOT_ACCOUNT_ID;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HashTimeoutSha256MessageValidationServiceTest {

    private static final String PAYLOAD = "{\"accountId\":\"1\"}";
    private static final String HASH = "HASH";
    private static final String WRONG_HASH = "WRONG_HASH";
    private static final Long ACCOUNT_ID = 1L;
    private static final String API_KEY = "API_KEY";
    private static final Integer ONE_MINUTE = 60000;
    private static final Integer ONE_MINUTE_IN_PAST = -60000;

    @Mock
    private HashService hashService;

    @Mock
    private ApiKeyService apiKeyService;

    @Mock
    private ApplicationPropertiesService applicationPropertiesService;

    @InjectMocks
    private HashTimeoutSha256MessageValidationService MessageValidationService;


    @Test
    public void signPayload_Will_Return_A_Payload_When_Given_A_String() throws Exception {
        when(hashService.hash(PAYLOAD)).thenReturn(HASH);
        when(apiKeyService.getKeyForMessageSigning(ACCOUNT_ID)).thenReturn(API_KEY);
        when(applicationPropertiesService.getApplicationProperties()).thenReturn(buildApplicationProperties());

        SignedPayload signedPayload = MessageValidationService.signPayload(PAYLOAD);
        assertThat(signedPayload.getAccountId(), is(1L));
        assertThat(signedPayload.getPayload(), is(PAYLOAD));
        assertThat(signedPayload.getPayloadHash(), is(HASH));
        assertThat(signedPayload.getSignature(), is(notNullValue()));
        assertThat(signedPayload.getTimestamp(), is(notNullValue()));
    }

    @Test
    public void retrieveValidatedPayload_Will_Return_A_Payload_When_Given_A_Signed_Payload() throws Exception {
        when(hashService.hash(PAYLOAD)).thenReturn(HASH);
        when(apiKeyService.getKeyForMessageSigning(ACCOUNT_ID)).thenReturn(API_KEY);
        when(applicationPropertiesService.getApplicationProperties()).thenReturn(buildApplicationProperties());
        
        SignedPayload signedPayload = MessageValidationService.signPayload(PAYLOAD);
        String payload = MessageValidationService.retrieveValidatedPayload(signedPayload);
        
        assertThat(payload, is(PAYLOAD));
    }

    @Test(expected = ExpiredException.class)
    public void retrieveValidatedPayload_Will_Throw_ExpiredException_When_Message_Is_Too_Old() throws Exception {
        when(hashService.hash(PAYLOAD)).thenReturn(HASH);
        when(apiKeyService.getKeyForMessageSigning(ACCOUNT_ID)).thenReturn(API_KEY);
        when(applicationPropertiesService.getApplicationProperties()).thenReturn(buildApplicationProperties(ONE_MINUTE_IN_PAST));

        SignedPayload signedPayload = MessageValidationService.signPayload(PAYLOAD);
        MessageValidationService.retrieveValidatedPayload(signedPayload);

    }

    @Test(expected = HashFailedException.class)
    public void retrieveValidatedPayload_Will_Throw_HashFailedException_When_Message_Hash_Does_Not_Match() throws Exception {
        when(hashService.hash(PAYLOAD)).thenReturn(HASH);
        when(apiKeyService.getKeyForMessageSigning(ACCOUNT_ID)).thenReturn(API_KEY);
        when(applicationPropertiesService.getApplicationProperties()).thenReturn(buildApplicationProperties());

        SignedPayload signedPayload = MessageValidationService.signPayload(PAYLOAD);

        when(hashService.hash(PAYLOAD)).thenReturn(WRONG_HASH);

        MessageValidationService.retrieveValidatedPayload(signedPayload);

    }

    @Test(expected = SignatureFailedException.class)
    public void retrieveValidatedPayload_Will_Throw_SignatureFailedException_When_Someone_Changes_Timestamp() throws Exception {

        when(hashService.hash(PAYLOAD)).thenReturn(HASH);
        when(apiKeyService.getKeyForMessageSigning(ACCOUNT_ID)).thenReturn(API_KEY);
        when(applicationPropertiesService.getApplicationProperties()).thenReturn(buildApplicationProperties());

        SignedPayload signedPayload = MessageValidationService.signPayload(PAYLOAD);

        signedPayload.setTimestamp(DateTime.now().plusDays(1).getMillis()); // try and make the message one day younger

        MessageValidationService.retrieveValidatedPayload(signedPayload);

    }

    private ApplicationProperties buildApplicationProperties() {
        return buildApplicationProperties(ONE_MINUTE);
    }

    private ApplicationProperties buildApplicationProperties(final Integer maxMessageAgeInMillis) {
        ApplicationProperties applicationProperties = new ApplicationProperties();
        applicationProperties.put(MAX_AGE_OF_MESSAGE_IN_MILLIS.getKey(), maxMessageAgeInMillis.toString());
        applicationProperties.put(ROOT_ACCOUNT_ID.getKey(), ACCOUNT_ID.toString());

        return applicationProperties;
    }
}
