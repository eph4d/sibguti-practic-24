package io.sibguti.authstarter.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "auth-properties")
@Data
public class AuthStarterProperties {
    private String[] endpoints;
}
