package com.firatkaya.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.firatkaya.model.AuthenticationRequest;
import com.firatkaya.model.User;
import com.firatkaya.model.UserPermissions;
import com.firatkaya.security.JwtUtil;
import com.firatkaya.service.EmailService;
import com.firatkaya.service.UserService;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/v1/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtUtil jwtUtil;
	
	//Non-Authenticate
	@PostMapping(value = "/login")
	public ResponseEntity<?> login(@RequestBody AuthenticationRequest authRequest) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		}
		catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or passwod. ",e);
		}
		final UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
		final String jwt = jwtUtil.generateToken(userDetails);
		
		return ResponseEntity.ok(jwt);	
	}
	
	//Authenticate
	@GetMapping(value="/username/{username}")
	public ResponseEntity<?> getUserByUsername(@PathVariable  (value = "username",required = true) String username){
		return ResponseEntity.ok(userService.getUserbyUsername(username));
	}
	
	//Authenticate
	@PostMapping(value="/username/photo")
	public ResponseEntity<?> getUserPhotoByUsername(@Validated @RequestBody HashMap<String, String>  request){
		User user = userService.getUserbyUsername(request.get("username"));
		if(user != null) {
			return ResponseEntity.ok(user.getUserProfilPhoto());
		} 
		User user2 = userService.getUser(request.get("username"));
		if(user2 != null) {
			List<String> list = new ArrayList<String>();
			list.add(user2.getUserName());
			list.add(user2.getUserProfilPhoto());
			return ResponseEntity.ok(list);
		} 
		return ResponseEntity.notFound().build();	
	}
	
	
	@PostMapping(value="/register")
	public ResponseEntity<?> addUser(@Validated @RequestBody  User user){
		userService.saveUser(user);
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@PutMapping(value="/update/userpermissions/{username}")
	public ResponseEntity<?> updateUserPermissions(@RequestBody UserPermissions userPermissions,@PathVariable(value = "username",required = true) String username){
		return ResponseEntity.ok(userService.updateUserPermissions(username,userPermissions));	
	}
	
	 //Non-Authenticate
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
	
	//Authenticate
	@PostMapping("/sendemail")
	public ResponseEntity<?> sendVerificationEmail(@RequestBody HashMap<String, String> request) throws MessagingException {
		emailService.sendVerificationEmail(request);
		return ResponseEntity.ok(HttpStatus.OK);
	}

	//Non- Authenticate
	@PostMapping("/sendResetEmail")
	public ResponseEntity<?> sendResetEmail(@RequestBody HashMap<String, String>  request) throws MessagingException {
		emailService.sendResetPasswordEmail(request);
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	//Non- Authenticate
	@PostMapping("/reset")
	public ResponseEntity<?> resetPassword(@RequestBody HashMap<String, String>  request) throws MessagingException {
		userService.updatePassword(request);
		return ResponseEntity.ok(HttpStatus.OK);	
	}
	
	// Authenticate
	@GetMapping(value = "search/{keyword}")
	public ResponseEntity<Collection<?>> searchUser(@PathVariable(value = "keyword",required = true) String keyword) {
		return ResponseEntity.ok(userService.searchUser(keyword));
	}
	
	// Non-Authenticate
	@PostMapping(value = "/validaterecaptcha")
	public ResponseEntity<?> validatereCaptcha(@RequestBody HashMap<String, String>  request) {
		return ResponseEntity.ok(userService.validateCaptcha(request.get("key")));
	}
	// Authenticate
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
    //Authenticate
	@PostMapping(value = "/updatepicture/{userId}")
	public ResponseEntity<?> updatepicture(@RequestParam("file")  MultipartFile file,@PathVariable(value="userId") String userId) throws IOException {
		userService.updateUserImage(file,userId);
		return ResponseEntity.ok(HttpStatus.OK);
	}
		
}
