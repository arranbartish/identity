package com.solvedbysunrise.identity.service.identity.content;

import com.solvedbysunrise.identity.service.ContentKey;
import com.solvedbysunrise.identity.service.VelocityContentManager;
import com.solvedbysunrise.identity.service.velocity.ContentGenerator;
import com.solvedbysunrise.identity.service.velocity.GenerationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;
import static com.solvedbysunrise.identity.service.ContentKey.ACTIVATION_EMAIL_CONTENT;
import static com.solvedbysunrise.identity.service.dtto.ContentType.HTML;
import static com.solvedbysunrise.identity.service.dtto.ContentType.TEXT;
import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class VelocityContentManagerTest {


    private static final String HTML_CONTENT = "html";
    private static final String TEXT_CONTENT = "text";
    private static final String ENGLISH_CODE = "en";
    private static final String FRENCH_CODE = "fr";

    @InjectMocks
    private VelocityContentManager velocityContentManager;

    @Mock
    private Map<String, Object> values;

    @Before
    public void setup() {
        Map<ContentKey, ContentGenerator> englishTextGenerator = newHashMap();
        englishTextGenerator.put(ACTIVATION_EMAIL_CONTENT, new TextContentGenerator(ENGLISH_CODE));
        Map<ContentKey, ContentGenerator> englishHtmlGenerator = newHashMap();
        englishHtmlGenerator.put(ACTIVATION_EMAIL_CONTENT, new HtmlContentGenerator(ENGLISH_CODE));

        Map<ContentKey, ContentGenerator> frenchTextGenerator = newHashMap();
        frenchTextGenerator.put(ACTIVATION_EMAIL_CONTENT, new TextContentGenerator(FRENCH_CODE));
        Map<ContentKey, ContentGenerator> frenchHtmlGenerator = newHashMap();
        frenchHtmlGenerator.put(ACTIVATION_EMAIL_CONTENT, new HtmlContentGenerator(FRENCH_CODE));

        Map<String, Map<ContentKey, ContentGenerator>> languageCategorisedHtmlVelocityContentGeneratorMap = newHashMap();
        languageCategorisedHtmlVelocityContentGeneratorMap.put(FRENCH_CODE, frenchHtmlGenerator);
        languageCategorisedHtmlVelocityContentGeneratorMap.put(ENGLISH_CODE, englishHtmlGenerator);

        Map<String, Map<ContentKey, ContentGenerator>> languageCategorisedTextVelocityContentGeneratorMap = newHashMap();
        languageCategorisedTextVelocityContentGeneratorMap.put(FRENCH_CODE, frenchTextGenerator);
        languageCategorisedTextVelocityContentGeneratorMap.put(ENGLISH_CODE, englishTextGenerator);

// FIXME
//        velocityContentManager.setLanguageCategorisedHtmlVelocityContentGeneratorMap(languageCategorisedHtmlVelocityContentGeneratorMap);
//        velocityContentManager.setLanguageCategorisedTextVelocityContentGeneratorMap(languageCategorisedTextVelocityContentGeneratorMap);
    }

    @Test
    public void testGenerateContent_Will_Return_French_HTML_When_Given_A_Valid_Key_And_ContentType() throws Exception {
        String content = velocityContentManager.generateContent(values, ACTIVATION_EMAIL_CONTENT, HTML, FRENCH_CODE);

        String expectedResult = format("%s %s", FRENCH_CODE, HTML_CONTENT);
        assertThat(content, is(expectedResult));
    }

    @Test
    public void testGenerateContent_Will_Return_English_HTML_When_Given_A_Valid_Key_And_ContentType() throws Exception {
        String content = velocityContentManager.generateContent(values, ACTIVATION_EMAIL_CONTENT, HTML, ENGLISH_CODE);

        String expectedResult = format("%s %s", ENGLISH_CODE, HTML_CONTENT);
        assertThat(content, is(expectedResult));
    }

    @Test
    public void testGenerateContent_Will_Return_French_TEXT_When_Given_A_Valid_Key_And_ContentType() throws Exception {
        String content = velocityContentManager.generateContent(values, ACTIVATION_EMAIL_CONTENT, TEXT, FRENCH_CODE);

        String expectedResult = format("%s %s", FRENCH_CODE, TEXT_CONTENT);
        assertThat(content, is(expectedResult));
    }

    @Test
    public void testGenerateContent_Will_Return_English_TEXT_When_Given_A_Valid_Key_And_ContentType() throws Exception {
        String content = velocityContentManager.generateContent(values, ACTIVATION_EMAIL_CONTENT, TEXT, ENGLISH_CODE);

        String expectedResult = format("%s %s", ENGLISH_CODE, TEXT_CONTENT);
        assertThat(content, is(expectedResult));
    }

    private class HtmlContentGenerator extends AbstractContentGenerator {
        private HtmlContentGenerator(String languageCode) {
            super(HTML_CONTENT, languageCode);
        }
    }

    private class TextContentGenerator extends AbstractContentGenerator {

        private TextContentGenerator(String languageCode) {
            super(TEXT_CONTENT, languageCode);
        }
    }

    private abstract class AbstractContentGenerator implements ContentGenerator {

        private final String languageCode;
        private final String content;

        protected AbstractContentGenerator(String content, String languageCode) {
            this.languageCode = languageCode;
            this.content = content;
        }

        @Override
        public String generateTemplateContent(String paramName, Object value) throws GenerationException {
            Map<String, Object> stringObjectMap = newHashMap();
            stringObjectMap.put(paramName, value);
            return generateTemplateContent(stringObjectMap);
        }

        @Override
        public String generateTemplateContent(Map<String, Object> params) throws GenerationException {
            return format("%s %s", languageCode, content);
        }
    }


}
