package com.firatkaya.service.Impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.firatkaya.entity.User;
import com.firatkaya.entity.UserPermissions;
import com.firatkaya.entity.UserProfile;
import com.firatkaya.exceptions.EmailException;
import com.firatkaya.exceptions.UserEmailAlreadyExistsException;
import com.firatkaya.exceptions.UserNameAlreadyExistsException;
import com.firatkaya.exceptions.UserNotFoundException;
import com.firatkaya.model.excep.UserExceptr;
import com.firatkaya.repository.CommentRepository;
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
	private CommentRepository commentRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private  BCryptPasswordEncoder bCryptPasswordEncoder;
	
	private final RestTemplate restTemplate;
	
	public UserServiceImp(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }
	
	@Override
	public User getUser(String email) {
		User user = userRepository.findByUserEmail(email);
		
		if(user == null) {
			throw new UserNotFoundException(email);
		}
		
		return user;
	}

	@Override
	public User saveUser(User user) {

		
		if(userRepository.existsByUserEmail(user.getUserEmail())) {
			throw new UserEmailAlreadyExistsException(user.getUserEmail());
			
		} else if(userRepository.existsByUserName(user.getUserName())) {
			throw new UserNameAlreadyExistsException(user.getUserName());
		}
		
		UserProfile userProfile = new UserProfile();
		UserPermissions userPermissions = new UserPermissions();
		userProfile.setUserEmail(user.getUserEmail());
		userPermissions.setUserEmail(user.getUserEmail()); 
		user.setUserId(UUID.randomUUID().toString());
		user.setUserProfile(userProfile);
		user.setUserPermissions(userPermissions);
		user.setUserProfilPhoto(DEFAULT_PROFIL_PHOTO);
		
		return  userRepository.save(user);
		
		 
	}

	@Transactional
	@Override
	public User updateUser(User user) {
		User result = null;
		if(userRepository.existsByUserEmail(user.getUserEmail())) 
			result = userRepository.save(user);
		else 
			throw new UserNotFoundException(user.getUserEmail());
		return result;
	}
	
	@Transactional
	@Override
	public boolean updateUserPermissions(String username,UserPermissions userPermissions) {
		User user = userRepository.findByUserName(username);
		if(userRepository.existsByUserEmail(user.getUserEmail())) {
			  userPermissions.setUserEmail(user.getUserEmail());
		      userRepository.updateUserPermissions(userPermissions);
		} else {
			throw new UserNotFoundException(username);
		}
	      return true;
	}
	
	@Transactional
	@Override
	public boolean deleteUser(String email) {
		
		if(userRepository.existsByUserEmail(email)) {
			 userRepository.deleteById(email);
			 return true;
		} else {
			throw new UserNotFoundException(email);
		}
		
		
	}

	@Override
	public boolean verificationUser(String userId, String email) {
		boolean	isUserExists;
		isUserExists = userRepository.existsByUserEmailandUserId(email, userId) == 1 ? true : false;
		if(!isUserExists) {
			throw new UserNotFoundException(email);
		}	
		return isUserExists;
	}

	@Override
	public Collection<?> searchUser(String keyword) {
		return userRepository.searchByUsernameAndUseremail(keyword, UserExceptr.class);
	}

	@Override
	public User getUserbyUsername(String username) {
		User user = userRepository.findByUserName(username);
		
		if(user == null) {
			throw new UserNotFoundException(username);
		}
		return user;
	}

	@Override
	public String validateCaptcha(String key) {
		String url = VERİFY_CAPTCHA_URL_V2 + "secret="+SECRET_KEY+"&response="+key;
		return restTemplate.getForObject(url, String.class);
	}
	
	@Transactional
	@Override
	public boolean updatePassword(HashMap<String, String> request) {
		String email =request.get("email");
		String userId = request.get("userid");
		String ipAddress = request.get("ipaddress");
		String userAgent = request.get("useragent");
		String password = request.get("password");
		if(userRepository.existsByUserEmailandUserId(email, userId) == 1) {
			userRepository.updateUserPassword(email, password);
			try {
				emailService.sendSuccessResetPassword(email,ipAddress,userAgent);

			} catch (MessagingException e) {
				throw new EmailException(email);
			}
		} else {
			throw new UserNotFoundException(email);
		}
		
			return true;
	}

	
	@Transactional
	@Override
	public boolean updateUserUsername(String email, String username) {
		if(userRepository.existsByUserEmail(email)) {
			userRepository.updateUserUsernameOnUser(email, username);
			userRepository.updateUserUsernameOnComment(username);
		} else {
			throw new UserNotFoundException(email);
		}

		
		return true;
	}
	
	@Transactional
	@Override
	public boolean updateUserGithubAddress(String email, String githubaddress) {
		
		if(userRepository.existsByUserEmail(email)) {
			userRepository.updateGithubAddress(email, githubaddress);
		} else {
			throw new UserNotFoundException(email);
		}
		return true;
	}
	
	@Transactional
	@Override
	public boolean updateUserLinkedinAddress(String email, String linkedinaddress) {
		if(userRepository.existsByUserEmail(email)) {
			userRepository.updateLinkedinAddress(email, linkedinaddress);
		} else {
			throw new UserNotFoundException(email);
		}
		return true;
	}
	
	@Transactional
	@Override
	public boolean updateUserBirthDate(String email, String date) {
		
		if(userRepository.existsByUserEmail(email)) 
			userRepository.updateUserBirthDate(email, date);
		else 
			throw new UserNotFoundException(email);
		
		
		return true;
	}

	@Transactional
	@Override
	public boolean updateUserPasswordSettings(String email, String pass) {
		if(userRepository.existsByUserEmail(email)) 
			userRepository.updateUserPassword(email, pass);
		else 
			throw new UserNotFoundException(email);
		
		
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
	        commentRepository.updateUserPhoto(userRepository.findByUserId(userId).getUserName(), path.toString().substring(50, path.toString().length()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		boolean isUserExists = userRepository.existsByUserEmail(email);
		if(isUserExists) {
			List<Object[]>  user =  userRepository.findUser(email);
			String userEmail = user.get(0)[0].toString();
			String userPass = bCryptPasswordEncoder.encode(user.get(0)[1].toString());
			return new org.springframework.security.core.userdetails.User(userEmail,userPass,new ArrayList<>());
		}
		else {
			 throw new UserNotFoundException(email);
		}

	}
	
}
