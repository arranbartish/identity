package com.solvedbysunrise.identity.service;

import com.solvedbysunrise.identity.service.dtto.PreparedEmail;

public interface SendEmailService {

    String sendMail(PreparedEmail preparedEmail);

}
