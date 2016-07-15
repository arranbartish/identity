package com.solvedbysunrise.identity.service;

import com.solvedbysunrise.identity.service.velocity.ContentGenerator;

import java.util.Locale;
import java.util.Map.Entry;
import java.util.Set;

public interface VelocityContentGeneratorResolver {

    ContentGenerator resolveContentGenerator(Locale locale, ContentKey contentKey);

    Set<Entry<ContentKey,ContentGenerator>> resolveAllEntries();
}
