package com.solvedbysunrise.identity.service;

import com.solvedbysunrise.identity.data.dto.EmailProperties;
import com.solvedbysunrise.identity.data.entity.jpa.email.EmailType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SpringInjectedEmailPropertiesService implements EmailPropertiesService {

    private final Map<EmailType, EmailProperties> emailPropertiesMap;

    @Autowired
    public SpringInjectedEmailPropertiesService(Map<EmailType, EmailProperties> emailPropertiesMap) {
        this.emailPropertiesMap = emailPropertiesMap;
    }

    @Override
    public EmailProperties getEmailProperties(EmailType emailType) {
        return emailPropertiesMap.get(emailType);
    }
}
