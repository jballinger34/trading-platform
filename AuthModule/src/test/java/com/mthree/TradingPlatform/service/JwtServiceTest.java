package com.mthree.TradingPlatform.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.jwt.*;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @Mock
    private JwtEncoder jwtEncoder;

    @Mock
    private Jwt jwt;

    @InjectMocks
    private JwtService jwtService;

    @Test
    void generateToken_returnsTokenValue() {
        UUID userId = UUID.randomUUID();

        when(jwtEncoder.encode(any())).thenReturn(jwt);
        when(jwt.getTokenValue()).thenReturn("mock-token");

        String result = jwtService.generateToken(userId);

        assertNotNull(result);
        assertEquals("mock-token", result);

        verify(jwtEncoder, times(1)).encode(any());
    }

    @Test
    void generateToken_containsCorrectSubject() {
        UUID userId = UUID.randomUUID();

        ArgumentCaptor<JwtEncoderParameters> captor =
                ArgumentCaptor.forClass(JwtEncoderParameters.class);

        when(jwtEncoder.encode(any())).thenReturn(jwt);
        when(jwt.getTokenValue()).thenReturn("mock-token");

        jwtService.generateToken(userId);

        verify(jwtEncoder).encode(captor.capture());

        JwtClaimsSet claims = captor.getValue().getClaims();

        assertEquals(userId.toString(), claims.getSubject());
        assertTrue(claims.getExpiresAt().isAfter(Instant.now()));
    }
}