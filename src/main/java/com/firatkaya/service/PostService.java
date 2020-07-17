package com.firatkaya.service;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import com.firatkaya.model.Post;

@Service
public interface PostService {

	List<Post> getAllPost();
	
	Post getPost(String postId);

	Post getByPostTitle(String postTitle);
	
	Post savePost(Post post);
	
	Post updatePost(Post post);
	
	boolean deletePost(String postId);
	
	Collection<?> lastPost(int limit,String ordertype);
	
	Collection<?> searchPost(String keyword);
}
