package com.solvedbysunrise.identity.service.velocity;

import org.apache.velocity.tools.generic.ListTool;

import java.io.IOException;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

public class TemplateProcessor {

    public String generateTemplateContent(final Map<String, Object> params, Generator generator) throws GenerationException {
        Map<String, Object> localParams = assignLocalParams(params);
        addUtilitiesToParamMap(localParams);
        try {
            return generator.generateContent(localParams);
        } catch (Exception exception) {
            throw new GenerationException("Error generating content from template", exception);
        }
    }

    private Map<String, Object> assignLocalParams(final Map<String, Object> params) {
        Map<String, Object> localParams;
        if (params == null) {
            localParams = newHashMap();
        } else {
            localParams = params;
        }
        return localParams;
    }

    @SuppressWarnings("deprecation")
    private void addUtilitiesToParamMap(Map<String, Object> params) {
        params.put("listTool", new ListTool());
    }
    
    public interface Generator {
        String generateContent(Map<String, Object> params) throws IOException;
    }
}
