package com.solvedbysunrise.identity.service.security;

public interface ApiKeyService {
    String getKeyForMessageSigning(Long accountId);
}
