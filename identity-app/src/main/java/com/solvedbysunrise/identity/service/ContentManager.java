package com.solvedbysunrise.identity.service;

import com.solvedbysunrise.identity.service.dtto.ContentType;

import java.util.Locale;
import java.util.Map;

public interface ContentManager {

    String generateContent(Map<String, Object> values, ContentKey contentKey, ContentType type, Locale locale);
}
