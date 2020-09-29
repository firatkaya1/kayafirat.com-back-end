package com.firatkaya.controller;

import java.util.Collection;
import java.util.HashMap;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.firatkaya.service.OauthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.firatkaya.entity.User;
import com.firatkaya.entity.UserPermissions;
import com.firatkaya.model.AuthenticationRequest;
import com.firatkaya.service.EmailService;
import com.firatkaya.service.UserService;

/**
 * @author firatkaya
 * @version 1.0.0
 */

@RestController
@RequestMapping("api/v1/user")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;
    private final EmailService emailService;
    private final OauthService oauthService;


    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody  AuthenticationRequest authRequest, HttpServletResponse res) throws Exception {
        String token = userService.authenticateUser(authRequest);
        Cookie cookie = new Cookie("authenticate", token);
        cookie.setMaxAge(86400);
       // cookie.setDomain("kayafirat.com");
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        User user = userService.getUserByEmail(authRequest.getUsername());
        Cookie usernameCookie = new Cookie("username",user.getUserName());
        usernameCookie.setMaxAge(86400);
        //  usernameCookie.setDomain("kayafirat.com");
        usernameCookie.setPath("/");
        usernameCookie.setSecure(false);
        usernameCookie.setHttpOnly(false);
        Cookie userPhotoCookie = new Cookie("userPhoto",user.getUserProfilePhoto());
        userPhotoCookie.setMaxAge(86400);
        userPhotoCookie.setPath("/");
        //   userPhotoCookie.setDomain("kayafirat.com");
        userPhotoCookie.setSecure(false);
        userPhotoCookie.setHttpOnly(false);
        res.addCookie(usernameCookie);
        res.addCookie(userPhotoCookie);
        res.addCookie(cookie);
        res.setHeader("Access-Control-Allow-Credentials", "true");

        return ResponseEntity.ok().body(token);
    }

    @PostMapping(value = "/auth/github")
    public ResponseEntity<?> authGithub(@RequestBody HashMap<String,String> request) throws Exception {
        String jwt = oauthService.oAuthGithubUserAuthenticate(request.get("code"));
        System.out.println("jwt : "+jwt);
        return ResponseEntity.ok(jwt);
    }

    @PostMapping(value = "/auth/linkedin")
    public ResponseEntity<?> authLinkedin(@RequestBody HashMap<String, String> request) throws Exception {
        return ResponseEntity.ok(oauthService.oAuthLinkedinUserAuthenticate(request.get("code")));
    }

    @GetMapping(value = "/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable(value = "username")  String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }


    @GetMapping(value = "/email/photo/{email}")
    public ResponseEntity<?> getUserPhotoByEmail(@PathVariable(value = "email") String email) {
        System.out.println("email photo :"+email);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping(value = "/username/photo/{username}")
    public ResponseEntity<?> getUserPhotoByUsername(@PathVariable(value = "username")  String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username).getUserProfilePhoto());

    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> addUser(@Validated @RequestBody User user) {
        userService.saveUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping(value = "/update/permissions/{username}")
    public ResponseEntity<?> updateUserPermissions(@RequestBody UserPermissions userPermissions, @PathVariable(value = "username")  String username) {
        return ResponseEntity.ok(userService.updateUserPermissions(username, userPermissions));
    }

    @PostMapping("/verification")
    public ResponseEntity<?> verificationUser( @RequestBody  HashMap<String, String>  request) {

        if (userService.updateUserVerification(request.get("id"), request.get("email"))) {
            User user = userService.getUserByEmail(request.get("email"));
            user.setVerification(true);
            userService.updateUser(user);
            try {
                emailService.sendSuccessVerification(user.getUserEmail());
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return ResponseEntity.ok(HttpStatus.OK);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/sendmail")
    public ResponseEntity<?> sendVerificationEmail(@RequestBody HashMap<String, String> request) throws MessagingException {
        emailService.sendVerificationEmail(request);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/sendResetEmail")
    public ResponseEntity<?> sendResetEmail(@RequestBody HashMap<String, String> request) throws MessagingException {
        emailService.sendResetPasswordEmail(request);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/reset")
    public ResponseEntity<?> resetPassword(@RequestBody HashMap<String, String> request) {
        userService.updatePassword(request);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping(value = "search/{keyword}")
    public ResponseEntity<Collection<?>> searchUser(@PathVariable(value = "keyword") String keyword) {
        return ResponseEntity.ok(userService.searchUser(keyword));
    }

    @PostMapping(value = "/validaterecaptcha")
    public ResponseEntity<?> validateGoogleCaptcha(@RequestBody HashMap<String, String> request) {
        return ResponseEntity.ok(userService.validateCaptcha(request.get("key")));
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> updateUserUsername(@RequestBody HashMap<String, String> request) {
        String key = request.get("key");
        String usermail = request.get("email");
        switch (key) {
            case "username":
                userService.updateUserUsername(usermail, request.get("username"));
                break;
            case "githubaddress":
                userService.updateUserGithubAddress(usermail, request.get("githubaddress"));
                break;
            case "linkedinaddress":
                userService.updateUserLinkedinAddress(usermail, request.get("linkedinaddress"));
                break;
            case "birthdate":
                userService.updateUserBirthDate(usermail, request.get("birthdate"));
                break;
            default:
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();


    }


    @PostMapping(value = "/updatepicture/{userId}")
    public ResponseEntity<?> updatepicture(@RequestParam("file") MultipartFile file, @PathVariable(value = "userId") String userId) {
        userService.updateUserImage(file, userId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<?> logout(HttpServletResponse res) {
        Cookie cookie = new Cookie("authenticate","");
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        Cookie usernameCookie = new Cookie("username","");
        usernameCookie.setMaxAge(0);
        usernameCookie.setPath("/");
        usernameCookie.setSecure(false);
        usernameCookie.setHttpOnly(false);
        Cookie userPhotoCookie = new Cookie("userPhoto","");
        userPhotoCookie.setMaxAge(0);
        userPhotoCookie.setPath("/");
        userPhotoCookie.setSecure(false);
        userPhotoCookie.setHttpOnly(false);
        res.addCookie(usernameCookie);
        res.addCookie(userPhotoCookie);
        res.addCookie(cookie);

        return ResponseEntity.ok(HttpStatus.OK);
    }


}
