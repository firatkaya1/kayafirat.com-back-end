package com.firatkaya.model;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "userExceptr", types = User.class)
public interface UserExceptr {

	String getUser_email();
	
	String getUser_name();
	
	String getUser_profile_photo();
	
	
	
}
