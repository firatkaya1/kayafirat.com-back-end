package com.firatkaya.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.firatkaya.entity.Comment;
import com.firatkaya.service.CommentService;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("api/v1/comment")
public class CommentController {

	private final CommentService commentService;

	@Autowired
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	@GetMapping(value = "/{postId}")
	public ResponseEntity<Comment> getSingleComment(@PathVariable(value = "postId") String postId){
		return ResponseEntity.ok(commentService.getOneComment(postId));
	}

	@GetMapping(value = "all/{postId}")
	public ResponseEntity<List<Comment>> getPostComments(@PathVariable(value = "postId") String postId){
		return ResponseEntity.ok(commentService.getAllComments(postId));
	}
	
	@PostMapping(value = "/{postId}")
	public ResponseEntity<?> addComment(@RequestBody Comment comment,@PathVariable(value = "postId") String postId){
		commentService.saveComment(comment,postId);
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<?> updateComment(@RequestBody HashMap<String,String> request){
		commentService.updateComment(request);
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@DeleteMapping(value = "/{postId}/{commentId}")
	public ResponseEntity<?> deleteComment(@PathVariable(value = "commentId") String commentId,@PathVariable(value = "postId") String postId){
		System.out.println("commentId:"+commentId);
		System.out.println("post Id :"+postId);
		commentService.deleteComment(commentId,postId);

		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@GetMapping(value = "search/{keyword}")
	public ResponseEntity<Collection<?>> searchComment(@PathVariable(value = "keyword") String keyword) {
		return ResponseEntity.ok(commentService.searchComment(keyword));
	}
}
