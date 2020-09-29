package com.firatkaya.model.projection;

import org.springframework.data.rest.core.config.Projection;

import com.firatkaya.entity.Post;

@Projection(name = "excerptSearch", types = Post.class)
public interface PostExceptrSearch {
	
	String getPost_Id();
	
	String getPost_Tag();
	
	String getPost_Title();
	
	String getPost_Header();
	
	
}
