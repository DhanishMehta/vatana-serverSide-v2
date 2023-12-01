package com.capstone.grocery.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.grocery.model.User;
import com.capstone.grocery.response.AuthenticateRequest;
import com.capstone.grocery.response.AuthenticationResponse;
import com.capstone.grocery.response.CommonResponse;
import com.capstone.grocery.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public CommonResponse<AuthenticationResponse<User>> register(@RequestBody User user) {
        return userService.register(user);
    };

    @PostMapping("/authenticate")
    public CommonResponse<AuthenticationResponse<User>> authenticate(@RequestBody AuthenticateRequest user) {
        return userService.authenticate(user);
    };

    @GetMapping("/usernames")
    public CommonResponse<List<String>> getAllUsernames() {
        return userService.getAllUsernames();
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello from auth";
    }
    
    
}
