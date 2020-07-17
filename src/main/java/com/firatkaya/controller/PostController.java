	package com.firatkaya.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.firatkaya.model.Post;
import com.firatkaya.model.StaticsViews;
import com.firatkaya.service.PostService;
import com.firatkaya.service.StaticsViewService;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("api/v1/post")
public class PostController {
	
	@Autowired
	PostService postService;
	
	@Autowired
	StaticsViewService staticViewService;
	
	@GetMapping
	public ResponseEntity<List<Post>> getAllPosts(){
		return ResponseEntity.ok(postService.getAllPost());
	}
	
	@GetMapping(value = "postId/{postId}")
	public ResponseEntity<Post> getPost(@PathVariable(value = "postId",required = true) String postId){
		return ResponseEntity.ok(postService.getPost(postId));
	}
	
	@GetMapping(value = "postTitle/{postTitle}")
	public ResponseEntity<List<Post>> getPostTitle(@PathVariable(value = "postTitle",required = true) String postTitle){
		List<Post> myPost= new ArrayList<>();
		myPost.add(postService.getByPostTitle(postTitle));
		return ResponseEntity.ok(myPost);
	}
	
	@PostMapping
	public ResponseEntity<?> addPost(@RequestBody Post post){
		postService.savePost(post);
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{postId}")
	public ResponseEntity<?> deleteByPostId(@PathVariable(value = "postId",required = true) String postId) {
		
		boolean result = postService.deletePost(postId);
		
		if(result)
			return ResponseEntity.status(HttpStatus.OK).build(); 
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); 
	}
	
	
	
	@GetMapping(value = "lastposts/{postnumber}/{ordertype}")
	public ResponseEntity<Collection<?>> lastpost(@PathVariable(value = "postnumber",required = true) int limit,
													@PathVariable(value = "ordertype",required = true) String ordertype) {
		
		return ResponseEntity.ok(postService.lastPost(limit,ordertype));
	}
		
	@GetMapping(value = "search/{keyword}")
	public ResponseEntity<Collection<?>> searchPost(@PathVariable(value = "keyword",required = true) String keyword) {
		return ResponseEntity.ok(postService.searchPost(keyword));
	}
	
	@PostMapping(value = "/updateview")
	public ResponseEntity<?> updateMaxView(@RequestBody StaticsViews staticview) {
		staticViewService.addStaticViews(staticview);
		return ResponseEntity.ok(HttpStatus.OK);
		
	}
	
	
	
	
}
