package com.firatkaya.service.Impl;

import java.util.Collection;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.firatkaya.model.Post;
import com.firatkaya.model.PostExceptr;
import com.firatkaya.model.PostExceptrSearch;
import com.firatkaya.repository.CommentRepository;
import com.firatkaya.repository.PostRepository;
import com.firatkaya.service.PostService;

@Service
public class PostServiceImp implements PostService{
	
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	CommentRepository commentRepository;

	@Override
	public Page<PostExceptr> getAllPost(int pageNumber,int pageSize,String sortedBy,String orderBy) {
		Sort sort; 
		if(orderBy.equals("asc")) 
			 sort = Sort.by(sortedBy).ascending();
		 else 
			 sort = Sort.by(sortedBy).descending();
		
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize,sort);
	
		return postRepository.findAllProjectedBy(pageable);
	}

	@Override
	public Post getPost(String postId) {
		return postRepository.findByPostIdOrderByPostTimeAsc(postId);
	}

	@Override
	public Post savePost(Post post) {
		post.setPostId(UUID.randomUUID().toString());
		postRepository.save(post);
		return post;
	}

	@Override
	public Post updatePost(Post post) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deletePost(String postId) {
		postRepository.deleteById(postId);
		return true;
	}

	public Collection<?> lastPost(int limit,String ordertype){
		if(ordertype.toLowerCase().equals("desc"))
			return postRepository.orderByDesc(limit,PostExceptr.class);
		else if(ordertype.toLowerCase().equals("asc"))
			return postRepository.orderByAsc(limit,PostExceptr.class);
		else  return null;
	}
	
	public Collection<?> searchPost(String keyword){
		return postRepository.searchByTitleHeaderTag(keyword,PostExceptrSearch.class);
	}

	@Override
	public Post getByPostTitle(String postTitle) {
		return postRepository.findByPostTitle(postTitle);
	}
	
}
