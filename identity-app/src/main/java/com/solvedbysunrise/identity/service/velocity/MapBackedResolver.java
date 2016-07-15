package com.solvedbysunrise.identity.service.velocity;

import com.solvedbysunrise.identity.service.ContentKey;
import com.solvedbysunrise.identity.service.VelocityContentGeneratorResolver;

import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import static java.util.Locale.ENGLISH;

public class MapBackedResolver implements VelocityContentGeneratorResolver {

    private final Map<String, Map<ContentKey, ContentGenerator>> languageMap;

    public MapBackedResolver(Map<String, Map<ContentKey, ContentGenerator>> languageMap) {
        this.languageMap = languageMap;
    }

    @Override
    public ContentGenerator resolveContentGenerator(Locale locale, ContentKey contentKey) {
        Map<ContentKey, ContentGenerator> resolvedMap = languageMap.get(locale.getLanguage());
        return resolvedMap.get(contentKey);
    }

    @Override
    public Set<Entry<ContentKey, ContentGenerator>> resolveAllEntries() {
        Map<ContentKey, ContentGenerator> resolvedMap = languageMap.get(ENGLISH.getLanguage());
        return resolvedMap.entrySet();
    }
};