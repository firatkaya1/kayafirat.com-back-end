package com.firatkaya.service.Impl;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.firatkaya.model.User;
import com.firatkaya.model.UserExceptr;
import com.firatkaya.model.UserPermissions;
import com.firatkaya.model.UserProfile;
import com.firatkaya.repository.UserRepository;
import com.firatkaya.service.UserService;


@Service
public class UserServiceImp implements UserService {
	
	private static final String DEFAULT_PROFIL_PHOTO="assets/images/profile.svg";
	
	@Autowired
	private UserRepository userRepository;

	
	
	@Override
	public List<User> getAllUser() {
		return (List<User>) userRepository.findAll();
	}

	@Override
	public User getUser(String email) {
		return userRepository.findByUserEmail(email);
	}

	@Override
	public User saveUser(User user) {

		User result = null;
		
		if(!userRepository.existsByUserEmail(user.getUserEmail())) {
			UserProfile userProfile = new UserProfile();
			UserPermissions userPermissions = new UserPermissions();
			userProfile.setUserEmail(user.getUserEmail());
			userPermissions.setUserEmail(user.getUserEmail()); 
			user.setUserId(UUID.randomUUID().toString());
			user.setUserProfile(userProfile);
			user.setUserPermissions(userPermissions);
			user.setUserProfilPhoto(DEFAULT_PROFIL_PHOTO);
			result = userRepository.save(user);
		}
		return result;
	}

	@Transactional
	@Override
	public User updateUser(User user) {
		User result = null;
		if(userRepository.existsByUserEmail(user.getUserEmail())) 
			result = userRepository.save(user);
		
		return result;
	}
	
	@Transactional
	@Override
	public boolean deleteUser(String email) {
		
		if(userRepository.existsByUserEmail(email)) {
			 userRepository.deleteById(email);
			 return true;
		}
		
		return false;
	}

	@Override
	public boolean verificationUser(String userId, String email) {
		boolean	isUserExists;
		isUserExists = userRepository.existsByUserEmailandUserId(email, userId) == 1 ? true : false;
		return isUserExists;
	}

	@Override
	public Collection<?> searchUser(String keyword) {
		return userRepository.searchByUsernameAndUseremail(keyword, UserExceptr.class);
	}

	@Override
	public User getUserbyUserid(String userId) {
		return userRepository.findByUserId(userId);
	}

	
	
}
