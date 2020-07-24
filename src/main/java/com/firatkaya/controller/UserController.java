package com.firatkaya.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.web.multipart.MultipartFile;

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
	
	@PutMapping(value="/update/userpermissions/{username}")
	public ResponseEntity<?> updateUserPermissions(@RequestBody UserPermissions userPermissions,@PathVariable(value = "username",required = true) String username){
		
		if(userService.updateUserPermissions(username,userPermissions)) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); 
	}

	@DeleteMapping
	public ResponseEntity<?> deleteByUsername(@RequestBody HashMap<String, String> request) {
		
		boolean result = userService.deleteUser(request.get("email"));
		
		if(result)
			return ResponseEntity.status(HttpStatus.OK).build(); 
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); 
	}
	
	@PostMapping("/verification")
	public ResponseEntity<?> verificationUser(@RequestBody HashMap<String, String> request){
		
		if(userService.verificationUser(request.get("id"), request.get("email")))   {
			User user = userService.getUser(request.get("email"));
			user.setVerification(true);
			userService.updateUser(user);
			try {
				emailService.sendSuccessVerification(user.getUserEmail());
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			return ResponseEntity.ok(HttpStatus.OK);
		} 		
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
	
	@PostMapping("/sendemail")
	public ResponseEntity<?> sendVerificationEmail(@RequestBody HashMap<String, String> request) throws MessagingException {
		User user = userService.getUser(request.get("email"));
		emailService.sendVerificationEmail(user.getUserEmail(),user.getUserId());
		return ResponseEntity.ok(HttpStatus.OK);
		
	}
	
	@PostMapping("/sendResetEmail")
	public ResponseEntity<?> sendResetEmail(@RequestBody HashMap<String, String>  request) throws MessagingException {
		
		User user = userService.getUser(request.get("email"));
		if(user !=null ) {
			emailService.sendResetPasswordEmail(user.getUserEmail(),user.getUserId());
			return ResponseEntity.ok(HttpStatus.OK);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/reset")
	public ResponseEntity<?> resetPassword(@RequestBody HashMap<String, String>  request) throws MessagingException {
		User user = userService.getUser(request.get("email"));
		if(user !=null ) {
			userService.updatePassword(user.getUserEmail(), request.get("password"));
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
	
	@PutMapping(value="/update")
	public ResponseEntity<?> updateUserUsername(@RequestBody HashMap<String, String>  request){
	String key=request.get("key");
	String useremail = request.get("email");
	switch (key) {
		case "username":
			userService.updateUserUsername(useremail, request.get("username"));
			break;
		case "githubaddress":
			userService.updateUserGithubAddress(useremail, request.get("githubaddress"));			
			break;	
		case "linkedinaddress":
			userService.updateUserLinkedinAddress(useremail, request.get("linkedinaddress"));			
			break;
		case "birthdate":
			userService.updateUserBirthDate(useremail, request.get("birthdate"));	
			break;	
		default:
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	return ResponseEntity.status(HttpStatus.OK).build(); 
	
	
	}
	
	@PostMapping(value = "/updatepicture/{userId}")
	public ResponseEntity<?> updatepicture(@RequestParam MultipartFile file,@PathVariable(value="userId") String userId) throws IOException {
		
		System.out.println("Post Saati :"+new Date());
		userService.updateUserImage(file,userId);
		
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
}
