package com.solvedbysunrise.identity.data.dao.email;

import com.solvedbysunrise.identity.WastedtimeApplication;
import com.solvedbysunrise.identity.config.TestConfiguration;
import com.solvedbysunrise.identity.data.dao.IntegrationTestForBasicDao;
import com.solvedbysunrise.identity.data.entity.jpa.email.EmailProperty;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import static com.solvedbysunrise.identity.data.entity.jpa.email.EmailType.CONTACT_US;
import static com.solvedbysunrise.identity.data.entity.jpa.email.EmailType.RESET_PASSWORD;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

@Rollback
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {WastedtimeApplication.class, TestConfiguration.class})
@IntegrationTest
public class EmailPropertyDaoIntegrationTest extends IntegrationTestForBasicDao <EmailPropertyDao, EmailProperty, Long> {

    private static final String NAME = "name";
    private static final String VALUE = "value";

    @Autowired
    private EmailPropertyDao emailPropertyDao;

    private EmailProperty entityToSave = new EmailProperty();
    private EmailProperty entityToLookup = new EmailProperty();

    @Before
    public void setUp() throws Exception {
        entityToSave.setType(CONTACT_US);
        entityToSave.setName(NAME);
        entityToSave.setValue(VALUE);

        entityToLookup.setType(RESET_PASSWORD);
        entityToLookup.setName(NAME);
        entityToLookup.setValue(VALUE);
        emailPropertyDao.save(entityToLookup);
    }

    @Test
    public void findByEmailType_will_return_property() throws Exception {
        Collection<EmailProperty> properties = emailPropertyDao.findByType(RESET_PASSWORD);
        assertThat(properties, contains(entityToLookup));
    }

    @Test
    public void findByEmailType_will_return_only_one_property() throws Exception {
        Collection<EmailProperty> properties = emailPropertyDao.findByType(RESET_PASSWORD);
        assertThat(properties, hasSize(1));
    }

    @Override
    public EmailPropertyDao getDao() {
        return emailPropertyDao;
    }

    @Override
    public EmailProperty entityToSave() {
        return entityToSave;
    }

    @Override
    public EmailProperty entityToDelete() {
        return entityToLookup;
    }

    @Override
    public EmailProperty entityToCompare() {
        return entityToLookup;
    }

    @Override
    public Long entityIdToLookup() {
        return entityToLookup.getId();
    }
}