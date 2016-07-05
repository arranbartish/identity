package com.solvedbysunrise.identity.service.security;

import com.solvedbysunrise.identity.service.id.UniqueIdentifierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RandomStringApiKeyService implements ApiKeyService {

    private final UniqueIdentifierService uniqueIdentifierService;

    @Autowired
    public RandomStringApiKeyService(UniqueIdentifierService uniqueIdentifierService) {
        this.uniqueIdentifierService = uniqueIdentifierService;
    }

    @Override
    public String getKeyForMessageSigning(Long accountId) {
        return uniqueIdentifierService.generateUniqueIdentifier();
    }

    @Override
    public void addNewAPIKeyToAccount(Long id) {
        // whatever dude
    }
}
