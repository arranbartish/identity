package com.solvedbysunrise.identity.controller;

import com.solvedbysunrise.identity.WastedtimeApplication;
import com.solvedbysunrise.identity.config.TestConfiguration;
import com.solvedbysunrise.identity.data.dao.account.RegisteredEntityDao;
import com.solvedbysunrise.identity.data.dto.EmailProperties;
import com.solvedbysunrise.identity.mock.service.MailGunMessageCaptor;
import com.solvedbysunrise.identity.service.EmailPropertiesService;
import com.solvedbysunrise.identity.service.MailGunEmailService;
import com.solvedbysunrise.identity.service.dtto.Account;
import com.solvedbysunrise.identity.service.dtto.Address;
import com.solvedbysunrise.identity.service.dtto.PersonalRegistrationRequest;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.util.Date;

import static com.google.common.collect.Lists.newArrayList;
import static com.solvedbysunrise.identity.data.entity.jpa.email.EmailType.REGISTRATION_ACTIVATION;
import static com.solvedbysunrise.identity.service.identity.enums.Gender.MALE;
import static com.solvedbysunrise.identity.util.UriUtil.getFullyQualifiedUriPattern;
import static java.util.Locale.CANADA_FRENCH;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {WastedtimeApplication.class, TestConfiguration.class})
@WebIntegrationTest
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
public class RegistraionControllerIntegrationTest {

    private static final Logger LOGGER = getLogger(RegistraionControllerIntegrationTest.class);

    private static final String REGISTRATION_RESOURCE = "/register";
    private static final Date EXPECTED_BIRTHDAY = DateTime.now().minusYears(30).toDate();

    private static final PersonalRegistrationRequest REGISTRATION_REQUEST = buildRegistrationRequest();
    private static final Account EXPECTED_ACCOUNT = buildAccount();

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RegisteredEntityDao registeredEntityDao;

    @Autowired
    private String identityBaseUrl;

    @Autowired
    private MailGunMessageCaptor captor;

    @Autowired
    private EmailPropertiesService emailPropertiesService;

    private URI registrationUri;

    @Before
    public void setup() {
        registrationUri = new UriTemplate(getFullyQualifiedUriPattern(identityBaseUrl, REGISTRATION_RESOURCE)).expand();
    }

    @Test
    public void will_register_account_with_expected_values() {
        RequestEntity<PersonalRegistrationRequest> request = postRequestForRegistration(REGISTRATION_REQUEST);

        URI uri = restTemplate.postForLocation(request.getUrl(), request);
        ResponseEntity<Account> result = restTemplate.getForEntity(uri, Account.class);

        assertThat(result.getBody(), is(EXPECTED_ACCOUNT));
    }

    @Test
    public void will_send_registration_mail_with_expected_values() {
        RequestEntity<PersonalRegistrationRequest> request = postRequestForRegistration(REGISTRATION_REQUEST);
        EmailProperties emailProperties = emailPropertiesService.getEmailProperties(REGISTRATION_ACTIVATION);

        URI uri = restTemplate.postForLocation(request.getUrl(), request);
        MultiValueMap<String, Object> messageMap = captor.getMessageMap();
        assertThat(messageMap, allOf(
                is( notNullValue()),
                hasEntry("DOMAIN", newArrayList("email.example.com")),
                hasEntry(MailGunEmailService.CAMPAIGN, newArrayList(emailProperties.getCampaign())),
                hasEntry(MailGunEmailService.MAILGUN_FROM, newArrayList("Accounts <registrations@email.example.com>")),
                hasEntry(MailGunEmailService.MAILGUN_SUBJECT, newArrayList(emailProperties.getSubject())),
                hasEntry(MailGunEmailService.MAILGUN_TO, newArrayList("arran@example.com")),
                hasEntry(is(MailGunEmailService.MAILGUN_HTML), is(notNullValue())),
                hasEntry(MailGunEmailService.TRACKING, newArrayList(emailProperties.isTrackingEnabled())),
                hasEntry(MailGunEmailService.TRACKING_OPEN, newArrayList(emailProperties.isOpenTrackingEnabled())),
                hasEntry(MailGunEmailService.TRACKING_CLICK, newArrayList(emailProperties.isClickTrackingEnabled()))
        ));
    }

    private RequestEntity<PersonalRegistrationRequest> postRequestForRegistration(PersonalRegistrationRequest registrationRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON_UTF8);
        return new RequestEntity<>(registrationRequest, headers, POST, registrationUri);
    }

    private static PersonalRegistrationRequest buildRegistrationRequest() {
        PersonalRegistrationRequest request = new PersonalRegistrationRequest();
        request.setUsername("arran");
        request.setCountryCode(CANADA_FRENCH.getCountry());
        request.setLanguageCode(CANADA_FRENCH.getLanguage());
        request.setAddress(buildAddress());
        request.setAgreedToTerms(true);
        request.setEmail("arran@example.com");
        request.setDateOfBirth(EXPECTED_BIRTHDAY);
        request.setFamilyName("BArtish");
        request.setGivenNames("Arran");
        request.setGender(MALE);
        request.setPassword("some password");
        request.setPhoneNumber("38473838");
        return request;
    }

    private static Address buildAddress() {
        Address address = new Address();
        address.setCountryCode(CANADA_FRENCH.getCountry());
        address.setCity("Terrebonne");
        address.setPostOrZipCode("JFH JDH");
        address.setStateOrProvice("Quebec");
        address.setStreet("Some Street address");
        return  address;
    }

    private static Account buildAccount() {
        Account account = new Account();
        account.setId(1L);
        account.setDisplayName("arran");
        return account;
    }
}