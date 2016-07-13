package com.solvedbysunrise.identity.mock.service;

import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;

@Service
public class SingletonMapMailGunMessageCaptor implements MailGunMessageCaptor {

    private MultiValueMap<String, Object> message;

    @Override
    public void storeMessage(MultiValueMap<String, Object> message) {
        this.message = message;
    }

    @Override
    public MultiValueMap<String, Object> getMessageMap() {
        return message;
    }
}
