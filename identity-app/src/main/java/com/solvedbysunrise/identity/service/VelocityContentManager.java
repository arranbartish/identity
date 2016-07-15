package com.solvedbysunrise.identity.service;

import com.solvedbysunrise.identity.service.dtto.ContentType;
import com.solvedbysunrise.identity.service.velocity.ContentGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Map;

@Service
public class VelocityContentManager implements ContentManager {

    private VelocityContentGeneratorResolver textVelocityContentGeneratorResolver;

    private VelocityContentGeneratorResolver htmlVelocityContentGeneratorResolver;

    @Autowired
    public VelocityContentManager(VelocityContentGeneratorResolver textVelocityContentGeneratorResolver, VelocityContentGeneratorResolver htmlVelocityContentGeneratorResolver) {
        this.textVelocityContentGeneratorResolver = textVelocityContentGeneratorResolver;
        this.htmlVelocityContentGeneratorResolver = htmlVelocityContentGeneratorResolver;
    }

    @Override
    public String generateContent(final Map<String, Object> values, final ContentKey contentKey, final ContentType type, final Locale locale) {
        ContentGenerator contentGenerator = null;
        switch (type) {
            case HTML:
                contentGenerator = htmlVelocityContentGeneratorResolver.resolveContentGenerator(locale, contentKey);
                break;
            case TEXT:
                contentGenerator = textVelocityContentGeneratorResolver.resolveContentGenerator(locale, contentKey);
                break;
        }
        return contentGenerator.generateTemplateContent(values);
    }

    public void setTextVelocityContentGeneratorResolver(VelocityContentGeneratorResolver textVelocityContentGeneratorResolver) {
        this.textVelocityContentGeneratorResolver = textVelocityContentGeneratorResolver;
    }

    public void setHtmlVelocityContentGeneratorResolver(VelocityContentGeneratorResolver htmlVelocityContentGeneratorResolver) {
        this.htmlVelocityContentGeneratorResolver = htmlVelocityContentGeneratorResolver;
    }
}