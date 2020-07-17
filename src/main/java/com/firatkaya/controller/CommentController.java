package com.firatkaya.controller;

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

import com.firatkaya.model.Comment;
import com.firatkaya.service.CommentService;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("api/v1/comment")
public class CommentController {

	
	@Autowired
	CommentService commentService;
	
	@GetMapping
	public ResponseEntity<List<Comment>> getAllComments(){
		return ResponseEntity.ok(commentService.getAllComments());
	}
	
	@GetMapping(value = "postId/{postId}")
	public ResponseEntity<List<Comment>> getPostComments(@PathVariable(value = "postId",required = true) String postId){
		return ResponseEntity.ok(commentService.getAllComments(postId));
	}
	
	@GetMapping(value = "/{commentId}")
	public ResponseEntity<Comment> getOneComment(@PathVariable(value = "commentId",required = true) String commentId){
		return ResponseEntity.ok(commentService.getOneComment(commentId));
	}
	
	@PostMapping(value = "/{postId}")
	public ResponseEntity<?> addComment(@RequestBody Comment comment,@PathVariable(value = "postId",required = true) String postId){
		commentService.saveComment(comment,postId);
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{commentId}")
	public ResponseEntity<?> deleteComment(@PathVariable(value = "commentId",required = true) String commentId) {
		boolean result = commentService.deleteComment(commentId);
		
		if(result)
			return ResponseEntity.status(HttpStatus.OK).build(); 
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); 
	}
	
	@GetMapping(value = "search/{keyword}")
	public ResponseEntity<Collection<?>> searchComment(@PathVariable(value = "keyword",required = true) String keyword) {
		return ResponseEntity.ok(commentService.searchComment(keyword));
	}
}
