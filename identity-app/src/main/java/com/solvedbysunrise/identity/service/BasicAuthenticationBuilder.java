package com.solvedbysunrise.identity.service;


import com.solvedbysunrise.identity.service.dtto.UsernameAndPassword;
import com.solvedbysunrise.identity.service.exception.AuthenticationFailedException;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;

import java.nio.charset.Charset;

import static java.lang.String.format;
import static java.nio.charset.Charset.forName;
import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.codec.binary.Base64.encodeBase64;
import static org.apache.commons.lang3.ArrayUtils.getLength;
import static org.apache.commons.lang3.ArrayUtils.isEmpty;
import static org.apache.commons.lang3.CharEncoding.UTF_8;
import static org.apache.commons.lang3.StringUtils.*;
import static org.slf4j.LoggerFactory.getLogger;

public class BasicAuthenticationBuilder {

    private static final Charset UTF_8_CHARSET = forName(UTF_8);
    private static final String BASIC_PREFIX = "Basic";
    private static final String CREDS_SEPERATOR = ":";

    private static final Logger LOGGER = getLogger(BasicAuthenticationBuilder.class);

    private static final String SOURCE = "IDENTITY";

    public static String encodeUsernameAndPassword(String username, String password) {
        String concatinatedCreds = format("%s%s%s", username, CREDS_SEPERATOR, password);
        byte[] bytes = encodeBase64(concatinatedCreds.getBytes(UTF_8_CHARSET));
        String encodedCreds = new String(bytes);

        return trim(format("%s %s", BASIC_PREFIX, encodedCreds));
    }

    public static UsernameAndPassword decodeUsernameAndPassword(String basicAuthenticationString) {
        String[] creds = null;
        try {

            String encodedCreds = trim(remove(basicAuthenticationString, BASIC_PREFIX));

            String concatinatedCreds = new String(decodeBase64(encodedCreds.getBytes()), UTF_8);

            creds = split(concatinatedCreds, CREDS_SEPERATOR);


            if(isEmpty(creds) || getLength(creds) != 2) {
                String toShow = ArrayUtils.toString(creds, "null");
                String message = format("Failed to authenticate with invalid array [%s]", toShow);
                LOGGER.warn(message);
                throw new AuthenticationFailedException(message, SOURCE);
            }
        } catch (Exception e) {
            String message = "Failed to authenticate";
            LOGGER.warn(message, e);
            throw new AuthenticationFailedException(message, e, SOURCE);
        }

        return new UsernameAndPassword(creds[0], creds[1]);
    }

}
