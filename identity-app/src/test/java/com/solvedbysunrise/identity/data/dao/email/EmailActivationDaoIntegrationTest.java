package com.solvedbysunrise.identity.data.dao.email;

import com.solvedbysunrise.identity.WastedtimeApplication;
import com.solvedbysunrise.identity.config.TestConfiguration;
import com.solvedbysunrise.identity.data.dao.IntegrationTestForBasicDao;
import com.solvedbysunrise.identity.data.entity.jpa.ReflectiveEntity;
import com.solvedbysunrise.identity.data.entity.jpa.email.ActivationEmail;
import com.solvedbysunrise.identity.data.entity.jpa.email.EmailType;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

import static com.solvedbysunrise.identity.data.entity.jpa.email.EmailType.REGISTRATION_ACTIVATION;
import static org.junit.Assert.*;

@Rollback
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {WastedtimeApplication.class, TestConfiguration.class})
@IntegrationTest
public class EmailActivationDaoIntegrationTest extends IntegrationTestForBasicDao <EmailActivationDao, ActivationEmail, Long> {

    private static final String ACTIVATION_GUID = "GUID";

    @Autowired
    private EmailActivationDao emailActivationDao;

    private ActivationEmail entityToSave = new ActivationEmail();
    private ActivationEmail entityToLookup = new ActivationEmail();

    @Before
    public void setup() {
        setValues(entityToSave);
        setValues(entityToLookup);
        emailActivationDao.save(entityToLookup);
    }

    @Override
    public EmailActivationDao getDao() {
        return emailActivationDao;
    }

    @Override
    public ActivationEmail entityToSave() {
        return entityToSave;
    }

    @Override
    public ActivationEmail entityToDelete() {
        return entityToLookup;
    }

    @Override
    public ActivationEmail entityToCompare() {
        return entityToLookup;
    }

    @Override
    public Long entityIdToLookup() {
        return entityToLookup.getId();
    }

    public void setValues(ActivationEmail entity) {
        BasicEmailDaoIntegrationTest.setValues(entity, REGISTRATION_ACTIVATION);
        entity.setActivationGuid(ACTIVATION_GUID);
    }
}