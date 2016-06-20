package com.solvedbysunrise.identity.data.dao.email;

import com.solvedbysunrise.identity.WastedtimeApplication;
import com.solvedbysunrise.identity.config.TestConfiguration;
import com.solvedbysunrise.identity.data.dao.IntegrationTestForBasicDao;
import com.solvedbysunrise.identity.data.entity.jpa.email.BasicEmail;
import com.solvedbysunrise.identity.data.entity.jpa.email.Email;
import com.solvedbysunrise.identity.data.entity.jpa.email.EmailType;
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

import java.util.Calendar;

import static com.solvedbysunrise.identity.data.entity.jpa.email.EmailType.CONTACT_US;
import static java.util.Locale.CANADA;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.number.OrderingComparison.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;

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
    private static final String CONFIRMATION_ID = "ANOTHER_ID";

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

    @Test
    public void findByGuid_will_return_email() throws Exception {
        BasicEmail email = basicEmailDao.findByGuid(entityToLookup.getGuid());
        assertThat(email, is(entityToLookup));
    }

    @Test
    public void findByConfirmationId_will_return_email() throws Exception {
        BasicEmail email = basicEmailDao.findByConfirmationId(entityToLookup.getConfirmationId());
        assertThat(email, is(entityToLookup));
    }

    @Test
    public void entity_will_have_a_creation_date_when_created() throws Exception {
        BasicEmail email = basicEmailDao.findOne(entityToLookup.getId());
        assertThat(email.getCreateDate(), allOf(
                is( notNullValue()),
                lessThanOrEqualTo(Calendar.getInstance())));
    }

    @Test
    public void entity_will_have_a_null_update_date_when_only_created() throws Exception {
        BasicEmail email = basicEmailDao.findOne(entityToLookup.getId());
        assertThat(email.getUpdateDate(), is( notNullValue()));
    }

    @Test
    public void entity_will_have_an_update_date_after_the_created_date_when_updated() throws Exception {
        Long id = entityToLookup.getId();
        BasicEmail email = basicEmailDao.findOne(id);
        Calendar originalCreateDate = email.getCreateDate();
        email.setTextPayload("something else");
        basicEmailDao.save(email);
        email = basicEmailDao.findOne(id);
        assertThat(email.getUpdateDate(), allOf(
                is( notNullValue()),
                greaterThanOrEqualTo(originalCreateDate)));
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
        entity.setConfirmationId(CONFIRMATION_ID);
        entity.setType(type);
        entity.setSentDate(DateTime.now().toDate());
        entity.setToAddress(ADDRESS);
    }
}
