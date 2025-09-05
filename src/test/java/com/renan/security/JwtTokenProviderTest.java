package com.renan.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setup() {
        jwtTokenProvider = new JwtTokenProvider();
    }

    @Test
    void deveGerarTokenValido() {
        String token = jwtTokenProvider.createToken("user@teste.com");
        assertNotNull(token);
        assertTrue(jwtTokenProvider.validateToken(token));
    }

    @Test
    void deveExtrairEmailDoToken() {
        String email = "user@teste.com";
        String token = jwtTokenProvider.createToken(email);
        assertEquals(email, jwtTokenProvider.getEmailFromToken(token));
    }

    @Test
    void deveInvalidarTokenInvalido() {
        assertFalse(jwtTokenProvider.validateToken("invalid.token.here"));
    }
}
