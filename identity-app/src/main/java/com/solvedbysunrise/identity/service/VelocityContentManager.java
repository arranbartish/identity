package com.solvedbysunrise.identity.service;

import com.solvedbysunrise.identity.service.dtto.ContentType;
import com.solvedbysunrise.identity.service.velocity.ContentGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class VelocityContentManager implements ContentManager {

    @Resource(name = "languageCategorisedHtmlVelocityContentGeneratorMap")
    private Map<String, Map<ContentKey, ContentGenerator>> languageCategorisedHtmlVelocityContentGeneratorMap;

    @Resource(name = "languageCategorisedTextVelocityContentGeneratorMap")
    private Map<String, Map<ContentKey, ContentGenerator>> languageCategorisedTextVelocityContentGeneratorMap;

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

    public void setLanguageCategorisedHtmlVelocityContentGeneratorMap(Map<String, Map<ContentKey, ContentGenerator>> languageCategorisedHtmlVelocityContentGeneratorMap) {
        this.languageCategorisedHtmlVelocityContentGeneratorMap = languageCategorisedHtmlVelocityContentGeneratorMap;
    }

    public void setLanguageCategorisedTextVelocityContentGeneratorMap(Map<String, Map<ContentKey, ContentGenerator>> languageCategorisedTextVelocityContentGeneratorMap) {
        this.languageCategorisedTextVelocityContentGeneratorMap = languageCategorisedTextVelocityContentGeneratorMap;
    }
}