package com.solvedbysunrise.identity;

import com.solvedbysunrise.identity.config.ProductionConfiguration;
import com.solvedbysunrise.identity.service.ContentKey;
import com.solvedbysunrise.identity.service.velocity.ContentGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.client.RestTemplate;

import javax.validation.Validator;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

@SpringBootApplication
@EnableConfigurationProperties
@EnableJpaAuditing
@EnableAutoConfiguration
@EnableTransactionManagement
@EnableAsync
@EnableScheduling
public class WastedtimeApplication {


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
    public Map<ContentKey, ContentGenerator> htmlVelocityContentGeneratorMap() {
        return newHashMap();
    }

    @Bean
    public Map<ContentKey, ContentGenerator> textVelocityContentGeneratorMap() {
        return newHashMap();
    }

}
