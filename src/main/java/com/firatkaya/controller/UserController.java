package com.firatkaya.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
import com.firatkaya.service.UserService;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("api/v1/user")
public class UserController {

	@Autowired
	UserService userService;
	
	
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
	
	@GetMapping(value="/userid/{userid}")
	public ResponseEntity<?> getUser(@PathVariable(value = "userid",required = true) String userid){
		
		User user = userService.getUserbyUserid(userid);
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

	@DeleteMapping(value = "/{email}")
	public ResponseEntity<?> deleteByUsername(@PathVariable(value = "email",required = true) String email) {
		
		boolean result = userService.deleteUser(email);
		
		if(result)
			return ResponseEntity.status(HttpStatus.OK).build(); 
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); 
	}
	
	@GetMapping("/verification/{email}/{id}")
	public ResponseEntity<?> verificationUser(@PathVariable(value = "email",required = true) String email,
											  @PathVariable(value = "id",required = true) String id){
		
		if(userService.verificationUser(id, email)) {
			User user = userService.getUser(email);
			user.setVerification(true);
			userService.updateUser(user);
			return ResponseEntity.ok(HttpStatus.OK);
		} 		
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
	
	@GetMapping(value = "search/{keyword}")
	public ResponseEntity<Collection<?>> searchUser(@PathVariable(value = "keyword",required = true) String keyword) {
		return ResponseEntity.ok(userService.searchUser(keyword));
	}
	
}
