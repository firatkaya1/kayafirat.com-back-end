package com.firatkaya.controller;

import com.firatkaya.model.AuthenticationRequest;
import com.firatkaya.service.EmailService;
import com.firatkaya.service.OauthService;
import com.firatkaya.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200",allowCredentials = "false")
@RestController
@RequestMapping("api/v1/admin")
public class AdminController {

    private final UserService userService;
    private final EmailService emailService;
    private final OauthService oauthService;

    @Autowired
    public AdminController(UserService userService,EmailService emailService, OauthService oauthService) {
        this.userService = userService;
        this.emailService = emailService;
        this.oauthService = oauthService;
    }

    @PostMapping(value = "/auth")
    public ResponseEntity<?> auth(@RequestBody AuthenticationRequest authRequest) throws Exception {
        return ResponseEntity.ok().body(userService.authenticateUser(authRequest));
    }

    @GetMapping(value ="api/v1/admin/posts")
    public ResponseEntity<?> getPosts(@RequestBody AuthenticationRequest authRequest) throws Exception {
        return ResponseEntity.ok().body(userService.authenticateUser(authRequest));
    }

}
