package com.solvedbysunrise.identity.data.dao.email;

import com.solvedbysunrise.identity.WastedtimeApplication;
import com.solvedbysunrise.identity.config.TestConfiguration;
import com.solvedbysunrise.identity.data.dao.IntegrationTestForBasicDao;
import com.solvedbysunrise.identity.data.entity.jpa.email.ResetPasswordEmail;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static com.solvedbysunrise.identity.data.entity.jpa.email.EmailType.RESET_PASSWORD;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@Rollback
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {WastedtimeApplication.class, TestConfiguration.class})
@IntegrationTest
public class ResetPasswordEmailDaoIntegrationTest extends IntegrationTestForBasicDao<ResetPasswordEmailDao, ResetPasswordEmail, Long> {


    private static final String RESET_GUID = "RESET_GUID";
    @Autowired
    private ResetPasswordEmailDao resetPasswordEmailDao;

    private ResetPasswordEmail entityToSave = new ResetPasswordEmail();
    private ResetPasswordEmail entityToLookup = new ResetPasswordEmail();

    @Before
    public void setup() {
        setValues(entityToSave);
        setValues(entityToLookup);
        resetPasswordEmailDao.save(entityToLookup);
    }

    @Test
    public void findByGuid_will_return_email() throws Exception {
        ResetPasswordEmail email = resetPasswordEmailDao.findByGuid(entityToLookup.getGuid());
        assertThat(email, is(entityToLookup));
    }

    @Test
    public void findByConfirmationId_will_return_email() throws Exception {
        ResetPasswordEmail email = resetPasswordEmailDao.findByConfirmationId(entityToLookup.getConfirmationId());
        assertThat(email, is(entityToLookup));
    }

    @Override
    public ResetPasswordEmailDao getDao() {
        return resetPasswordEmailDao;
    }

    @Override
    public ResetPasswordEmail entityToSave() {
        return entityToSave;
    }

    @Override
    public ResetPasswordEmail entityToDelete() {
        return entityToLookup;
    }

    @Override
    public ResetPasswordEmail entityToCompare() {
        return entityToLookup;
    }

    @Override
    public Long entityIdToLookup() {
        return entityToLookup.getId();
    }

    public void setValues(ResetPasswordEmail entity) {
        BasicEmailDaoIntegrationTest.setValues(entity, RESET_PASSWORD);
        entity.setResetPasswordGuid(RESET_GUID);
    }
}