package com.solvedbysunrise.identity.service;


import com.solvedbysunrise.identity.data.dto.ApplicationProperties;
import com.solvedbysunrise.identity.data.dto.EmailProperties;
import com.solvedbysunrise.identity.service.dtto.IdOnly;
import com.solvedbysunrise.identity.service.dtto.PreparedEmail;
import com.solvedbysunrise.identity.service.properties.ApplicationPropertiesService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Maps.newHashMap;
import static com.solvedbysunrise.identity.service.BasicAuthenticationBuilder.encodeUsernameAndPassword;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@Service
@Transactional
public class MailGunEmailService implements SendEmailService{

    public static final Logger LOGGER = getLogger(MailGunEmailService.class);
    public static final String MAILGUN_API = "api";
    public static final String MAILGUN_FROM = "from";
    public static final String MAILGUN_TO = "to";
    public static final String MAILGUN_SUBJECT = "subject";
    public static final String MAILGUN_TEXT = "text";

    public static final String MAILGUN_HTML = "html";
    public static final String TRACKING_OPEN = "o:tracking-opens";
    public static final String TRACKING = "o:tracking";
    public static final String TRACKING_CLICK = "o:tracking-clicks";
    public static final String CAMPAIGN = "o:campaign";
    public static final String TAG = "o:tag";

    public static final String REQUEST_URL_HEADER = "X-CUSTOM-HTTP-REQUEST-URL";
    public static final String CONTENT_TYPE_HEADER = "Content-Type";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String MAILGUN_PARAMETER_HEADER = "X-Mailgun-Variables";

    private final RestTemplate restTemplate;

    private final EmailPropertiesService emailPropertiesService;
    private final ApplicationPropertiesService applicationPropertiesService;

    @Autowired
    public MailGunEmailService(final RestTemplate restTemplate,
                                      final EmailPropertiesService emailPropertiesService,
                                      final ApplicationPropertiesService applicationPropertiesService) {
        this.restTemplate = restTemplate;
        this.emailPropertiesService = emailPropertiesService;
        this.applicationPropertiesService = applicationPropertiesService;
    }

    @Override
    public String sendMail(final PreparedEmail preparedEmail) {

        EmailProperties emailProperties = emailPropertiesService.getEmailProperties(preparedEmail.getType());
        ApplicationProperties applicationProperties = applicationPropertiesService.getApplicationProperties();

        final String mailGunApiWithDomain = format(applicationProperties.getMailGunApiURLPattern(),
                                     emailProperties.getFromAddressDomain());

        URI uri = fromHttpUrl(mailGunApiWithDomain).build().toUri();

        Map<String, String> payload = generatePayload(preparedEmail, emailProperties);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_FORM_URLENCODED);
        headers.add(AUTHORIZATION_HEADER, encodeUsernameAndPassword(MAILGUN_API,
                applicationProperties.getMailGunApiKey()));
        @SuppressWarnings("unchecked")
        RequestEntity request = new RequestEntity(payload, headers, GET, uri);

        ResponseEntity<IdOnly> response = restTemplate.exchange(request, IdOnly.class);

        return response.getBody().getId();

    }

    private Map<String, String> generatePayload(final PreparedEmail preparedEmail, final EmailProperties emailProperties) {
        Map<String, String> payload = newHashMap();

        payload.put(TRACKING, emailProperties.isTrackingEnabled());
        payload.put(TRACKING_CLICK, emailProperties.isClickTrackingEnabled());
        payload.put(TRACKING_OPEN, emailProperties.isOpenTrackingEnabled());
        payload.put("v:guid", preparedEmail.getGuid());

        if(isNotBlank(emailProperties.getCampaign())) {
            payload.put(CAMPAIGN, emailProperties.getCampaign());
        }

        final String fromAddress = format(emailProperties.getFromAddressPattern(),
                                          emailProperties.getFromAddressUser(),
                                        emailProperties.getFromAddressDomain());
        payload.put(MAILGUN_FROM, fromAddress);
        payload.put(MAILGUN_SUBJECT, emailProperties.getSubject());

        Set<String> emailTags = emailProperties.getTags();
        for (String emailTag : emailTags) {
            payload.put(TAG, emailTag);
        }

        payload.put(MAILGUN_TO, preparedEmail.getToAddress());
        if(isNotBlank(preparedEmail.getHtmlContent())) {
            payload.put(MAILGUN_HTML, preparedEmail.getHtmlContent());
        }
        if(isNotBlank(preparedEmail.getTextContent())) {
            payload.put(MAILGUN_TEXT, preparedEmail.getTextContent());
        }
        return payload;
    }
}
