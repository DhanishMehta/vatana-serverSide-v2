package com.capstone.grocery.configuration;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public class CorsCustomizerTest {
    
    private CorsCustomizer corsCustomizer;
    private HttpSecurity http;
    
    @BeforeEach
    public void setUp() throws Exception {
        corsCustomizer = new CorsCustomizer();
        http = mock(HttpSecurity.class);
    }
    
    @Test
    public void testCorsCustomizer() throws Exception {
        corsCustomizer.corsCustomizer(http);
        
        verify(http).cors(any());
    }
}