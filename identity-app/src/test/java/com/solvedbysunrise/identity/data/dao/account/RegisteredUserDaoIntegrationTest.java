package com.solvedbysunrise.identity.data.dao.account;

import com.solvedbysunrise.identity.WastedtimeApplication;
import com.solvedbysunrise.identity.config.TestConfiguration;
import com.solvedbysunrise.identity.data.dao.IntegrationTestForBasicDao;
import com.solvedbysunrise.identity.data.entity.jpa.account.BasicRegisteredEntity;
import com.solvedbysunrise.identity.data.entity.jpa.user.RegisteredUser;
import org.hamcrest.CoreMatchers;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static com.solvedbysunrise.identity.data.entity.jpa.user.PhoneType.Mobile;
import static java.util.Locale.CANADA_FRENCH;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@Rollback
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {WastedtimeApplication.class, TestConfiguration.class})
@IntegrationTest
public class RegisteredUserDaoIntegrationTest extends IntegrationTestForBasicDao<RegisteredUserDao, RegisteredUser, Long> {

    private static final String USERNAME = "user";
    private static final String EMAIL = "me@domain.com";
    private static final String PASSWORD = "password";
    private static final String PASSWORD_VERSION = "v2";
    private static final String DISPLAY_NAME = "DisplayName";
    private static final String LAST_NAME = "Bartish";
    private static final String GIVEN_NAMES = "Arran";
    private static final String PRIMARY_PHONE = "+198473992";

    @Autowired
    private RegisteredUserDao registeredUserDao;

    private RegisteredUser entityToSave;

    private RegisteredUser entityToLookup;

    @Before
    public void setup() {
        entityToSave = new RegisteredUser();
        setValues(entityToSave);
        entityToLookup = new RegisteredUser();
        setValues(entityToLookup);
        registeredUserDao.save(entityToLookup);
    }

    @Test
    public void findByPrimaryEmail_will_return_user_to_lookup() throws Exception {
        RegisteredUser account = registeredUserDao.findByPrimaryEmail(entityToLookup.getPrimaryEmail());
        assertThat(account, is(entityToLookup));
    }

    @Override
    public RegisteredUserDao getDao() {
        return registeredUserDao;
    }

    @Override
    public RegisteredUser entityToSave() {
        return entityToSave;
    }

    @Override
    public RegisteredUser entityToDelete() {
        return entityToLookup;
    }

    @Override
    public RegisteredUser entityToCompare() {
        return entityToLookup;
    }

    @Override
    public Long entityIdToLookup() {
        return entityToLookup.getId();
    }

    private void setValues(final RegisteredUser entity) {
        entity.setUsername(USERNAME);
        entity.setPrimaryEmail(EMAIL);
        entity.setPassword(PASSWORD);
        entity.setPasswordVersion(PASSWORD_VERSION);
        entity.setLanguageCode(CANADA_FRENCH.getLanguage());
        entity.setDisplayName(DISPLAY_NAME);
        entity.setDateOfBirth(DateTime.now().minusYears(23).toDate());
        entity.setFamilyName(LAST_NAME);
        entity.setGivenNames(GIVEN_NAMES);
        entity.setPrimaryPhone(PRIMARY_PHONE);
        entity.setPrimaryPhoneType(Mobile);
        entity.setResidenceCountryCode(CANADA_FRENCH.getCountry());
        entity.setCountryCode(CANADA_FRENCH.getCountry());
        entity.setLocale(CANADA_FRENCH);
    }
}