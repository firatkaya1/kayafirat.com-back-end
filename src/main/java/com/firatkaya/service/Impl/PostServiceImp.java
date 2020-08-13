package com.firatkaya.service.Impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.firatkaya.entity.Post;
import com.firatkaya.exceptions.PostNotFoundException;
import com.firatkaya.exceptions.UnknownOrderedRequestException;
import com.firatkaya.model.excep.PostExceptr;
import com.firatkaya.model.excep.PostExceptrSearch;
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
		if(!postRepository.existsByPostId(postId))
			throw new PostNotFoundException(postId);
		
		return postRepository.findByPostIdOrderByPostTimeAsc(postId);
	}

	public Collection<?> lastPost(int limit,String ordertype){
		if(ordertype.toLowerCase().equals("desc"))
			return postRepository.orderByDesc(limit,PostExceptr.class);
		else if(ordertype.toLowerCase().equals("asc"))
			return postRepository.orderByAsc(limit,PostExceptr.class);
		else  throw new UnknownOrderedRequestException();
	}
	
	public Page<PostExceptrSearch> searchPost(String keyword,int pageNumber,int pageSize,String sortedBy,String orderBy){
		Sort sort; 
		if(orderBy.equals("asc")) 
			 sort = Sort.by(sortedBy).ascending();
		 else 
			 sort = Sort.by(sortedBy).descending();
		
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize,sort);
		
		return postRepository.searchByTitleHeaderTag(pageable,keyword);
	}

	@Override
	public Post getByPostTitle(String postTitle) {
		return postRepository.findByPostTitle(postTitle);
	}

	@Override
	public Collection<?> getByPostTag(String postTag) {
		return postRepository.findByAllPostTag(postTag, PostExceptr.class);
	}
	
}
