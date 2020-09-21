package com.firatkaya.controller;

import com.firatkaya.model.AuthenticationRequest;
import com.firatkaya.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping("api/v1/admin")
public class AdminController {

    private final UserService userService;
    private final EmailService emailService;
    private final OauthService oauthService;
    private final PostService postService;
    private final CommentService commentService;

    @Autowired
    public AdminController(UserService userService,EmailService emailService, OauthService oauthService,PostService postService,CommentService commentService) {
        this.userService = userService;
        this.emailService = emailService;
        this.oauthService = oauthService;
        this.postService = postService;
        this.commentService = commentService;
    }

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

    @PostMapping(value = "/test")
    public ResponseEntity<?> test()   {
        return ResponseEntity.ok().body("test başarılı");
    }

}
