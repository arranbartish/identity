package com.solvedbysunrise.identity.service;

import com.solvedbysunrise.identity.data.dao.account.RegisteredEntityDao;
import com.solvedbysunrise.identity.data.dao.account.RegisteredUserDao;
import com.solvedbysunrise.identity.data.dto.ApplicationProperties;
import com.solvedbysunrise.identity.data.entity.jpa.account.BasicRegisteredEntity;
import com.solvedbysunrise.identity.data.entity.jpa.user.RegisteredUser;
import com.solvedbysunrise.identity.service.dtto.PersonalRegistrationRequest;
import com.solvedbysunrise.identity.service.properties.ApplicationPropertiesService;
import com.solvedbysunrise.identity.service.security.ApiKeyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.solvedbysunrise.identity.data.entity.jpa.user.PhoneType.MOBILE;
import static com.solvedbysunrise.identity.internationalization.LocaleUtil.convertToLocale;
import static com.solvedbysunrise.identity.service.identity.enums.KnownRoles.REGISTERED_USER;

@Service
@Transactional
public class DatabasedrivenPersonalRegistrationService extends AbstractIdentityService
        implements PersonalRegistrationService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(DatabasedrivenPersonalRegistrationService.class);

    private final PasswordEncoder passwordEncoder;

    private final RegisteredEntityDao entityDao;

    private final RegisteredUserDao userDao;

    private final SendEmailManager sendEmailManager;

    private final ApiKeyService apiKeyService;

    private final ApplicationPropertiesService applicationPropertiesService;

    private static final String DEFAULT_ROLE = REGISTERED_USER.name();

    @Autowired
    public DatabasedrivenPersonalRegistrationService(
            final PasswordEncoder passwordEncoder,
            final RegisteredUserDao registeredUserDao,
            final RegisteredEntityDao entityDao,
            final SendEmailManager sendEmailManager,
            final ApiKeyService apiKeyService,
            final ApplicationPropertiesService applicationPropertiesService) {
        this.passwordEncoder = passwordEncoder;
        this.userDao = registeredUserDao;
        this.entityDao = entityDao;
        this.apiKeyService = apiKeyService;
        this.sendEmailManager = sendEmailManager;
        this.applicationPropertiesService = applicationPropertiesService;
    }

    @Override
    public void createAccount(PersonalRegistrationRequest accountDetails) {
        LOGGER.debug(String.format("createAccount(%s)",
                accountDetails.toString()));

        ApplicationProperties applicationProperties = applicationPropertiesService.getApplicationProperties();

        String encodedPassword = passwordEncoder.encode(
                accountDetails.getPassword());

        RegisteredUser registeredUser = new RegisteredUser();
        registeredUser.setUsername(accountDetails.getUsername());
        registeredUser.setDateOfBirth(accountDetails.getDateOfBirth());
        registeredUser.setGivenNames(accountDetails.getGivenNames());
        registeredUser.setFamilyName(accountDetails.getFamilyName());
        registeredUser.setPassword(encodedPassword);
        registeredUser.setPasswordVersion(applicationProperties.getCurrentPasswordVersion());
        registeredUser.setDisplayName(accountDetails.getDisplayName());
        registeredUser.setPrimaryEmail(accountDetails.getEmail());
        registeredUser.setPrimaryPhone(accountDetails.getPhoneNumber());
        registeredUser.setPrimaryPhoneType(MOBILE);
        registeredUser.setResidenceCountryCode(accountDetails.getCountryCode());
        registeredUser.setLocale(convertToLocale(accountDetails.getLanguageCode(), accountDetails.getCountryCode()));
        userDao.save(registeredUser);

        BasicRegisteredEntity registeredEntity = entityDao.findOne(registeredUser.getId());

        registeredEntity.addRoles(getDefaultRoles());
        entityDao.save(registeredEntity);
        LOGGER.info(String.format("Creating entity %s",
                registeredUser.toString()));

        apiKeyService.addNewAPIKeyToAccount(registeredEntity.getId());
        sendEmailManager.sendRegistrationActivation(registeredUser.getId());
    }

    private String[] getDefaultRoles() {
        return new String[] {DEFAULT_ROLE};
    }

}
