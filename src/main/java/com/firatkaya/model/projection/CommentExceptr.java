package com.firatkaya.model.projection;

import org.springframework.data.rest.core.config.Projection;

import com.firatkaya.entity.Comment;

@Projection(name = "commentExceptr", types = Comment.class)
public interface CommentExceptr {

	String getComment_Id();
	
	String getUser_name();
	
	String getComment_Message();

	String getComment_Time();

	String getIs_Hide();

}
