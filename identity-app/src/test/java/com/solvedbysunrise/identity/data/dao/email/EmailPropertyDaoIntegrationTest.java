package com.solvedbysunrise.identity.data.dao.email;

import com.solvedbysunrise.identity.WastedtimeApplication;
import com.solvedbysunrise.identity.config.TestConfiguration;
import com.solvedbysunrise.identity.data.dao.IntegrationTestForBasicDao;
import com.solvedbysunrise.identity.data.entity.jpa.email.EmailProperty;
import com.solvedbysunrise.identity.data.entity.jpa.email.EmailPropertyId;
import com.solvedbysunrise.identity.data.entity.jpa.email.EmailType;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@Rollback
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {WastedtimeApplication.class, TestConfiguration.class})
@IntegrationTest
public class EmailPropertyDaoIntegrationTest extends IntegrationTestForBasicDao <EmailPropertyDao, EmailProperty, EmailPropertyId> {

    private static final String NAME = "name";
    private static final String VALUE = "value";


    @Autowired
    private EmailPropertyDao emailPropertyDao;

    private EmailProperty entityToSave = new EmailProperty();
    private EmailProperty entityToLookup = new EmailProperty();

    @Before
    public void setUp() throws Exception {
        EmailPropertyId idToSave = new EmailPropertyId();
        idToSave.setType(EmailType.CONTACT_US);
        idToSave.setName(NAME);
        entityToSave.setValue(VALUE);
        entityToSave.setEmailPropertyId(idToSave);

        EmailPropertyId idToLookup = new EmailPropertyId();
        idToLookup.setType(EmailType.RESET_PASSWORD);
        idToLookup.setName(NAME);
        entityToLookup.setValue(VALUE);
        entityToLookup.setEmailPropertyId(idToLookup);
        emailPropertyDao.save(entityToLookup);
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
    public EmailPropertyId entityIdToLookup() {
        return entityToLookup.getEmailPropertyId();
    }
}