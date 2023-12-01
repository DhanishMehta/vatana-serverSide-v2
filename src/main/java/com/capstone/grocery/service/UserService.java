package com.capstone.grocery.service;

import java.util.List;

import com.capstone.grocery.model.User;
import com.capstone.grocery.response.AuthenticateRequest;
import com.capstone.grocery.response.AuthenticationResponse;
import com.capstone.grocery.response.CommonResponse;

public interface UserService {

    public CommonResponse<List<User>> getAllUsers();
    public CommonResponse<User> getUserById(String UserId);
    public CommonResponse<User> postNewUser(User user);
    public CommonResponse<User> updateUser(String UserId, User user);
    public CommonResponse<User> deleteUser(String UserId);

    public CommonResponse<List<String>> getAllUsernames();

    public CommonResponse<AuthenticationResponse<User>> register(User user);
    public CommonResponse<AuthenticationResponse<User>> authenticate(AuthenticateRequest req);
}
