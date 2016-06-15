package com.solvedbysunrise.identity.data.dao.email;

import com.solvedbysunrise.identity.WastedtimeApplication;
import com.solvedbysunrise.identity.config.TestConfiguration;
import com.solvedbysunrise.identity.data.dao.IntegrationTestForBasicDao;
import com.solvedbysunrise.identity.data.entity.jpa.email.BasicEmail;
import com.solvedbysunrise.identity.data.entity.jpa.email.Email;
import com.solvedbysunrise.identity.data.entity.jpa.email.EmailType;
import org.hibernate.id.GUIDGenerator;
import org.hibernate.metamodel.binding.EntityIdentifier;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

import static com.solvedbysunrise.identity.data.entity.jpa.email.EmailType.CONTACT_US;
import static java.util.Locale.CANADA;
import static org.junit.Assert.*;

@Rollback
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {WastedtimeApplication.class, TestConfiguration.class})
@IntegrationTest
public class BasicEmailDaoIntegrationTest extends IntegrationTestForBasicDao <BasicEmailDao, BasicEmail, Long>{

    private static final String SOME_HTML = "html";
    private static final String SOME_TEXT = "text";
    private static final Long ENTITY_ID = 1L;
    private static final String ADDRESS = "you@domain.com";
    private static final String GUID = "GUID";

    @Autowired
    private BasicEmailDao basicEmailDao;

    private BasicEmail entityToSave;
    private BasicEmail entityToLookup;

    @Before
    public void setup() {
        entityToSave = new BasicEmail();
        setValues(entityToSave);
        entityToLookup = new BasicEmail();
        setValues(entityToLookup);
        basicEmailDao.save(entityToLookup);
    }

    @Override
    public BasicEmailDao getDao() {
        return basicEmailDao;
    }

    @Override
    public BasicEmail entityToSave() {
        return entityToSave;
    }

    @Override
    public BasicEmail entityToDelete() {
        return entityToLookup;
    }

    @Override
    public BasicEmail entityToCompare() {
        return entityToLookup;
    }

    @Override
    public Long entityIdToLookup() {
        return entityToLookup.getId();
    }

    public void setValues(BasicEmail entity) {
        setValues(entity, CONTACT_US);
    }

    static public void setValues(Email entity, EmailType type) {
        entity.setLocale(CANADA);
        entity.setHtmlPayload(SOME_HTML);
        entity.setTextPayload(SOME_TEXT);
        entity.setEntityId(ENTITY_ID);
        entity.setGuid(GUID);
        entity.setType(type);
        entity.setSentDate(DateTime.now().toDate());
        entity.setToAddress(ADDRESS);
    }
}
