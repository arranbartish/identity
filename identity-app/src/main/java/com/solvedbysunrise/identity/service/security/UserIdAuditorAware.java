package com.solvedbysunrise.identity.service.security;

import org.springframework.data.domain.AuditorAware;

public class UserIdAuditorAware implements AuditorAware<String> {

    private final Long systemId;

    public UserIdAuditorAware(final Long systemId) {
        this.systemId = systemId;
    }

    @Override
    public String getCurrentAuditor() {
        return systemId.toString();
    }
}
