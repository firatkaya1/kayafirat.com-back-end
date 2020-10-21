package com.kayafirat.service;

import java.util.Collection;
import java.util.HashMap;

import com.kayafirat.model.AuthenticationRequest;
import com.kayafirat.model.projection.UserDetailExcept;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import com.kayafirat.entity.User;
import com.kayafirat.entity.UserPermissions;

public interface UserService extends UserDetailsService {


    User getUserByEmail(String email);

    User getUserByUsername(String username);

    Collection<UserDetailExcept> getAll();

    User saveUser(User user);

    User updateUser(User user);

    boolean deleteUser(String email);

    boolean updateUserPermissions(UserPermissions userPermissions);

    boolean updateUserVerification(String userId, String userEmail);

    Collection<?> searchUser(String keyword);

    String validateCaptcha(String key);

    void updatePassword(HashMap<String, String> request);

    void updateUserGithubAddress(String email, String githubaddress);

    void updateUserLinkedinAddress(String email, String linkedinaddress);

    void updateUserUsername(String email, String username);

    void updateUserBirthDate(String email, String date);

    void updateUserImage(MultipartFile file, String userId);

    String authenticateUser(AuthenticationRequest authRequest) throws Exception;
    
    
}
