package com.solvedbysunrise.identity.service;

import com.solvedbysunrise.identity.service.dtto.ContentType;

import java.util.Map;

public interface ContentManager {

    String generateContent(final Map<String, Object> values, final ContentKey contentKey, final ContentType type, final String language);
}
