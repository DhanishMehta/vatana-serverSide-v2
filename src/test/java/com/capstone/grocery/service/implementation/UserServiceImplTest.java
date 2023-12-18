package com.capstone.grocery.service.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.capstone.grocery.model.User;
import com.capstone.grocery.model.UserRole;
import com.capstone.grocery.repository.UserRepository;
import com.capstone.grocery.response.AuthenticateRequest;
import com.capstone.grocery.response.AuthenticationResponse;
import com.capstone.grocery.response.CommonResponse;
import com.capstone.grocery.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import okio.Options;

public class UserServiceImplTest {
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private UserRepository userRepository;
    
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    User user_1 = new User("test_1", "test", "test", "test", "test", "test", null , UserRole.USER, null, null);
    User user_2 = new User("test_2", "test", "test", "test", "test", "test", null , UserRole.USER, null, null);
    User user_3 = new User("test_3", "test", "test", "test", "test", "test", null , UserRole.USER, null, null);


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userServiceImpl).build();
    }

    @Test
    void testGetAllUsers_success() {
        List<User> users = new ArrayList<>(Arrays.asList(user_1, user_2, user_3));
        Mockito.when(userRepository.findAll()).thenReturn(users);
        CommonResponse<List<User>> response = userServiceImpl.getAllUsers();
        assertEquals(200, response.getStatusCode());
    }

    @Test
    void testGetAllUsers_failure() {
        List<User> users = new ArrayList<>(Arrays.asList(user_1, user_2, user_3));
        Mockito.when(userRepository.findAll()).thenThrow(new NullPointerException());
        CommonResponse<List<User>> response = userServiceImpl.getAllUsers();
        assertEquals(404, response.getStatusCode());
    }
    
    @Test
    void testGetUserById_success() {
        Mockito.when(userRepository.findById(user_1.getUserId())).thenReturn(Optional.of(user_1));
        CommonResponse<User> response = userServiceImpl.getUserById(user_1.getUserId());
        assertEquals(200, response.getStatusCode());
    }
    
    @Test
    void testGetUserById_failure() {
        Mockito.when(userRepository.findById(user_1.getUserId())).thenThrow(new NullPointerException());
        CommonResponse<User> response = userServiceImpl.getUserById(user_1.getUserId());
        assertEquals(404, response.getStatusCode());
    }
    
    @Test
    void testPostNewUser_success() {
        Mockito.when(userRepository.save(user_1)).thenReturn(user_1);
        CommonResponse<User> response = userServiceImpl.postNewUser(user_1);
        assertEquals(200, response.getStatusCode());
    }
    
    @Test
    void testPostNewUser_failure() {
        Mockito.when(userRepository.save(user_1)).thenThrow(new NullPointerException());
        CommonResponse<User> response = userServiceImpl.postNewUser(user_1);
        assertEquals(404, response.getStatusCode());
    }
    
    @Test
    void testUpdateUser_success() {
        Mockito.when(userRepository.findById(user_1.getUserId())).thenReturn(Optional.of(user_1));
        Mockito.when(userRepository.save(user_1)).thenReturn(user_1);
        CommonResponse<User> response = userServiceImpl.updateUser(user_1.getUserId(), user_1);
        assertEquals(200, response.getStatusCode());
    }
    
    @Test
    void testUpdateUser_failure() {
        Mockito.when(userRepository.findById(user_1.getUserId())).thenReturn(Optional.of(user_1));
        Mockito.when(userRepository.save(user_1)).thenThrow(new NullPointerException());
        CommonResponse<User> response = userServiceImpl.updateUser(user_1.getUserId(), user_1);
        assertEquals(404, response.getStatusCode());
    }
    
    @Test
    void testDeleteUser_success() {
        Mockito.when(userRepository.findById(user_1.getUserId())).thenReturn(Optional.of(user_1));
        CommonResponse<User> response = userServiceImpl.deleteUser(user_1.getUserId());
        assertEquals(200, response.getStatusCode());
    }
    
    @Test
    void testDeleteUser_failure() {
        Mockito.when(userRepository.findById(user_1.getUserId())).thenThrow(new NullPointerException());
        CommonResponse<User> response = userServiceImpl.deleteUser(user_1.getUserId());
        assertEquals(404, response.getStatusCode());
    }
    
    @Test
    void testGetAllUsernames_success() {
        List<User> users = new ArrayList<>(Arrays.asList(user_1, user_2, user_3));
        Mockito.when(userRepository.findAllUsername()).thenReturn(users);
        CommonResponse<List<String>> response = userServiceImpl.getAllUsernames();
        assertEquals(200, response.getStatusCode());
        assertEquals(3, response.getData().size());
    }
    
    @Test
    void testGetAllUsernames_failure() {
        Mockito.when(userRepository.findAllUsername()).thenThrow(new NullPointerException());
        CommonResponse<List<String>> response = userServiceImpl.getAllUsernames();
        assertEquals(404, response.getStatusCode());
    }

    @Test
    void testRegisterUser_passwordEncoderAndJwtService() {
        String password = "encryptedPassword";
        String token = "testToken";
        Mockito.when(passwordEncoder.encode(user_1.getUserEncryptedPassword())).thenReturn(password);
        Mockito.when(jwtService.generateToken(user_1)).thenReturn(token);
        AuthenticationResponse<User> expectedResponse = new AuthenticationResponse<User>(token, user_1);
        CommonResponse<AuthenticationResponse<User>> response = userServiceImpl.register(user_1);
        assertEquals(200, response.getStatusCode());
        assertEquals(expectedResponse, response.getData());
    }
    
    @Test
    void testAuthenticateUser_passwordEncoderAndJwtService() {
        String username = user_1.getUserEmail();
        String password = user_1.getUserEncryptedPassword();
        AuthenticateRequest req = new AuthenticateRequest(username, password);
        String token = "testToken";
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        Mockito.when(userRepository.findByUserEmail(user_1.getUserEmail())).thenReturn(Optional.of(user_1));
        Mockito.when(jwtService.generateToken(user_1)).thenReturn(token);
        AuthenticationResponse<User> expectedResponse = new AuthenticationResponse<User>(token, user_1);
        CommonResponse<AuthenticationResponse<User>> response = userServiceImpl.authenticate(req);
        assertEquals(200, response.getStatusCode());
        assertEquals(expectedResponse, response.getData());
    }
}
