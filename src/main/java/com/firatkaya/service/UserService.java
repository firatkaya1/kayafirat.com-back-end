package com.firatkaya.service;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.firatkaya.model.User;
import com.firatkaya.model.UserPermissions;

@Service
public interface UserService extends UserDetailsService {

	List<User> getAllUser();
	
	User getUser(String email);
	
	User getUserbyUsername(String username);
	
	User saveUser(User user);
	
	User updateUser(User user);
	
	boolean deleteUser(String email);
	
	boolean updateUserPermissions(String username,UserPermissions userPermissions);
	
	boolean verificationUser(String userId,String userEmail);
	
	Collection<?> searchUser(String keyword);
	
	String validateCaptcha(String key);

	boolean updatePassword(String email,String password);
	
	boolean updateUserGithubAddress(String email,String githubaddress);
	
	boolean updateUserLinkedinAddress(String email,String linkedinaddress);
	
	boolean updateUserUsername(String email,String username);
	
	boolean updateUserBirthDate(String email,String date);
	
	boolean updateUserPasswordSettings(String email, String pass);
	
	void updateUserImage(MultipartFile file,String userId);
}
