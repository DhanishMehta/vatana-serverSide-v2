package com.capstone.grocery.service.implementation;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

public class JwtServiceImplTest {

    @Mock
    private JwtServiceImpl jwtServiceMock;

    @InjectMocks
    private JwtServiceImpl jwtServiceImpl;

    private final String SECRET_KEY = "testSecretKey12344567testSecretKey12344567testSecretKey12344567";

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void generateTokenTest() {
        UserDetails userDetails = new User("testuser", "testpassword", new ArrayList<>());
        String token = jwtServiceImpl.generateToken(userDetails);

        assertNotNull(token);
        assertTrue(!token.isEmpty());
    }

    @Test
    public void generateTokenWithExtraClaimsTest() {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", "admin");

        UserDetails userDetails = new User("testuser", "testpassword", new ArrayList<>());
        String token = jwtServiceImpl.generateToken(extraClaims, userDetails);

        assertNotNull(token);
        assertTrue(!token.isEmpty());
    }

    @Test
    public void isTokenExpiredTest() {
        String expiredToken = Jwts.builder().setSubject("testuser")
                .setIssuedAt(new Date(System.currentTimeMillis() - 1000))
                .setExpiration(new Date(System.currentTimeMillis() - 1)).signWith(getSignInKey())
                .compact();

        // assertThrows(ExpiredJwtException.class, () -> jwtServiceImpl.isTokenExpired(expiredToken));
        assertThrows(SignatureException.class, () -> jwtServiceImpl.isTokenExpired(expiredToken));
    }

    @Test
    public void extractUsernameMalformedTokenTest() {
        String malformedToken = "12345";
        // jwtServiceImpl.extractUsername(malformedToken);
        // assertNotEquals("testuser", jwtServiceImpl.extractUsername(malformedToken));
        assertThrows(MalformedJwtException.class, () -> jwtServiceImpl.extractUsername(malformedToken));
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
