package com.firatkaya.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.firatkaya.model.User;
import com.firatkaya.model.UserPermissions;
import com.firatkaya.service.EmailService;
import com.firatkaya.service.UserService;


@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("api/v1/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	EmailService emailService;
	
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers(){
		return ResponseEntity.ok(userService.getAllUser());
	}
	
	@GetMapping(value = "/login")
	public ResponseEntity<HttpStatus> checkUser(@RequestParam Map<String,String> requestParams) {
		String userEmail = requestParams.get("email");
		String userPassword = requestParams.get("password");
		User user = userService.getUser(userEmail);
		if(user != null) {
			if(user.getUserPassword().equals(userPassword)) {
				return ResponseEntity.ok(HttpStatus.OK);
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}		
		}
		return ResponseEntity.notFound().build();	
	}
	
	@GetMapping(value="/username/{username}")
	public ResponseEntity<?> getUserByUsername(@PathVariable(value = "username",required = true) String username){
		
		User user = userService.getUserbyUsername(username);
		List<User> myList = new ArrayList<>();
		if(user != null) {
			myList.add(user);
				return ResponseEntity.ok(myList);
		
		}
		return ResponseEntity.notFound().build();	
	}
	
	@PostMapping(value="/register")
	public ResponseEntity<?> addUser(@RequestBody User user){
		
		if(userService.saveUser(user) != null) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.status(HttpStatus.CONFLICT).build(); 
	}
	
	@PutMapping(value="/update")
	public ResponseEntity<?> updateUser(@RequestBody User user){
		
		if(userService.updateUser(user) != null) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); 
	}
	
	@PutMapping(value="/update/userpermissions/{username}")
	public ResponseEntity<?> updateUserPermissions(@RequestBody UserPermissions userPermissions,@PathVariable(value = "username",required = true) String username){
		
		if(userService.updateUserPermissions(username,userPermissions)) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); 
	}

	@DeleteMapping(value = "/{email}")
	public ResponseEntity<?> deleteByUsername(@PathVariable(value = "email",required = true) String email) {
		
		boolean result = userService.deleteUser(email);
		
		if(result)
			return ResponseEntity.status(HttpStatus.OK).build(); 
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); 
	}
	
	@PostMapping("/verification/{email}/{id}")
	public ResponseEntity<?> verificationUser(@PathVariable(value = "email",required = true) String email,
											  @PathVariable(value = "id",required = true) String id){
		
		if(userService.verificationUser(id, email)) {
			User user = userService.getUser(email);
			user.setVerification(true);
			userService.updateUser(user);
			try {
				emailService.sendSuccessVerification(email);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			return ResponseEntity.ok(HttpStatus.OK);
		} 		
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
	
	@PostMapping("/sendemail/{email}")
	public ResponseEntity<?> sendVerificationEmail(@PathVariable(value = "email",required = true) String email) throws MessagingException {
		User user = userService.getUser(email);
		emailService.sendVerificationEmail(email,user.getUserId());
		return ResponseEntity.ok(HttpStatus.OK);
		
	}
	
	@PostMapping("/sendResetEmail/{email}")
	public ResponseEntity<?> sendResetEmail(@PathVariable(value = "email",required = true) String email) throws MessagingException {
		User user = userService.getUser(email);
		if(user !=null ) {
			emailService.sendResetPasswordEmail(email,user.getUserId());
			return ResponseEntity.ok(HttpStatus.OK);
		}
		
		return ResponseEntity.notFound().build();
		
	}
	
	@PostMapping("/reset/{email}/{password}")
	public ResponseEntity<?> resetPassword(@PathVariable(value = "email",required = true) String email,@PathVariable(value = "password",required = true) String password) throws MessagingException {
		User user = userService.getUser(email);
		if(user !=null ) {
			userService.updatePassword(email, user.getUserId(), password);
			return ResponseEntity.ok(HttpStatus.OK);
		}
		return ResponseEntity.notFound().build();
		
	}
	
	@GetMapping(value = "search/{keyword}")
	public ResponseEntity<Collection<?>> searchUser(@PathVariable(value = "keyword",required = true) String keyword) {
		return ResponseEntity.ok(userService.searchUser(keyword));
	}
	
	@GetMapping(value = "/validaterecaptcha/{key}")
	public ResponseEntity<?> validatereCaptcha(@PathVariable(value = "key",required = true) String key) {
		return ResponseEntity.ok(userService.validateCaptcha(key));
	}
	
	@PutMapping(value="/update/usergithub/{email}/{githubaddress}")
	public ResponseEntity<?> updateUserGithubAddress(@PathVariable(value = "email",required = true) String email,@PathVariable(value = "githubaddress",required = true) String githubaddress){
		
		if(userService.updateUserGithubAddress(email, githubaddress)) {
			return ResponseEntity.status(HttpStatus.OK).build();
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); 
	}
	
	@PutMapping(value="/update/userlinkedin/{email}/{linkedinaddress}")
	public ResponseEntity<?> updateUserLinkedinAddress(@PathVariable(value = "email",required = true) String email,@PathVariable(value = "linkedinaddress",required = true) String linkedinaddress){
		
		if(userService.updateUserLinkedinAddress(email, linkedinaddress)) {
			return ResponseEntity.status(HttpStatus.OK).build();
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); 
	}
	
	@PutMapping(value="/update/userbirthdate/{email}/{birthdate}")
	public ResponseEntity<?> updateUserBirthdate(@PathVariable(value = "email",required = true) String email,@PathVariable(value = "birthdate",required = true) String birthdate){
		
		if(userService.updateUserBirthDate(email, birthdate)) {
			return ResponseEntity.status(HttpStatus.OK).build();
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); 
	}
	
	@PutMapping(value="/update/userpass/{email}/{userpassword}")
	public ResponseEntity<?> updateUserPassword(@PathVariable(value = "email",required = true) String email,@PathVariable(value = "userpassword",required = true) String userpassword){
		
		if(userService.updateUserPasswordSettings(email, userpassword)) {
			return ResponseEntity.status(HttpStatus.OK).build();
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); 
	}
	
	
	
	
	
	
	
	
}
