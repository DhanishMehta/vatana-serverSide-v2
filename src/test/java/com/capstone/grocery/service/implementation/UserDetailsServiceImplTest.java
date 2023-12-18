package com.capstone.grocery.service.implementation;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserDetailsServiceImplTest {

   @Spy
   private BCryptPasswordEncoder bCryptPasswordEncoder;

   @InjectMocks
   private UserDetailsServiceImpl userDetailsService;

   @BeforeEach
   public void init() {
      MockitoAnnotations.initMocks(this);
   }

   @Test
   public void userDetailsServiceTest() {
      UserDetailsService userDetailsManager = userDetailsService.userDetailsService(bCryptPasswordEncoder);

      UserDetails userDetails = userDetailsManager.loadUserByUsername("user");
      assertEquals("user", userDetails.getUsername());
      assertTrue(bCryptPasswordEncoder.matches("userPass", userDetails.getPassword()));
      assertEquals(1, userDetails.getAuthorities().size());

      userDetails = userDetailsManager.loadUserByUsername("admin");
      assertEquals("admin", userDetails.getUsername());
      assertTrue(bCryptPasswordEncoder.matches("adminPass", userDetails.getPassword()));
      assertEquals(2, userDetails.getAuthorities().size());
   }

}