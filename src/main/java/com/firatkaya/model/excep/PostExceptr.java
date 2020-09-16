package com.firatkaya.model.excep;

import org.springframework.data.rest.core.config.Projection;

import com.firatkaya.entity.Post;


@Projection(name = "excerpt", types = Post.class)
public interface PostExceptr  {

	String getPost_Id();

	String getPost_Tag();

	String getPost_Title();

	String getPost_Header();

	String getPost_Time();

	String getPost_Max_View();

	String getPost_Max_Comment();

}
