package io.sibguti.authstarter.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;
@ComponentScan
@AutoConfiguration
@EnableWebSecurity
@EnableConfigurationProperties(AuthStarterProperties.class)
public class SecurityConfig {
    @Autowired
    private AuthStarterProperties authStarterProperties;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomFilter customFilter) throws Exception
    {
        System.out.println(123);
        //TODO сделать так, чтобы стартер перехватывал запрос который содержит user-info и шел проверять на ендпоинт /starter/check-session
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/starter/check-session").permitAll()
                        .requestMatchers(authStarterProperties.getEndpoints()).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .successForwardUrl("/api/login")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}



