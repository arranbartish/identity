package com.solvedbysunrise.identity.data.dto;

import java.util.HashMap;

import static com.solvedbysunrise.identity.data.dto.ApplicationProperties.Key.*;
import static org.apache.commons.collections4.MapUtils.*;

public class ApplicationProperties extends HashMap<String, String> {

    public enum Key {
        MAIL_GUN_API_URL_PATTERN("MAIL_GUN_API_URL_PATTERN"),
        MAIL_GUN_API_KEY("MAIL_GUN_API_KEY"),
        EMAIL_ACTIVATION_LINK_STRING_TEMPLATE("EMAIL_ACTIVATION_LINK_STRING_TEMPLATE"),
        EMAIL_DEACTIVATION_LINK_STRING_TEMPLATE("EMAIL_DEACTIVATION_LINK_STRING_TEMPLATE"),
        EMAIL_PASSWORD_RESET_LINK_STRING_TEMPLATE("EMAIL_PASSWORD_RESET_LINK_STRING_TEMPLATE"),
        EMAIL_RESEND_TIME_IN_SECONDS("EMAIL_RESEND_TIME_IN_SECONDS"),
        EMAIL_RESEND_BATCH_SIZE("EMAIL_RESEND_BATCH_SIZE"),
        CURRENT_PASSWORD_VERSION("CURRENT_PASSWORD_VERSION"),
        EMAIL_IN_BROWSER_LINK_STRING_TEMPLATE("EMAIL_IN_BROWSER_LINK_STRING_TEMPLATE"),
        MAX_AGE_OF_MESSAGE_IN_MILLIS("MAX_AGE_OF_MESSAGE_IN_MILLIS"),
        IS_AUDIT_ENABLED("IS_AUDIT_ENABLED"),
        GEO_IP_URL_PATTERN("GEO_IP_URL_PATTERN"),
        ROOT_ACCOUNT_ID("ROOT_ACCOUNT_ID");

        private final String key;

        Key(final String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }


    public String getMailGunApiURLPattern() {
       return getString(this, MAIL_GUN_API_URL_PATTERN.getKey());
    }

    public String getMailGunApiKey() {
        return getString(this, MAIL_GUN_API_KEY.getKey());
    }

    public String getEmailActivationLinkStringTemplate() {
        return getString(this, EMAIL_ACTIVATION_LINK_STRING_TEMPLATE.getKey());
    }

    public String getEmailDeactivationLinkStringTemplate() {
        return getString(this, EMAIL_DEACTIVATION_LINK_STRING_TEMPLATE.getKey());
    }

    public String getEmailPasswordResetLinkStringTemplate() {
        return getString(this, EMAIL_PASSWORD_RESET_LINK_STRING_TEMPLATE.getKey());
    }

    public String getEmailInBrowserLinkStringTemplate() {
        return getString(this, EMAIL_IN_BROWSER_LINK_STRING_TEMPLATE.getKey());
    }

    public Long getRootAccountId() {
        return getLong(this, ROOT_ACCOUNT_ID.getKey());
    }

    public Integer getEmailResendTimeInSeconds() {
        return getInteger(this, EMAIL_RESEND_TIME_IN_SECONDS.getKey());
    }

    public Integer getEmailResendBatchSize() {
        return getInteger(this, EMAIL_RESEND_BATCH_SIZE.getKey());
    }

    public String getCurrentPasswordVersion() {
        return getString(this, CURRENT_PASSWORD_VERSION.getKey());
    }

    public boolean isAuditEnabled() {
        return getBoolean(this, IS_AUDIT_ENABLED.getKey(), false);
    }

    public Integer getMaxAgeOfMessageInMillis() {
        return getInteger(this, MAX_AGE_OF_MESSAGE_IN_MILLIS.getKey());
    }

    public String getGeoIpURLPattern() {
        return getString(this, GEO_IP_URL_PATTERN.getKey());
    }
}
