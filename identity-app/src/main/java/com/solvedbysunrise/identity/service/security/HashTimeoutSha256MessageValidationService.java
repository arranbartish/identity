package com.solvedbysunrise.identity.service.security;

import com.solvedbysunrise.identity.data.dto.ApplicationProperties;
import com.solvedbysunrise.identity.service.properties.ApplicationPropertiesService;
import com.solvedbysunrise.identity.service.security.dto.SignedPayload;
import com.solvedbysunrise.identity.service.security.exception.ExpiredException;
import com.solvedbysunrise.identity.service.security.exception.HashFailedException;
import com.solvedbysunrise.identity.service.security.exception.SignatureFailedException;
import com.solvedbysunrise.identity.service.security.exception.SignatureGenerationFailedException;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;

import static java.lang.String.format;
import static java.lang.String.valueOf;
import static java.nio.charset.Charset.forName;
import static org.apache.commons.codec.binary.Hex.encodeHex;
import static org.apache.commons.lang3.CharEncoding.UTF_8;
import static org.joda.time.DateTime.now;
import static org.slf4j.LoggerFactory.getLogger;

@Service
public class HashTimeoutSha256MessageValidationService implements MessageValidationService {

    private static final String ALGORITHM = "HmacSHA256";
    private static final Logger LOGGER = getLogger(HashTimeoutSha256MessageValidationService.class);

    private final HashService hashService;
    private final ApiKeyService apiKeyService;
    private final ApplicationPropertiesService applicationPropertiesService;

    @Autowired
    public HashTimeoutSha256MessageValidationService(final HashService hashService,
                                                     final ApiKeyService apiKeyService,
                                                     final ApplicationPropertiesService applicationPropertiesService) {
        this.hashService = hashService;
        this.apiKeyService = apiKeyService;
        this.applicationPropertiesService = applicationPropertiesService;
    }

    @Override
    public SignedPayload signPayloadForAccount(Long accountId, String payload) {
        SignedPayload signedPayload = new SignedPayload();
        long milliSeconds = now().getMillis();
        signedPayload.setAccountId(accountId);
        signedPayload.setPayload(payload);
        signedPayload.setTimestamp(milliSeconds);
        signedPayload.setPayloadHash(hashService.hash(payload));

        String unecryptedSignature = buildUnencryptedSignature(signedPayload);

        signedPayload.setSignature(sign(accountId, unecryptedSignature));

        return signedPayload;
    }

    private String sign(Long accountId, String unecryptedSignature) {
        try {
            Charset charset = forName(UTF_8);
            Mac sha256_HMAC =  Mac.getInstance(ALGORITHM);
            final String apiKey = apiKeyService.getKeyForMessageSigning(accountId);
            SecretKeySpec secret_Key = new SecretKeySpec(charset.encode(apiKey).array(), ALGORITHM);
            sha256_HMAC.init(secret_Key);
            final byte[] signature = sha256_HMAC.doFinal(charset.encode(unecryptedSignature).array());
            return valueOf(encodeHex(signature));
        } catch(Exception e) {
            String msg = format("failed to create signature %s for account %s", unecryptedSignature, accountId);
            LOGGER.info(msg, e);
            throw new SignatureGenerationFailedException(msg, e);
        }
    }

    private String buildUnencryptedSignature(SignedPayload signedPayload){
        return format("%s-%s", signedPayload.getTimestamp(), signedPayload.getPayloadHash());
    }

    @Override
    public String retrieveValidatedPayload(final SignedPayload signedPayload) {
        ApplicationProperties applicationProperties = applicationPropertiesService.getApplicationProperties();
        Integer maxAgeOfMessageInMillis = applicationProperties.getMaxAgeOfMessageInMillis();

        DateTime messageExpiry = new DateTime()
                .withMillis(signedPayload.getTimestamp())
                .plusMillis(maxAgeOfMessageInMillis);

        if(messageExpiry.isBeforeNow()){
            LOGGER.info(format("The message has expired %s", signedPayload));
            throw new ExpiredException();
        }

        String calculatedHash = hashService.hash(signedPayload.getPayload());
        if(!StringUtils.equals(calculatedHash, signedPayload.getPayloadHash())){
            LOGGER.info(format("The hash values of the message did not match %s", signedPayload));
            throw new HashFailedException();
        }

        String expectedUnencryptedSignature = buildUnencryptedSignature(signedPayload);
        String signedSignature = sign(signedPayload.getAccountId(), expectedUnencryptedSignature);
        if(!StringUtils.equals(signedSignature, signedPayload.getSignature())){
            LOGGER.info(format("Signatures did not match for message %s", signedPayload));
            throw new SignatureFailedException();
        }

        return signedPayload.getPayload();
    }

}
