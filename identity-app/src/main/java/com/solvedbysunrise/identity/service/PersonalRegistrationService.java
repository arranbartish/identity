package com.solvedbysunrise.identity.service;

import com.solvedbysunrise.identity.service.dtto.PersonalRegistrationRequest;

public interface PersonalRegistrationService {

    void createAccount(PersonalRegistrationRequest accountDetails);

}
