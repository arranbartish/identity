package com.solvedbysunrise.identity;

import com.solvedbysunrise.identity.config.ProductionConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.client.RestTemplate;

import javax.validation.Validator;

@SpringBootApplication
@EnableConfigurationProperties
@EnableJpaAuditing
@EnableAutoConfiguration
@EnableTransactionManagement
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


}
