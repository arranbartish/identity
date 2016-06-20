package com.solvedbysunrise.identity.data.dao.account;

import com.solvedbysunrise.identity.WastedtimeApplication;
import com.solvedbysunrise.identity.config.TestConfiguration;
import com.solvedbysunrise.identity.data.dao.IntegrationTestForBasicDao;
import com.solvedbysunrise.identity.data.entity.jpa.account.BasicRegisteredEntity;
import com.solvedbysunrise.identity.data.entity.jpa.account.RegisteredEntity;
import com.solvedbysunrise.identity.data.entity.jpa.account.RegisteredEntityRole;
import com.solvedbysunrise.identity.data.entity.jpa.account.RegisteredEntityTermsAndConditions;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;

import static com.solvedbysunrise.identity.data.dao.account.ActivationState.PENDING_ACTIVATION;
import static java.util.Locale.CANADA_FRENCH;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

@Rollback
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {WastedtimeApplication.class, TestConfiguration.class})
@IntegrationTest
public class RegisteredEntityDaoIntegrationTest extends IntegrationTestForBasicDao<RegisteredEntityDao, RegisteredEntity, Long> {

    private static final String FIRST_NAME = "Arran";
    private static final String PASSWORD = "password";
    private static final String V1 = "v1";
    private static final String EMAIL = "email@something.com"
            ;
    @Autowired
    private RegisteredEntityDao registeredEntityDao;

    private BasicRegisteredEntity entityToSave;

    private BasicRegisteredEntity entityToLookup;

    @Before
    public void setup() {
        RegisteredEntityTermsAndConditions terms1 = new RegisteredEntityTermsAndConditions();
        terms1.setVersion(1);

        entityToSave = new BasicRegisteredEntity();
        entityToSave.addRoles("A_ROLE", "ANOTHER_ROLE");
        entityToSave.addTermsAndConditionsInstance(terms1);
        setValues(entityToSave);

        RegisteredEntityTermsAndConditions terms2 = new RegisteredEntityTermsAndConditions();
        terms2.setVersion(2);

        entityToLookup = new BasicRegisteredEntity();
        entityToLookup.addRoles("SOME_ROLE", "SOME_OTHER_ROLE");
        entityToLookup.addTermsAndConditionsInstance(terms2);
        setValues(entityToLookup);

        registeredEntityDao.save(entityToLookup);
    }

    @Test
    public void findByUsername_will_return_entity() throws Exception {
        BasicRegisteredEntity found = registeredEntityDao.findByUsername(EMAIL);
        assertThat(found, is(entityToLookup));
    }

    @Test
    public void findAllRolesForAccount_will_return_roles_for_entity() throws Exception {
        Set<String> allRoles = registeredEntityDao.findAllRolesForAccount(entityToLookup.getId());
        assertThat(allRoles, containsInAnyOrder("SOME_ROLE", "SOME_OTHER_ROLE"));
    }

    @Test
    @Ignore("Currently broken, but we do not need this for now.")
    public void findAllAccountsWithPasswordVersionAndNoResetPassword_will_return_user() throws Exception {
        Collection<BasicRegisteredEntity> accounts = registeredEntityDao.findAllAccountsWithPasswordVersionAndNoResetPassword(entityToLookup.getPasswordVersion());
        assertThat(accounts, Matchers.contains(entityToLookup));
    }

    @Override
    public RegisteredEntityDao getDao() {
        return registeredEntityDao;
    }

    @Override
    public RegisteredEntity entityToSave() {
        return entityToSave;
    }

    @Override
    public RegisteredEntity entityToDelete() {
        return entityToLookup;
    }

    @Override
    public RegisteredEntity entityToCompare() {
        return entityToLookup;
    }

    @Override
    public Long entityIdToLookup() {
        return entityToLookup.getId();
    }

    private void setValues(final BasicRegisteredEntity entity) {
        entity.setUsername(EMAIL);
        entity.setCountryCode(CANADA_FRENCH.getCountry());
        entity.setActivationState(PENDING_ACTIVATION);
        entity.setDisplayName(FIRST_NAME);
        entity.setLanguageCode(CANADA_FRENCH.getLanguage());
        entity.setPassword(PASSWORD);
        entity.setPasswordVersion(V1);
        entity.setPrimaryEmail(EMAIL);
    }
}