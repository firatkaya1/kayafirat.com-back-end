package com.firatkaya.service;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.firatkaya.model.Post;
import com.firatkaya.model.PostExceptr;
import com.firatkaya.model.PostExceptrSearch;

@Service
public interface PostService {

	Page<PostExceptr>   getAllPost(int pageNumber,int pageSize,String sortedBy,String orderBy);
	
	Post getPost(String postId);

	Post getByPostTitle(String postTitle);
	
	Post savePost(Post post);
	
	Post updatePost(Post post);
	
	boolean deletePost(String postId);
	
	Collection<?> lastPost(int limit,String ordertype);
	
	Page<PostExceptrSearch> searchPost(String keyword,int pageNumber,int pageSize,String sortedBy,String orderBy);
	
	Collection<?> getByPostTag(String postTag);
	
	/*
	boolean checkImage(MultipartFile file) throws IOException;
	
	boolean checkScanId(String scanId); 
	*/
}
