package com.capstone.grocery.configuration;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.capstone.grocery.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtRequestFilterTest {
    
    private JwtRequestFilter jwtRequestFilter;
    private JwtService jwtService;
    private UserDetailsService userDetailsService;
    private UserDetails userDetails;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain filterChain;
    
    private static final String AUTH_HEADER = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
    private static final String JWT = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
    private static final String USER_EMAIL = "test@example.com";
    
    @BeforeEach
    public void setUp() throws Exception {
        jwtService = mock(JwtService.class);
        userDetailsService = mock(InMemoryUserDetailsManager.class);
        userDetails = new User(USER_EMAIL, new BCryptPasswordEncoder().encode("password"), List.of(new SimpleGrantedAuthority("USER")) );
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        filterChain = mock(FilterChain.class);
        jwtRequestFilter = new JwtRequestFilter(jwtService, userDetailsService);
    }
    
    @Test
    public void testDoFilterInternal_whenAuthHeaderIsMissing_thenContinueFilterChain() throws Exception {
        when(request.getHeader("Authorization")).thenReturn(null);
        
        jwtRequestFilter.doFilterInternal(request, response, filterChain);
        
        verify(filterChain).doFilter(request, response);
    }
    
    @Test
    public void testDoFilterInternal_whenAuthHeaderIsNotBearer_thenContinueFilterChain() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Basic dXNlcjpwYXNzd29yZA==");
        
        jwtRequestFilter.doFilterInternal(request, response, filterChain);
        
        verify(filterChain).doFilter(request, response);
    }
    
    @Test
    public void testDoFilterInternal_whenAuthHeaderIsBearerAndUserDetailsNotFound_thenContinueFilterChain() throws Exception {
        when(request.getHeader("Authorization")).thenReturn(AUTH_HEADER);
        when(jwtService.extractUsername(JWT)).thenReturn(USER_EMAIL);        
        jwtRequestFilter.doFilterInternal(request, response, filterChain);
        
        verify(filterChain).doFilter(request, response);
    }
    
    @Test
    public void testDoFilterInternal_whenAuthHeaderIsBearerAndUserDetailsFoundAndTokenIsValid_thenAuthenticate() throws Exception {
        when(request.getHeader("Authorization")).thenReturn(AUTH_HEADER);
        when(jwtService.extractUsername(JWT)).thenReturn(USER_EMAIL);
        when(userDetailsService.loadUserByUsername(USER_EMAIL)).thenReturn(userDetails);
        when(jwtService.isTokenValid(JWT, userDetails)).thenReturn(true);
        
        jwtRequestFilter.doFilterInternal(request, response, filterChain);
        
        verify(userDetailsService).loadUserByUsername(USER_EMAIL);
        verify(jwtService).isTokenValid(JWT, userDetails);
        verify(filterChain).doFilter(request, response);
    }
    
    @Test
    public void testDoFilterInternal_whenAuthHeaderIsBearerAndUserDetailsFoundAndTokenIsNotValid_thenContinueFilterChain() throws Exception {
        when(request.getHeader("Authorization")).thenReturn(AUTH_HEADER);
        when(jwtService.extractUsername(JWT)).thenReturn(USER_EMAIL);
        when(userDetailsService.loadUserByUsername(USER_EMAIL)).thenReturn(userDetails);
        when(jwtService.isTokenValid(JWT, userDetails)).thenReturn(false);
        
        jwtRequestFilter.doFilterInternal(request, response, filterChain);
    }

}