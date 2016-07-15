package com.solvedbysunrise.identity.service;

import com.solvedbysunrise.identity.data.entity.jpa.user.RegisteredUser;
import com.solvedbysunrise.identity.service.dtto.Account;
import com.solvedbysunrise.identity.service.dtto.PersonalRegistrationRequest;

public interface RegistrationService {

    Account createAccount(PersonalRegistrationRequest accountDetails);

    Account getAccount(Long accountId);

}
