package com.solvedbysunrise.identity.service;

import com.solvedbysunrise.identity.data.dto.EmailProperties;
import com.solvedbysunrise.identity.data.entity.jpa.email.EmailType;

public interface EmailPropertiesService {

    EmailProperties getEmailProperties(EmailType emailType);
}
