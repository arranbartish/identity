package com.solvedbysunrise.identity.data.dao.email;

import com.solvedbysunrise.identity.WastedtimeApplication;
import com.solvedbysunrise.identity.config.TestConfiguration;
import com.solvedbysunrise.identity.data.dao.IntegrationTestForBasicDao;
import com.solvedbysunrise.identity.data.entity.jpa.email.EmailEvent;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

import static org.joda.time.DateTime.now;
import static org.junit.Assert.*;

@Rollback
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {WastedtimeApplication.class, TestConfiguration.class})
@IntegrationTest
public class EmailEventDaoIntegrationTest extends IntegrationTestForBasicDao<EmailEventDao, EmailEvent, Long> {

    private static final Long EMAIL_ID = 1L;
    private static final String OPEN_EVENT_TYPE = "OPEN";
    private static final String READ_EVENT_TYPE = "READ";

    @Autowired
    private EmailEventDao emailEventDao;

    private EmailEvent entityToSave = new EmailEvent();
    private EmailEvent entityToLookup = new EmailEvent();

    @Before
    public void setUp() throws Exception {
        entityToSave.setEmailId(EMAIL_ID);
        entityToSave.setEventType(OPEN_EVENT_TYPE);
        entityToSave.setRemoteDate(new Timestamp(now().getMillis()));

        entityToLookup.setEmailId(EMAIL_ID);
        entityToLookup.setEventType(READ_EVENT_TYPE);
        entityToLookup.setRemoteDate(new Timestamp(now().getMillis()));
        emailEventDao.save(entityToLookup);
    }

    @Override
    public EmailEventDao getDao() {
        return emailEventDao;
    }

    @Override
    public EmailEvent entityToSave() {
        return entityToSave;
    }

    @Override
    public EmailEvent entityToDelete() {
        return entityToLookup;
    }

    @Override
    public EmailEvent entityToCompare() {
        return entityToLookup;
    }

    @Override
    public Long entityIdToLookup() {
        return entityToLookup.getId();
    }
}