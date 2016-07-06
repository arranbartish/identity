package com.solvedbysunrise.identity.service.velocity;

import com.google.common.collect.Maps;
import com.solvedbysunrise.identity.service.velocity.TemplateProcessor.Generator;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;

import java.io.StringWriter;
import java.util.Map;

public class VelocityFileTemplateContentGenerator implements ContentGenerator {
    private final Template template;
    private final String templateName;
    private final String namespace;
    private final TemplateProcessor templateProcessor;

    public VelocityFileTemplateContentGenerator(VelocityEngine velocityEngine, String templateName) {
        this(velocityEngine, templateName, null);
    }

    public VelocityFileTemplateContentGenerator(VelocityEngine velocityEngine, String templateName, String namespace) {
        templateProcessor = new TemplateProcessor();
        this.templateName = templateName;
        this.namespace = namespace;
        try {
            template = velocityEngine.getTemplate(templateName);
        } catch (Exception exception) {
            throw new GenerationException("Error producing velocity template from template: " + templateName, exception);
        }
    }

    public String generateTemplateContent(String paramName, Object value) throws GenerationException {
        Map<String, Object> params = Maps.newHashMap();
        params.put(paramName, value);
        return generateTemplateContent(params);
    }

    public String generateTemplateContent(Map<String, Object> params) throws GenerationException {
        return templateProcessor.generateTemplateContent(params, param -> {
            param.put("namespace", namespace);
            StringWriter stringWriter = new StringWriter();
            Context context = new VelocityContext(param);
            template.merge(context, stringWriter);
            return stringWriter.toString();
        });
    }

    public String getTemplateName() {
        return templateName;
    }
}
