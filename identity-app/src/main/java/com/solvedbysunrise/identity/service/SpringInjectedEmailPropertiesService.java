package com.solvedbysunrise.identity.service;

import com.solvedbysunrise.identity.data.dto.EmailProperties;
import com.solvedbysunrise.identity.data.entity.jpa.email.EmailType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.solvedbysunrise.identity.data.entity.jpa.email.EmailType.valueOf;

public class SpringInjectedEmailPropertiesService implements EmailPropertiesService {

    private final Map<EmailType, EmailProperties> emailPropertiesMap;

    public SpringInjectedEmailPropertiesService(Map<String, EmailProperties> emailPropertiesMap) {
        this.emailPropertiesMap = new HashMap<>();
        emailPropertiesMap.keySet()
                .parallelStream()
                .forEach(key ->
                        this.emailPropertiesMap.put(valueOf(key), emailPropertiesMap.get(key)));
    }

    @Override
    public EmailProperties getEmailProperties(EmailType emailType) {
        return emailPropertiesMap.get(emailType);
    }
}
