package com.firatkaya.controller;

import com.firatkaya.model.AuthenticationRequest;
import com.firatkaya.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@RestController
@RequestMapping("/v1/admin")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdminController {

    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;


    @GetMapping(value ="/posts")
    public ResponseEntity<?> getPosts(){
        return ResponseEntity.ok(postService.getAllPost());
    }

    @GetMapping(value ="/comments")
    public ResponseEntity<?> getComments(){
        return ResponseEntity.ok(commentService.getAllComments());
    }

    @GetMapping(value ="/comments/detail")
    public ResponseEntity<?> getCommentsDetail(@RequestParam String id){
        return ResponseEntity.ok(commentService.getAllCommentsDetails(id));
    }
    @PutMapping(value ="/comments   ")
    public ResponseEntity<?> updateComment(@RequestBody HashMap<String,String> request){
        commentService.updateComment(request);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping(value ="/users/{email}")
    public ResponseEntity<?> getComments(@PathVariable("email") String email){
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }


    @DeleteMapping("post/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable("postId") String postId)   {
        postService.deletePost(postId);
        return ResponseEntity.ok().body(postId);
    }

}
