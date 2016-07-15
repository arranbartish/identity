package com.solvedbysunrise.identity.service.identity.email;

import com.solvedbysunrise.identity.WastedtimeApplication;
import com.solvedbysunrise.identity.config.TestConfiguration;
import com.solvedbysunrise.identity.service.ContentKey;
import com.solvedbysunrise.identity.service.VelocityContentGeneratorResolver;
import com.solvedbysunrise.identity.service.velocity.ContentGenerator;
import com.solvedbysunrise.identity.service.velocity.VelocityFileTemplateContentGenerator;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

import static java.lang.String.format;
import static java.util.Locale.ENGLISH;
import static org.apache.commons.lang3.ClassUtils.isAssignable;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;


@Rollback
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {WastedtimeApplication.class, TestConfiguration.class})
@IntegrationTest
public class EmailTemplateConfigurationIntegrationTest  {

    private static final String HTML_EMAIL = "email/html/";
    private static final String TEXT_EMAIL = "email/text/";
    private static final String HTML = "HTML";
    private static final String TEXT = "TEXT";

    @Autowired
    private VelocityContentGeneratorResolver htmlVelocityContentGeneratorResolver;

    @Autowired
    private VelocityContentGeneratorResolver textVelocityContentGeneratorResolver;

    @Test
    public void htmlVelocityContentGeneratorMap_Will_Only_Contain_Html_Templates() throws Exception {
        Set<Map.Entry<ContentKey,ContentGenerator>> htmlTemplates = htmlVelocityContentGeneratorResolver.resolveAllEntries();
        checkTemplatePrefixes(htmlTemplates, HTML_EMAIL, HTML);
    }

    @Test
    public void textVelocityContentGeneratorMap_Will_Only_Contain_Text_Templates() throws Exception {
        Set<Map.Entry<ContentKey,ContentGenerator>> htmlTemplates = textVelocityContentGeneratorResolver.resolveAllEntries();
        checkTemplatePrefixes(htmlTemplates, TEXT_EMAIL, TEXT);
    }

    @Test
    public void htmlVelocityContentGeneratorMap_Will_Also_Have_A_Text_Template_In_Each_Language(){
        Set<Map.Entry<ContentKey, ContentGenerator>> entries = htmlVelocityContentGeneratorResolver.resolveAllEntries();
        for (Map.Entry<ContentKey, ContentGenerator> entry : entries) {
            ContentGenerator contentGenerator = textVelocityContentGeneratorResolver.resolveContentGenerator(ENGLISH, entry.getKey());
            assertThat(format("email [%s] had html template but not a text version", entry.getKey().toString()), contentGenerator, is(notNullValue()));
        }

    }

    @Test
    public void textVelocityContentGeneratorMap_Will_Also_Have_A_HTML_Template_In_Each_Language(){
        Set<Map.Entry<ContentKey, ContentGenerator>> entries = textVelocityContentGeneratorResolver.resolveAllEntries();
        for (Map.Entry<ContentKey, ContentGenerator> entry : entries) {
            ContentGenerator contentGenerator = htmlVelocityContentGeneratorResolver.resolveContentGenerator(ENGLISH, entry.getKey());
            assertThat(format("email [%s] had text template but not a html version", entry.getKey().toString()), contentGenerator, is(notNullValue()));
        }
    }

    private void checkTemplatePrefixes(Set<Map.Entry<ContentKey,ContentGenerator>> templates, String templatePrefix, String templateType) {
        for (Map.Entry<ContentKey, ContentGenerator> contentKeyContentGeneratorEntry : templates) {
            ContentGenerator template = contentKeyContentGeneratorEntry.getValue();
            if(isAssignable(template.getClass(), VelocityFileTemplateContentGenerator.class)){
                VelocityFileTemplateContentGenerator velocityHtmlTemplate = (VelocityFileTemplateContentGenerator)template;
                String templateName = velocityHtmlTemplate.getTemplateName();
                if(!StringUtils.startsWith(templateName, templatePrefix)){
                    fail(format("template type [%s] should be configured to a the correct template prefix [%s] but was [%s]", templateType, templatePrefix, templateName));
                }
            }
        }
    }
}
