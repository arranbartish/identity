package com.solvedbysunrise.identity.service;

import com.solvedbysunrise.identity.service.dtto.ContactUsRequest;

public interface SendEmailManager {

    void sendPasswordReset(final Long accountId);

    void sendRegistrationActivation(final Long accountId);

    void resendAnyOldEmailsThatHaveNotBeenSent();

    void sendContactUsEmail(final ContactUsRequest contactUsRequest);

}
