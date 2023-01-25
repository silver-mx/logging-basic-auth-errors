package com.example.logging;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CustomEntryPoint extends BasicAuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        log.warn("An unauthorized request was received. {}", authException.getMessage());
        log.warn("Here can we customize our handling of authentication failures");
        super.commence(request, response, authException);

    }

    @Override
    public void afterPropertiesSet() {
        this.setRealmName("Realm");
        super.afterPropertiesSet();
    }
}
