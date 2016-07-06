package com.solvedbysunrise.identity.service.velocity;

import java.util.Map;

public interface ContentGenerator {

    String generateTemplateContent(String paramName, Object value) throws GenerationException;

    String generateTemplateContent(Map<String, Object> params) throws GenerationException;

}