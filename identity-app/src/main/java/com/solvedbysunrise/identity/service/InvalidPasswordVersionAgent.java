package com.solvedbysunrise.identity.service;

import com.solvedbysunrise.identity.data.dao.account.RegisteredEntityDao;
import com.solvedbysunrise.identity.data.dto.ApplicationProperties;
import com.solvedbysunrise.identity.data.entity.jpa.account.BasicRegisteredEntity;
import com.solvedbysunrise.identity.data.entity.jpa.account.RegisteredEntity;
import com.solvedbysunrise.identity.service.properties.ApplicationPropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class InvalidPasswordVersionAgent {

    private final RegisteredEntityDao registeredEntityDao;

    private final SendEmailManager sendEmailManager;

    private final ApplicationPropertiesService applicationPropertiesService;

    @Autowired
    public InvalidPasswordVersionAgent(final RegisteredEntityDao registeredEntityDao,
                                       final SendEmailManager sendEmailManager,
                                       final ApplicationPropertiesService applicationPropertiesService) {
        this.registeredEntityDao = registeredEntityDao;
        this.sendEmailManager = sendEmailManager;
        this.applicationPropertiesService = applicationPropertiesService;
    }


    public void findAccountsAndNotifyUserOfPasswordUpgrade(){
        ApplicationProperties applicationProperties = applicationPropertiesService.getApplicationProperties();
        String currentPasswordVersion = applicationProperties.getCurrentPasswordVersion();

        Collection<BasicRegisteredEntity> usersWithNonCurrentPasswordVersion = registeredEntityDao.findAllAccountsWithPasswordVersionAndNoResetPassword(currentPasswordVersion);
        usersWithNonCurrentPasswordVersion
                .parallelStream()
                .map(RegisteredEntity::getId)
                .forEach(sendEmailManager::sendPasswordReset);
    }
}
