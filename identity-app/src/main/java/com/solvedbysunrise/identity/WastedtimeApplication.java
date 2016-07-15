package com.solvedbysunrise.identity;

import com.solvedbysunrise.identity.config.ProductionConfiguration;
import com.solvedbysunrise.identity.data.dto.ApplicationProperties;
import com.solvedbysunrise.identity.service.ContentKey;
import com.solvedbysunrise.identity.service.VelocityContentGeneratorResolver;
import com.solvedbysunrise.identity.service.security.UserIdAuditorAware;
import com.solvedbysunrise.identity.service.velocity.ContentGenerator;
import com.solvedbysunrise.identity.service.velocity.MapBackedResolver;
import com.solvedbysunrise.identity.service.velocity.VelocityFileTemplateContentGenerator;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.client.RestTemplate;

import javax.validation.Validator;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;
import static com.solvedbysunrise.identity.service.ContentKey.*;
import static java.util.Locale.ENGLISH;
import static java.util.Locale.FRENCH;

@SpringBootApplication
@EnableConfigurationProperties
@EnableJpaAuditing
@EnableAutoConfiguration
@EnableTransactionManagement
@EnableAsync
@EnableScheduling
public class WastedtimeApplication {

    @Autowired
    private ApplicationProperties applicationProperties;

    public static void main(String[] args) {
		SpringApplication.run(
                new Object[] {WastedtimeApplication.class,
                                ProductionConfiguration.class},
                args);
    }


    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }


    @Bean
    public VelocityContentGeneratorResolver htmlVelocityContentGeneratorResolver() throws IOException {
        final Map<String, Map<ContentKey, ContentGenerator>> languageMap = newHashMap();

        VelocityEngineFactoryBean velocityEngineFactory = new VelocityEngineFactoryBean();
        velocityEngineFactory.setResourceLoaderPath("classpath:velocity");
        velocityEngineFactory.setPreferFileSystemAccess(false);
        VelocityEngine velocityEngine = velocityEngineFactory.createVelocityEngine();

        VelocityFileTemplateContentGenerator englishHtmlActivation = new VelocityFileTemplateContentGenerator(velocityEngine, "email/html/en/registrationActivation.vm");
        VelocityFileTemplateContentGenerator englishHtmlContactUs = new VelocityFileTemplateContentGenerator(velocityEngine, "email/html/en/contactUs.vm");
        VelocityFileTemplateContentGenerator englishHtmlResetPassword = new VelocityFileTemplateContentGenerator(velocityEngine, "email/html/en/passwordUpgrade.vm");

        Map<ContentKey, ContentGenerator> contentGeneratorMap = newHashMap();
        contentGeneratorMap.put(ACTIVATION_EMAIL_CONTENT, englishHtmlActivation);
        contentGeneratorMap.put(CONTACT_US_EMAIL_CONTENT, englishHtmlContactUs);
        contentGeneratorMap.put(RESET_PASSWORD_EMAIL_CONTENT, englishHtmlResetPassword);

        languageMap.put(ENGLISH.getLanguage(), contentGeneratorMap);

        VelocityFileTemplateContentGenerator frenchHtmlActivation = new VelocityFileTemplateContentGenerator(velocityEngine, "email/html/fr/registrationActivation.vm");
        VelocityFileTemplateContentGenerator frenchHtmlContactUs = new VelocityFileTemplateContentGenerator(velocityEngine, "email/html/fr/contactUs.vm");
        VelocityFileTemplateContentGenerator frenchHtmlResetPassword = new VelocityFileTemplateContentGenerator(velocityEngine, "email/html/fr/passwordUpgrade.vm");

        contentGeneratorMap = newHashMap();
        contentGeneratorMap.put(ACTIVATION_EMAIL_CONTENT, frenchHtmlActivation);
        contentGeneratorMap.put(CONTACT_US_EMAIL_CONTENT, frenchHtmlContactUs);
        contentGeneratorMap.put(RESET_PASSWORD_EMAIL_CONTENT, frenchHtmlResetPassword);

        languageMap.put(FRENCH.getLanguage(), contentGeneratorMap);

        return new MapBackedResolver(languageMap);
    }

    @Bean
    public VelocityContentGeneratorResolver textVelocityContentGeneratorResolver() throws IOException {
        final Map<String, Map<ContentKey, ContentGenerator>> languageMap = newHashMap();

        VelocityEngineFactoryBean velocityEngineFactory = new VelocityEngineFactoryBean();
        velocityEngineFactory.setResourceLoaderPath("classpath:velocity");
        velocityEngineFactory.setPreferFileSystemAccess(false);
        VelocityEngine velocityEngine = velocityEngineFactory.createVelocityEngine();

        VelocityFileTemplateContentGenerator englishTextActivation = new VelocityFileTemplateContentGenerator(velocityEngine, "email/text/en/registrationActivation.vm");
        VelocityFileTemplateContentGenerator englishTextContactUs = new VelocityFileTemplateContentGenerator(velocityEngine, "email/text/en/contactUs.vm");
        VelocityFileTemplateContentGenerator englishTextResetPassword = new VelocityFileTemplateContentGenerator(velocityEngine, "email/text/en/passwordUpgrade.vm");

        Map<ContentKey, ContentGenerator> contentGeneratorMap = newHashMap();
        contentGeneratorMap.put(ACTIVATION_EMAIL_CONTENT, englishTextActivation);
        contentGeneratorMap.put(CONTACT_US_EMAIL_CONTENT, englishTextContactUs);
        contentGeneratorMap.put(RESET_PASSWORD_EMAIL_CONTENT, englishTextResetPassword);

        languageMap.put(ENGLISH.getLanguage(), contentGeneratorMap);

        VelocityFileTemplateContentGenerator frenchTextActivation = new VelocityFileTemplateContentGenerator(velocityEngine, "email/text/fr/registrationActivation.vm");
        VelocityFileTemplateContentGenerator frenchTextContactUs = new VelocityFileTemplateContentGenerator(velocityEngine, "email/text/fr/contactUs.vm");
        VelocityFileTemplateContentGenerator frenchTextResetPassword = new VelocityFileTemplateContentGenerator(velocityEngine, "email/text/fr/passwordUpgrade.vm");

        contentGeneratorMap = newHashMap();
        contentGeneratorMap.put(ACTIVATION_EMAIL_CONTENT, frenchTextActivation);
        contentGeneratorMap.put(CONTACT_US_EMAIL_CONTENT, frenchTextContactUs);
        contentGeneratorMap.put(RESET_PASSWORD_EMAIL_CONTENT, frenchTextResetPassword);

        languageMap.put(FRENCH.getLanguage(), contentGeneratorMap);

        return new MapBackedResolver(languageMap);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        SecureRandom intialRandom = new SecureRandom();
        byte[] seed = intialRandom.generateSeed(20);
        SecureRandom secureRandom = new SecureRandom(seed);
        return new BCryptPasswordEncoder(10, secureRandom);
    }

    @Bean
    AuditorAware<String> auditorProvider() {
        return new UserIdAuditorAware(applicationProperties.getRootAccountId());
    }
}
