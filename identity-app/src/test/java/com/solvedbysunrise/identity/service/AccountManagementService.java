package com.solvedbysunrise.identity.service;

public interface AccountManagementService {
    void activateAccountWithGuid(String activationGuid);

    void deactivateAccountWithGuid(String activationGuid);
}
