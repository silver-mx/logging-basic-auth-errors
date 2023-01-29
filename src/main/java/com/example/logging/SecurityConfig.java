package com.example.logging;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final CustomEntryPoint authenticationEntryPoint;

    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(CustomEntryPoint authenticationEntryPoint, PasswordEncoder passwordEncoder) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public InMemoryUserDetailsManager user() {
        return new InMemoryUserDetailsManager(User
                .withUsername("dnunez")
                .password(passwordEncoder.encode("pass"))
                .authorities("read")
                .build());
    }

    @Bean
    public AuthLoggingFilter authLoggingFilter() {
        return new AuthLoggingFilter();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .addFilterBefore(authLoggingFilter(), BasicAuthenticationFilter.class);
        return http.build();
    }
}
