package dev.caridadems.configuration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.junit.jupiter.api.Assertions.*;

class SecurityConfigTest {

    private SecurityConfig securityConfig;


    @BeforeEach
    void setUp() {
        securityConfig = new SecurityConfig();
    }

    @Test
    void securityFilterChain() throws Exception {
        final var http = Mockito.mock(HttpSecurity.class, Mockito.RETURNS_DEEP_STUBS);
        SecurityFilterChain chain = securityConfig.securityFilterChain(http);
        assertNotNull(chain);
    }
}