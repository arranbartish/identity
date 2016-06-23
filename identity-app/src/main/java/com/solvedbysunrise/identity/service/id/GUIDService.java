package com.solvedbysunrise.identity.service.id;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GUIDService implements UniqueIdentifierService {

    @Override
    public String generateUniqueIdentifier() {
        return UUID.randomUUID().toString();
    }
}
