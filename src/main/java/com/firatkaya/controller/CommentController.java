package com.firatkaya.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.firatkaya.service.UserService;
import com.firatkaya.validation.constraint.ValidateCaptcha;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.firatkaya.entity.Comment;
import com.firatkaya.service.CommentService;

@RestController
@RequestMapping("/v1/comment")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommentController {

	private final CommentService commentService;

	@GetMapping
	public ResponseEntity<List<Comment>> getPostComments(@RequestParam String id){
		return ResponseEntity.ok(commentService.getAllComments(id));
	}
	
	@PostMapping
	public ResponseEntity<?> addComment(@RequestBody Comment comment,@RequestParam String id, @RequestParam @ValidateCaptcha String captcha){
		return ResponseEntity.ok(commentService.saveComment(comment,id));
	}
	@PutMapping
	public ResponseEntity<?> updateComment(@RequestBody HashMap<String,String> request){
		commentService.updateComment(request);
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@DeleteMapping
	public ResponseEntity<?> deleteComment(@RequestParam String commentId){
		commentService.deleteComment(commentId);
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@GetMapping(value = "/search")
	public ResponseEntity<Collection<?>> searchComment(@RequestParam String keyword) {
		return ResponseEntity.ok(commentService.searchComment(keyword));
	}
}
