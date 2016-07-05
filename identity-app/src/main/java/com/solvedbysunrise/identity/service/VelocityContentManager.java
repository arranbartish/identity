package com.solvedbysunrise.identity.service;

import com.google.common.collect.Maps;
import com.solvedbysunrise.identity.service.dtto.ContentType;
import com.solvedbysunrise.identity.service.velocity.ContentGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

@Service
public class VelocityContentManager implements ContentManager {

    //@Resource(name = "languageCategorisedHtmlVelocityContentGeneratorMap")
    private final Map<String, Map<ContentKey, ContentGenerator>> languageCategorisedHtmlVelocityContentGeneratorMap;

    //@Resource(name = "languageCategorisedTextVelocityContentGeneratorMap")
    private final Map<String, Map<ContentKey, ContentGenerator>> languageCategorisedTextVelocityContentGeneratorMap;

    public VelocityContentManager() {
        this.languageCategorisedHtmlVelocityContentGeneratorMap = newHashMap();
        this.languageCategorisedTextVelocityContentGeneratorMap = newHashMap();
    }

    @Override
    public String generateContent(final Map<String, Object> values, final ContentKey contentKey, final ContentType type, final String language) {
        Map<ContentKey, ContentGenerator> contentGenerators = null;

        switch (type) {
            case HTML:
                contentGenerators = languageCategorisedHtmlVelocityContentGeneratorMap.get(language);
                break;
            case TEXT:
                contentGenerators = languageCategorisedTextVelocityContentGeneratorMap.get(language);
                break;
        }

        ContentGenerator contentGenerator = contentGenerators.get(contentKey);
        return contentGenerator.generateTemplateContent(values);
    }

}