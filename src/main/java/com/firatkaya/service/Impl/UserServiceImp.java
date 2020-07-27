package com.firatkaya.service.Impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.firatkaya.model.User;
import com.firatkaya.model.UserExceptr;
import com.firatkaya.model.UserPermissions;
import com.firatkaya.model.UserProfile;
import com.firatkaya.repository.UserRepository;
import com.firatkaya.service.EmailService;
import com.firatkaya.service.UserService;


@Service
public class UserServiceImp implements UserService {
	
	private static final String DEFAULT_PROFIL_PHOTO="assets/images/profile.svg";
	private static final String SECRET_KEY = "6LfC_bIZAAAAAC18vxthubhOnwLOF119RaS-GEC1";
	private static final String VERİFY_CAPTCHA_URL_V2 = "https://www.google.com/recaptcha/api/siteverify?";
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmailService emailService;
	
	private final RestTemplate restTemplate;
	
	public UserServiceImp(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }
	
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
	public boolean updateUserPermissions(String username,UserPermissions userPermissions) {
		User user = userRepository.findByUserName(username);
		if(userRepository.existsByUserEmail(user.getUserEmail())) {
			  userPermissions.setUserEmail(user.getUserEmail());
		      userRepository.updateUserPermissions(userPermissions);
		      return true;
		}
		return false;
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
	public User getUserbyUsername(String username) {
		return userRepository.findByUserName(username);
	}

	@Override
	public String validateCaptcha(String key) {
		String url = VERİFY_CAPTCHA_URL_V2 + "secret="+SECRET_KEY+"&response="+key;
		return restTemplate.getForObject(url, String.class);
	}
	
	@Transactional
	@Override
	public boolean updatePassword(String email,String password) {
		
			userRepository.updateUserPassword(email, password);
			try {
				emailService.sendSuccessResetPassword(email);
				return true;
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			return false;
	}

	
	@Transactional
	@Override
	public boolean updateUserUsername(String email, String username) {
		userRepository.updateUserUsernameOnUser(email, username);
		userRepository.updateUserUsernameOnComment(username);
		return true;
	}
	
	@Transactional
	@Override
	public boolean updateUserGithubAddress(String email, String githubaddress) {
		userRepository.updateGithubAddress(email, githubaddress);
		return true;
	}
	
	@Transactional
	@Override
	public boolean updateUserLinkedinAddress(String email, String linkedinaddress) {
		userRepository.updateLinkedinAddress(email, linkedinaddress);
		return true;
	}
	
	@Transactional
	@Override
	public boolean updateUserBirthDate(String email, String date) {
		userRepository.updateUserBirthDate(email, date);
		return true;
	}

	@Transactional
	@Override
	public boolean updateUserPasswordSettings(String email, String pass) {
		userRepository.updateUserPassword(email, pass);
		return true;
	}
	
	@Transactional
	@Override
	public void updateUserImage(MultipartFile file,String userId) {
		byte[] bytes;
		try {
			bytes = file.getBytes();
			Path path = Paths.get("/home/kaya/Desktop/Angular-Projects/firatkaya/src/assets/upload/" + userId +"."+ file.getOriginalFilename().split("\\.")[1]);
	        Files.write(path, bytes);
	        userRepository.updateUserPhoto(userId, path.toString().substring(50, path.toString().length()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		boolean isUserExists = userRepository.existsByUserEmail(email);
		if(isUserExists) {
			List<Object[]>  user =  userRepository.findUser(email);
			return new org.springframework.security.core.userdetails.User(user.get(0)[0].toString(),user.get(0)[1].toString(),new ArrayList<>());
		}
		else {
			 throw new UsernameNotFoundException(email);
		}

	}
	
}
