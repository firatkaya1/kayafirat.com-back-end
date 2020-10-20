package com.firatkaya.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.firatkaya.model.AuthenticationRequest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import com.firatkaya.entity.User;
import com.firatkaya.entity.UserPermissions;

public interface UserService extends UserDetailsService {


    User getUserByEmail(String email);

    User getUserByUsername(String username);

    List<User> getAll();

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
