package com.firatkaya.model.excep;

import org.springframework.data.rest.core.config.Projection;

import com.firatkaya.entity.User;

@Projection(name = "userExceptr", types = User.class)
public interface UserExceptr {

	String getUser_email();
	
	String getUser_name();
	
	String getUser_profile_photo();
	
	
	
}
