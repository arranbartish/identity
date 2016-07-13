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
import static com.solvedbysunrise.identity.data.entity.jpa.email.EmailType.CONTACT_US;
import static com.solvedbysunrise.identity.data.entity.jpa.email.EmailType.REGISTRATION_ACTIVATION;
import static com.solvedbysunrise.identity.data.entity.jpa.email.EmailType.RESET_PASSWORD;
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

    @Value("${mailgun.api.url.pattern:http://api.mailgun.net/v2/%s/messages}")
    private String mailgunApiUrlPattern;

    @Value("${mailgun.api.key:YeahRight}")
    private String mailgunApiKey;

    @Value("${email.activationLinkStringTemplate:https://identity.example.com/activate/%s}")
    private String emailActivationLinkStringTemplate;

    @Value("${email.deactivationLinkStringTemplate:https://identity.example.com/deactivate/%s}")
    private String emailDeactivationLinkStringTemplate;

    @Value("${email.passwordResetLinkStringTemplate:https://identity.example.com/password-reset/%s}")
    private String emailPasswordResetLinkStringTemplate;

    @Value("${email.resendTimeInSeconds:21600}")
    private String emailResendTimeInSeconds;

    @Value("${email.resendBatchSize:10}")
    private String emailResendBatchSize;

    @Value("${current.passwordVersion:1}")
    private String currentPasswordVersion;

    @Value("${email.in.browserLinkStringTemplate:https://identity.example.com/view/email/%s}")
    private String emailInBrowserLinkStringTemplate;

    @Value("${is.audit.enabled:false}")
    private String isAuditEnabled;

    @Value("${geo.ip.url.pattern:http://localhost:8787/json/%s}")
    private String geoIpUrlPattarn;

    @Value("${root.account.id:1}")
    private String rootAccountId;

    @Value("${maxAgeOfMessageInMillis:60000}")
    private String maxAgeOfMessageInMillis;

@Value("${contactUs.fromAddressDomain:email.example.com}")
private String contactUsFromAddressDomain;

    @Value("${contactUs.fromAddressPattern:Accounts <%s@%s>}")
    private String contactUsFromAddressPattern;

    @Value("${contactUs.fromAddressUser:contact.us}")
    private String contactUsFromAddressUser;

    @Value("${contactUs.isClickTrackingEnabled:true}")
    private String contactUsIsClickTrackingEnabled;

    @Value("${contactUs.isOpenTrackingEnabled:true}")
    private String contactUsIsOpenTrackingEnabled;

    @Value("${contactUs.isTrackingEnabled:true}")
    private String contactUsIsTrackingEnabled;

    @Value("${contactUs.subject:Contact us Query}")
    private String contactUsSubject;

    @Value("${contactUs.tag1:CONTACT_US}")
    private String contactUsTag1;

    @Value("${registrationActivation.campaign:registration}")
    private String registrationActivationCampaign;

    @Value("${registrationActivation.fromAddressDomain:email.example.com}")
    private String registrationActivationFromAddressDomain;

    @Value("${registrationActivation.fromAddressPattern:Accounts <%s@%s>}")
    private String registrationActivationFromAddressPattern;

    @Value("${registrationActivation.fromAddressUser:registrations}")
    private String registrationActivationFromAddressUser;

    @Value("${registrationActivation.isClickTrackingEnabled:true}")
    private String registrationActivationIsClickTrackingEnabled;

    @Value("${registrationActivation.isOpenTrackingEnabled:true}")
    private String registrationActivationIsOpenTrackingEnabled;

    @Value("${registrationActivation.isTrackingEnabled:true}")
    private String registrationActivationIsTrackingEnabled;

    @Value("${registrationActivation.subject:Welcome to The App}")
    private String registrationActivationSubject;

    @Value("${registrationActivation.tag1:REGISTRATION_ACTIVATION}")
    private String registrationActivationTag1;

    @Value("${resetPassword.campaign:registration}")
    private String resetPasswordCampaign;

    @Value("${resetPassword.fromAddressDomain:email.example.com}")
    private String resetPasswordFromAddressDomain;

    @Value("${resetPassword.fromAddressPattern:Accounts <%s@%s>}")
    private String resetPasswordFromAddressPattern;

    @Value("${resetPassword.fromAddressUser:reset.password}")
    private String resetPasswordFromAddressUser;

    @Value("${resetPassword.isClickTrackingEnabled:true}")
    private String resetPasswordIsClickTrackingEnabled;

    @Value("${resetPassword.isOpenTrackingEnabled:true}")
    private String resetPasswordIsOpenTrackingEnabled;

    @Value("${resetPassword.isTrackingEnabled:true}")
    private String resetPasswordIsTrackingEnabled;

    @Value("${resetPassword.subject:Reset your password}")
    private String resetPasswordSubject;

    @Value("${resetPassword.tag1:RESET_PASSWORD}")
    private String resetPasswordTag1;

    @Override
    @Bean(name = "wastedTimeBaseUrl")
    public String wastedTimeBaseUrl() {
        return baseUrl;
    }

    @Override
    @Bean(name = "identityBaseUrl")
    public String identityBaseUrl() {
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
    public EmailPropertiesService emailPropertiesService() {
        EmailProperties contactUsProperties = new EmailProperties();
        contactUsProperties.put(EmailProperties.FROM_ADDRESS_DOMAIN, contactUsFromAddressDomain);
        contactUsProperties.put(EmailProperties.FROM_ADDRESS_PATTERN, contactUsFromAddressPattern);
        contactUsProperties.put(EmailProperties.FROM_ADDRESS_USER, contactUsFromAddressUser);
        contactUsProperties.put(EmailProperties.IS_CLICK_TRACKING_ENABLED, contactUsIsClickTrackingEnabled);
        contactUsProperties.put(EmailProperties.IS_OPEN_TRACKING_ENABLED, contactUsIsOpenTrackingEnabled);
        contactUsProperties.put(EmailProperties.IS_TRACKING_ENABLED, contactUsIsTrackingEnabled);
        contactUsProperties.put(EmailProperties.SUBJECT, contactUsSubject);
        contactUsProperties.put(EmailProperties.TAG_ONE, contactUsTag1);

        EmailProperties registrationProperties = new EmailProperties();
        registrationProperties.put(EmailProperties.CAMPAIGN, registrationActivationCampaign);
        registrationProperties.put(EmailProperties.FROM_ADDRESS_DOMAIN, registrationActivationFromAddressDomain);
        registrationProperties.put(EmailProperties.FROM_ADDRESS_PATTERN, registrationActivationFromAddressPattern);
        registrationProperties.put(EmailProperties.FROM_ADDRESS_USER, registrationActivationFromAddressUser);
        registrationProperties.put(EmailProperties.IS_CLICK_TRACKING_ENABLED, registrationActivationIsClickTrackingEnabled);
        registrationProperties.put(EmailProperties.IS_OPEN_TRACKING_ENABLED, registrationActivationIsOpenTrackingEnabled);
        registrationProperties.put(EmailProperties.IS_TRACKING_ENABLED, registrationActivationIsTrackingEnabled);
        registrationProperties.put(EmailProperties.SUBJECT, registrationActivationSubject);
        registrationProperties.put(EmailProperties.TAG_ONE, registrationActivationTag1);

        EmailProperties resetPasswordProperties = new EmailProperties();
        resetPasswordProperties.put(EmailProperties.CAMPAIGN, resetPasswordCampaign);
        resetPasswordProperties.put(EmailProperties.FROM_ADDRESS_DOMAIN, resetPasswordFromAddressDomain);
        resetPasswordProperties.put(EmailProperties.FROM_ADDRESS_PATTERN, resetPasswordFromAddressPattern);
        resetPasswordProperties.put(EmailProperties.FROM_ADDRESS_USER, resetPasswordFromAddressUser);
        resetPasswordProperties.put(EmailProperties.IS_CLICK_TRACKING_ENABLED, resetPasswordIsClickTrackingEnabled);
        resetPasswordProperties.put(EmailProperties.IS_OPEN_TRACKING_ENABLED, resetPasswordIsOpenTrackingEnabled);
        resetPasswordProperties.put(EmailProperties.IS_TRACKING_ENABLED, resetPasswordIsTrackingEnabled);
        resetPasswordProperties.put(EmailProperties.SUBJECT, resetPasswordSubject);
        resetPasswordProperties.put(EmailProperties.TAG_ONE, resetPasswordTag1);

        Map<String, EmailProperties> emailPropertiesMap = newHashMap();

        emailPropertiesMap.put(REGISTRATION_ACTIVATION.name(), registrationProperties);
        emailPropertiesMap.put(CONTACT_US.name(), contactUsProperties);
        emailPropertiesMap.put(RESET_PASSWORD.name(), resetPasswordProperties);

        return new SpringInjectedEmailPropertiesService(emailPropertiesMap);
    }

}
