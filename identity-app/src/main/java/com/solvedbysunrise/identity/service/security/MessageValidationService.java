package com.solvedbysunrise.identity.service.security;

import com.solvedbysunrise.identity.service.security.dto.SignedPayload;

public interface MessageValidationService {

    SignedPayload signPayloadForAccount(Long accountId, String payload);

    String retrieveValidatedPayload(final SignedPayload signedPayload);
}
