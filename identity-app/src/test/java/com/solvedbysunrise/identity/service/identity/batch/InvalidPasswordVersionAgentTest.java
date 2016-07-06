package com.solvedbysunrise.identity.service.identity.batch;

import com.solvedbysunrise.identity.data.dao.account.RegisteredEntityDao;
import com.solvedbysunrise.identity.data.dto.ApplicationProperties;
import com.solvedbysunrise.identity.data.entity.jpa.account.BasicRegisteredEntity;
import com.solvedbysunrise.identity.service.InvalidPasswordVersionAgent;
import com.solvedbysunrise.identity.service.SendEmailManager;
import com.solvedbysunrise.identity.service.properties.ApplicationPropertiesService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.solvedbysunrise.identity.data.dto.ApplicationProperties.Key.CURRENT_PASSWORD_VERSION;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InvalidPasswordVersionAgentTest {

    private static final String PASSWORD_VERSION = "1";
    private static final Long ENTITY_ONE = 1L;
    private static final Long ENTITY_TWO = 2L;
    @Mock
    private RegisteredEntityDao registeredEntityDao;

    @Mock
    private SendEmailManager sendEmailManager;

    @Mock
    private ApplicationPropertiesService applicationPropertiesService;

    @InjectMocks
    private InvalidPasswordVersionAgent invalidPasswordVersionAgent;

    @Test
    public void findAccountsAndNotifyUserOfPasswordUpgrade_Will_Reset_Passwords_When_Accounts_Are_Returned() {
        when(applicationPropertiesService.getApplicationProperties()).thenReturn(buildApplicationProperties());
        when(registeredEntityDao.findAllAccountsWithPasswordVersionAndNoResetPassword(PASSWORD_VERSION)).thenReturn(buildEntityList());

        invalidPasswordVersionAgent.findAccountsAndNotifyUserOfPasswordUpgrade();

        verify(sendEmailManager).sendPasswordReset(ENTITY_ONE);
        verify(sendEmailManager).sendPasswordReset(ENTITY_TWO);

    }

    private ApplicationProperties buildApplicationProperties(){
        ApplicationProperties applicationProperties = new ApplicationProperties();
        applicationProperties.put(CURRENT_PASSWORD_VERSION.getKey(), PASSWORD_VERSION);
        return applicationProperties;
    }

    private List<BasicRegisteredEntity> buildEntityList() {
        final BasicRegisteredEntity registeredEntity1 = new BasicRegisteredEntity();
        registeredEntity1.setId(ENTITY_ONE);
        final BasicRegisteredEntity registeredEntity2 = new BasicRegisteredEntity();
        registeredEntity2.setId(ENTITY_TWO);
        final List<BasicRegisteredEntity> registeredEntities = newArrayList();
        registeredEntities.add(registeredEntity1);
        registeredEntities.add(registeredEntity2);
        return  registeredEntities;
    }

}
