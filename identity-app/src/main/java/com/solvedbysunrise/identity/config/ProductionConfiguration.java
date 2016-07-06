package com.solvedbysunrise.identity.config;

import com.solvedbysunrise.identity.config.exception.IncompleteConfiguration;
import com.solvedbysunrise.identity.data.dto.ApplicationProperties;
import com.solvedbysunrise.identity.data.dto.ApplicationProperties.Key;
import com.solvedbysunrise.identity.data.dto.EmailProperties;
import com.solvedbysunrise.identity.data.entity.jpa.email.EmailType;
import com.solvedbysunrise.identity.service.EmailPropertiesService;
import com.solvedbysunrise.identity.service.SpringInjectedEmailPropertiesService;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.solvedbysunrise.identity.data.dto.ApplicationProperties.Key.*;
import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.tuple.Pair.of;

@Configuration
public class ProductionConfiguration implements WastedTimeConfiguration {

    @Value("${baseUrl:http://localhost:8080/}")
    private String baseUrl;

    @Value("${DATABASE_URL:did-not-resolve}")
    private String databaseUrl;

    @Value("${testValue:also-did-not-work}")
    private String testValue;

    @Value("${duration.wasted.in.hours:8}")
    private Integer totalDurationInHours;

    @Value("${interval.in.minutes:15}")
    private Integer intervalInMinutes;

    @Value("${mailgun.api.url.pattern:http://api.mailgun.net/v2/receiptdrop.mailgun.org/messages}")
    private String mailgunApiUrlPattern;

    @Value("${mailgun.api.key:YeahRight}")
    private String mailgunApiKey;

    @Value("${email.activationLinkStringTemplate:https://identity.receiptdrop.com/activate/%s}")
    private String emailActivationLinkStringTemplate;

    @Value("${email.deactivationLinkStringTemplate:https://identity.receiptdrop.com/deactivate/%s}")
    private String emailDeactivationLinkStringTemplate;

    @Value("${email.passwordResetLinkStringTemplate:https://identity.receiptdrop.com/password-reset/%s}")
    private String emailPasswordResetLinkStringTemplate;

    @Value("${email.resendTimeInSeconds:21600}")
    private String emailResendTimeInSeconds;

    @Value("${email.resendBatchSize:10}")
    private String emailResendBatchSize;

    @Value("${current.passwordVersion:1}")
    private String currentPasswordVersion;

    @Value("${email.in.browserLinkStringTemplate:https://identity.receiptdrop.com/view/email/%s}")
    private String emailInBrowserLinkStringTemplate;

    @Value("${is.audit.enabled:false}")
    private String isAuditEnabled;

    @Value("${geo.ip.url.pattern:http://localhost:8787/json/%s}")
    private String geoIpUrlPattarn;

    @Value("${root.account.id:1}")
    private String rootAccountId;

    @Value("${maxAgeOfMessageInMillis:60000}")
    private String maxAgeOfMessageInMillis;

    @Override
    @Bean(name = "wastedTimeBaseUrl")
    public String wastedTimeBaseUrl() {
        return baseUrl;
    }

    @Override
    @Bean(name = "wastedTimeUrl")
    public String wastedTimeUrl() {
        return databaseUrl;
    }

    @Override
    @Bean(name = "testValue")
    public String testValue() {
        return testValue;
    }

    @Override
    @Bean(name = "totalDurationInHours")
    public Integer totalDurationInHours() {
        return totalDurationInHours;
    }

    @Override
    @Bean(name = "intervalInMinutes")
    public Integer intervalInMinutes() {
        return intervalInMinutes;
    }

    @Override
    @Bean(name = "config")
    public Collection<Pair<String, String>> config() {
        return newArrayList(
                of("database", wastedTimeUrl()),
                of("url", wastedTimeBaseUrl()),
                of("test", testValue()));
    }

    @Bean(name = "applicationProperties")
    public ApplicationProperties applicationProperties() {
        ApplicationProperties applicationProperties = new ApplicationProperties();

        applicationProperties.put(MAIL_GUN_API_URL_PATTERN.getKey(), mailgunApiUrlPattern);
        applicationProperties.put(MAIL_GUN_API_KEY.getKey(), mailgunApiKey);
        applicationProperties.put(EMAIL_ACTIVATION_LINK_STRING_TEMPLATE.getKey(), emailActivationLinkStringTemplate);
        applicationProperties.put(EMAIL_DEACTIVATION_LINK_STRING_TEMPLATE.getKey(), emailDeactivationLinkStringTemplate);
        applicationProperties.put(EMAIL_PASSWORD_RESET_LINK_STRING_TEMPLATE.getKey(), emailPasswordResetLinkStringTemplate);
        applicationProperties.put(EMAIL_RESEND_TIME_IN_SECONDS.getKey(), emailResendTimeInSeconds);
        applicationProperties.put(EMAIL_RESEND_BATCH_SIZE.getKey(), emailResendBatchSize);
        applicationProperties.put(CURRENT_PASSWORD_VERSION.getKey(), currentPasswordVersion);
        applicationProperties.put(EMAIL_IN_BROWSER_LINK_STRING_TEMPLATE.getKey(), emailInBrowserLinkStringTemplate);
        applicationProperties.put(IS_AUDIT_ENABLED.getKey(), isAuditEnabled);
        applicationProperties.put(GEO_IP_URL_PATTERN.getKey(), geoIpUrlPattarn);
        applicationProperties.put(ROOT_ACCOUNT_ID.getKey(), rootAccountId);
        applicationProperties.put(MAX_AGE_OF_MESSAGE_IN_MILLIS.getKey(), maxAgeOfMessageInMillis);


        List<Key> unpamppedKeys = new ArrayList<>();
        try (Stream<Key> keys = stream(values())) {
            unpamppedKeys.addAll(
                    keys
                            .parallel()
                            .filter(key -> isBlank(applicationProperties.get(key.getKey())))
                            .collect(toList()));
        }
        if (isNotEmpty(unpamppedKeys)) {
            StringBuilder builder = new StringBuilder();
            unpamppedKeys.stream().forEach(unmappedKey -> builder.append("- ").append(unmappedKey.getKey()).append("\n"));
            throw new IncompleteConfiguration(format("[%s] configuration items are missing\n%s", unpamppedKeys.size(), builder.toString()));
        }
        return applicationProperties;
    }

    @Bean
    public EmailPropertiesService emailPropertiesMap() {
        Map<String, EmailProperties> emailPropertiesMap = newHashMap();
        emailPropertiesMap.put(EmailType.REGISTRATION_ACTIVATION.name(), new EmailProperties());

        return new SpringInjectedEmailPropertiesService(emailPropertiesMap);
    }
}
