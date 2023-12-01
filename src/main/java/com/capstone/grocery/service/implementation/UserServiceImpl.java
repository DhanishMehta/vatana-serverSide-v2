package com.capstone.grocery.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.capstone.grocery.model.User;
import com.capstone.grocery.repository.UserRepository;
import com.capstone.grocery.response.AuthenticateRequest;
import com.capstone.grocery.response.AuthenticationResponse;
import com.capstone.grocery.response.CommonResponse;
import com.capstone.grocery.service.JwtService;
import com.capstone.grocery.service.UserService;
import com.capstone.grocery.utility.Utility;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    @Override
    public CommonResponse<List<User>> getAllUsers() {
        try {
            List<User> userList = userRepository.findAll();
            // TODO
            return Utility.getCommonResponse(200, true, "Users List", null, userList);
        } catch (Exception exc) {
            // TODO
            return Utility.getCommonResponse(404, false, "Users Not Found Error: " + exc, null, null);
        }
    }

    @Override
    public CommonResponse<User> getUserById(String UserId) {
        try {
            User user = userRepository.findById(UserId).get();
            // TODO
            return Utility.getCommonResponse(200, true, "User Found", null, user);
        } catch (Exception exc) {
            // TODO
            return Utility.getCommonResponse(404, false, "User Not Found Error: " + exc, null, null);
        }
    }

    @Override
    public CommonResponse<User> postNewUser(User user) {
        try {
            userRepository.save(user);
            // TODO
            return Utility.getCommonResponse(200, true, "User Added Successfully", null, user);
        } catch (Exception exc) {
            // TODO
            return Utility.getCommonResponse(404, false, "User Not Created Error: " + exc, null, null);
        }
    }

    @Override
    public CommonResponse<User> updateUser(String UserId, User user) {
        try {
            User oldUser = userRepository.findById(UserId).get();
            oldUser.setAllAttributes(user);
            userRepository.save(oldUser);
            // TODO
            return Utility.getCommonResponse(200, true, "User Updated", null, oldUser);
        } catch (Exception exc) {
            // TODO
            return Utility.getCommonResponse(404, false, "User Not Found Error: " + exc, null, null);
        }
    }

    @Override
    public CommonResponse<User> deleteUser(String UserId) {
        try {
            User user = userRepository.findById(UserId).get();
            userRepository.delete(user);
            // TODO
            return Utility.getCommonResponse(200, true, "User Deleted", null, user);
        } catch (Exception exc) {
            // TODO
            return Utility.getCommonResponse(404, false, "User Not Found Error: " + exc, null, null);
        }
    }

    @Override
    public CommonResponse<AuthenticationResponse<User>> register(User user) {
        user.setUserId(null);
        user.setUserEncryptedPassword(passwordEncoder.encode(user.getUserEncryptedPassword()));
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        AuthenticationResponse<User> authRes = new AuthenticationResponse<User>(jwtToken, user);
        return new CommonResponse<AuthenticationResponse<User>>(200, true, "User Authenticated", null, authRes);
    }

    @Override
    public CommonResponse<AuthenticationResponse<User>> authenticate(AuthenticateRequest req) {
        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getUserEmail(), req.getUserPassword()));
            var user = userRepository.findByUserEmail(req.getUserEmail()).orElseThrow();
            var jwtToken = jwtService.generateToken(user);
            AuthenticationResponse<User> authRes = new AuthenticationResponse<User>(jwtToken, user);
            return new CommonResponse<AuthenticationResponse<User>>(200, true, "User Authenticated", null, authRes);
        } catch (Exception exc) {
            return new CommonResponse<AuthenticationResponse<User>>(403, false, "User Not Authenticated", null, null);
        }
    }

    @Override
    public CommonResponse<List<String>> getAllUsernames() {
        try {
            List<String> usernamesList = new ArrayList<>();
            List<User> res = this.userRepository.findAllUsername();
            res.forEach(user -> {
                usernamesList.add(user.getUserEmail());
            });
            return Utility.getCommonResponse(200, true, "All Usernames", null, usernamesList);
        } catch (Exception exc) {
            return Utility.getCommonResponse(404, false, "Error: " + exc, null, null);
        }
    }

}
