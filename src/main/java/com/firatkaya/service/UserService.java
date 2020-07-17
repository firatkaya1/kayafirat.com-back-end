package com.firatkaya.service;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import com.firatkaya.model.User;

@Service
public interface UserService {

	List<User> getAllUser();
	
	User getUser(String email);
	
	User getUserbyUserid(String userId);
	
	User saveUser(User user);
	
	User updateUser(User user);
	
	boolean deleteUser(String email);
	
	boolean verificationUser(String userId,String userEmail);
	
	Collection<?> searchUser(String keyword);

	
}
