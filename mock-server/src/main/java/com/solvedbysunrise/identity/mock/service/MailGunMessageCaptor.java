package com.solvedbysunrise.identity.mock.service;

import org.springframework.util.MultiValueMap;

import java.util.List;

public interface MailGunMessageCaptor {

    void storeMessage(MultiValueMap<String, Object> message);

    MultiValueMap<String, Object> getMessageMap();
}
