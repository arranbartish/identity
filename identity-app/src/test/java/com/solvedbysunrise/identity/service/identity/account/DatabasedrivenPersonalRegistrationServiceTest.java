package com.solvedbysunrise.identity.service.identity.account;

import com.solvedbysunrise.identity.data.dao.account.RegisteredEntityDao;
import com.solvedbysunrise.identity.data.dao.account.RegisteredUserDao;
import com.solvedbysunrise.identity.data.dto.ApplicationProperties;
import com.solvedbysunrise.identity.data.entity.jpa.account.BasicRegisteredEntity;
import com.solvedbysunrise.identity.data.entity.jpa.user.RegisteredUser;
import com.solvedbysunrise.identity.service.DatabasedrivenPersonalRegistrationService;
import com.solvedbysunrise.identity.service.SendEmailManager;
import com.solvedbysunrise.identity.service.dtto.Address;
import com.solvedbysunrise.identity.service.dtto.PersonalRegistrationRequest;
import com.solvedbysunrise.identity.service.identity.enums.Gender;
import com.solvedbysunrise.identity.service.properties.ApplicationPropertiesService;
import com.solvedbysunrise.identity.service.security.ApiKeyService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.Locale;

import static com.solvedbysunrise.identity.service.identity.enums.Gender.MALE;
import static java.lang.Boolean.TRUE;
import static org.apache.commons.lang3.LocaleUtils.toLocale;
import static org.hamcrest.CoreMatchers.*;
import static org.joda.time.DateTime.parse;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DatabasedrivenPersonalRegistrationServiceTest {

	private static final String STREET = "209/28 Queensbridge St";

	private static final String CITY = "Melbourne";

	private static final String STATE = "Victoria";

	private static final String POST_CODE = "3006";

    private static final Locale LOCALE = toLocale("en_AU");

	private static final String COUNTRY_CODE = LOCALE.getCountry();

	private static final Date DATE_OF_BIRTH = parse("1981-03-20T00:00").toDate();

	private static final String EMAIL = "anemail@domain.com";

	private static final String GIVEN_NAMES = "James John";

	private static final String FAMILY_NAME = "Smith";

	private static final Gender GENDER = MALE;

	private static final String PASSWORD = "APASSWORD";
	
	private static final String ENCODED_PASSWORD = "AN_ENCODED_PASSWORD";

	private static final String PHONE_NUMBER = "0394833726";

	private static final Boolean AGREED_TO_TERMS = TRUE;

    private static final String CURRENT_PASSWORD_VERSION = "1";

    @Mock
	private PasswordEncoder passwordEncoder;
	
	@Mock
	private RegisteredUserDao registeredUserDao;

    @Mock
    private RegisteredEntityDao entityDao;

    @Mock
    private SendEmailManager sendEmailManager;

    @Mock
    private ApplicationPropertiesService applicationPropertiesService;

    @Mock
    private ApplicationProperties applicationProperties;

    @Mock
    private BasicRegisteredEntity registeredEntity;

    @Mock
    private ApiKeyService apiKeyService;

	@Captor
	private ArgumentCaptor<RegisteredUser> userCaptor;

    @Captor
    private ArgumentCaptor<BasicRegisteredEntity> entityCaptor;

	@InjectMocks
	private DatabasedrivenPersonalRegistrationService personalRegistrationService;

	@Before
	public void setup() {
		when(passwordEncoder.encode(Mockito.anyString())).thenReturn(ENCODED_PASSWORD);
        when(applicationPropertiesService.getApplicationProperties()).thenReturn(applicationProperties);
        when(applicationProperties.getCurrentPasswordVersion()).thenReturn(CURRENT_PASSWORD_VERSION);
        when(entityDao.findOne(null/*hibernate sets this so for it's null when mocked*/)).thenReturn(registeredEntity);
	}
	
	@Test
	public void createAccountWillCallSaveAndUpdateWhenPassedACompleteRequest() {

		PersonalRegistrationRequest request = buildRegistrationRequest();
		personalRegistrationService.createAccount(request);
		
		verify(registeredUserDao).save(userCaptor.capture());
        verify(entityDao).save(entityCaptor.capture());
		
		RegisteredUser registeredUser = userCaptor.getValue();
	    assertThat(registeredUser.getPrimaryEmail(), is(EMAIL));
	    assertThat(registeredUser.getDateOfBirth(), is(DATE_OF_BIRTH));
	    assertThat(registeredUser.getPassword(), is(ENCODED_PASSWORD));
        assertThat(registeredUser.getPasswordVersion(), is(CURRENT_PASSWORD_VERSION));
	    assertThat(registeredUser.getDisplayName(), nullValue());
	    assertThat(registeredUser.getGivenNames(), is(GIVEN_NAMES));
	    assertThat(registeredUser.getFamilyName(), is(FAMILY_NAME));
	    assertThat(registeredUser.getPrimaryPhone(), is(PHONE_NUMBER));
	    assertThat(registeredUser.getPrimaryPhoneType(), notNullValue());

        verify(sendEmailManager).sendRegistrationActivation(registeredUser.getId());
	}
	
	private PersonalRegistrationRequest buildRegistrationRequest() {
		PersonalRegistrationRequest request = new PersonalRegistrationRequest();
		request.setAddress(buildAddress());
		request.setDateOfBirth(DATE_OF_BIRTH);
		request.setEmail(EMAIL);
		request.setGivenNames(GIVEN_NAMES);
		request.setFamilyName(FAMILY_NAME);
		request.setGender(GENDER);
		request.setPassword(PASSWORD);
		request.setPhoneNumber(PHONE_NUMBER);
		request.setAgreedToTerms(AGREED_TO_TERMS);
        request.setCountryCode(LOCALE.getCountry());
        request.setLanguageCode(LOCALE.getLanguage());
		return request;
	}
	
	private Address buildAddress() {
		Address address = new Address();
		address.setStreet(STREET);
		address.setCity(CITY);
		address.setStateOrProvice(STATE);
		address.setPostOrZipCode(POST_CODE);
		address.setCountryCode(COUNTRY_CODE);
		return address;
	}

}
