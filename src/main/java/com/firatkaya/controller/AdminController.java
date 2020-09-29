package com.firatkaya.controller;

import com.firatkaya.model.AuthenticationRequest;
import com.firatkaya.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/admin")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdminController {

    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;


    @PostMapping(value = "/auth")
    public ResponseEntity<?> auth(@RequestBody AuthenticationRequest authRequest) throws Exception {
        return ResponseEntity.ok().body(userService.authenticateUser(authRequest));
    }

    @GetMapping(value ="/posts")
    public ResponseEntity<?> getPosts(){
        return ResponseEntity.ok(postService.getAllPost());
    }

    @GetMapping(value ="/comments")
    public ResponseEntity<?> getComments(){
        return ResponseEntity.ok(commentService.getAllComments());
    }

    @GetMapping(value ="/users/{email}")
    public ResponseEntity<?> getComments(@PathVariable("email") String email){
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @PostMapping(value = "/test")
    public ResponseEntity<?> test()   {
        return ResponseEntity.ok().body("test başarılı");
    }


    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable("postId") String postId)   {
        postService.deletePost(postId);
        return ResponseEntity.ok().body(postId);
    }

}
