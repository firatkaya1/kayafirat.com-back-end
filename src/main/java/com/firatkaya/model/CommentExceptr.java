package com.firatkaya.model;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "commentExceptr", types = Comment.class)
public interface CommentExceptr {

	String getComment_Id();
	
	String getUser_Id();
	
	String getComment_Message();
	
}
