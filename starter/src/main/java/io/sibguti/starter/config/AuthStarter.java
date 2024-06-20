package io.sibguti.starter.config;

import io.sibguti.starter.filter.AutoConfigurationInputFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.HashSet;
import java.util.List;

@Configuration
public class AuthStarter {
    @Autowired
    private Environment environment;

    @Bean
    @ConditionalOnMissingBean
    public AutoConfigurationInputFilter autoConfigurationInputFilter() {
        String[] endpoints = environment.getProperty("session.management.endpoints-to-close-session", "").split(",");
        return new AutoConfigurationInputFilter(new HashSet<>(List.of(endpoints)));
    }
}
