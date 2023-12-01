package com.capstone.grocery.service.implementation;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl {
    public UserDetailsService userDetailsService(BCryptPasswordEncoder bCryptPasswordEncoder) {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user")
        .password(bCryptPasswordEncoder.encode("userPass"))
        .roles("USER")
        .build());
        manager.createUser(User.withUsername("admin")
        .password(bCryptPasswordEncoder.encode("adminPass"))
        .roles("USER", "ADMIN")
        .build());
        return manager;
    }
}
