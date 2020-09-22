package com.firatkaya.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.firatkaya.service.OauthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.firatkaya.exceptions.UserEmailNotFoundException;
import com.firatkaya.exceptions.UserNameNotFoundException;

/**
 * @author firatkaya
 * @version 1.0.0
 */

@CrossOrigin
@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;
    private final EmailService emailService;
    private final OauthService oauthService;

    @Autowired
    public UserController(UserService userService,EmailService emailService, OauthService oauthService) {
        this.userService = userService;
        this.emailService = emailService;
        this.oauthService = oauthService;
    }


    /**
     * Return Json Web Token if user already exists else return
     * an exception. This method provide only POST include body
     *{@link com.firatkaya.model.AuthenticationRequest}  model.
     *{@link com.firatkaya.model.AuthenticationRequest} has two fields.
     *
     * @param authRequest  need to authenticate a user.
     * @return             if user exists, it will return JWT.
     * @throws Exception   if user not found in database, it will thrown BadCredentialsExceptions
     */
    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authRequest, HttpServletResponse res) throws Exception {
        String token = userService.authenticateUser(authRequest);
        Cookie cookie = new Cookie("authenticate", token);
        cookie.setMaxAge(86400);
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        User user = userService.getUser(authRequest.getUsername());
        Cookie usernameCookie = new Cookie("username",user.getUserName());
        usernameCookie.setMaxAge(86400);
        usernameCookie.setPath("/");
        usernameCookie.setSecure(false);
        usernameCookie.setHttpOnly(false);
        Cookie userPhotoCookie = new Cookie("userPhoto",user.getUserProfilePhoto());
        userPhotoCookie.setMaxAge(86400);
        userPhotoCookie.setPath("/");
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
    public ResponseEntity<?> authLinkedin(@RequestBody HashMap<String,String> request) throws Exception {
        return ResponseEntity.ok(oauthService.oAuthLinkedinUserAuthenticate(request.get("code")));
    }

    /**
     * @param username
     * @return
     * @throws UserEmailNotFoundException
     */
    @GetMapping(value = "/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable(value = "username") String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    /**
     * @param request
     * @return
     * @throws UserEmailNotFoundException
     */
    @PostMapping(value = "/email/photo")
    public ResponseEntity<?> getUserPhotoByEmail(@RequestBody HashMap<String, String> request,HttpServletResponse res) {

        return ResponseEntity.ok(HttpStatus.OK);


    }

    /**
     * @param request
     * @return
     * @throws  UserNameNotFoundException
     */
    @PostMapping(value = "/username/photo")
    public ResponseEntity<?> getUserPhotoByUsername(@RequestBody HashMap<String, String> request) {
        return ResponseEntity.ok(userService.getUserByUsername(request.get("username")).getUserProfilePhoto());

    }

    /**
     * @param user
     * @return
     */
    @PostMapping(value = "/register")
    public ResponseEntity<?> addUser(@Validated @RequestBody User user) {
        System.out.println("user :"+user.toString());
        userService.saveUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * @param userPermissions
     * @param username
     * @return
     */
    @PutMapping(value = "/update/userpermissions/{username}")
    public ResponseEntity<?> updateUserPermissions(@RequestBody UserPermissions userPermissions, @PathVariable(value = "username") String username) {
        return ResponseEntity.ok(userService.updateUserPermissions(username, userPermissions));
    }

    /**
     * @param request
     * @return
     */
    @PostMapping("/verification")
    public ResponseEntity<?> verificationUser(@RequestBody HashMap<String, String> request) {

        if (userService.verificationUser(request.get("id"), request.get("email"))) {
            User user = userService.getUser(request.get("email"));
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

    /**
     * @param request
     * @return
     * @throws MessagingException
     */
    @PostMapping("/sendemail")
    public ResponseEntity<?> sendVerificationEmail(@RequestBody HashMap<String, String> request) throws MessagingException {
        emailService.sendVerificationEmail(request);
        return ResponseEntity.ok(HttpStatus.OK);
    }


    /**
     * @param request
     * @return
     * @throws MessagingException
     */
    @PostMapping("/sendResetEmail")
    public ResponseEntity<?> sendResetEmail(@RequestBody HashMap<String, String> request) throws MessagingException {
        emailService.sendResetPasswordEmail(request);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * @param request
     * @return
     */
    @PostMapping("/reset")
    public ResponseEntity<?> resetPassword(@RequestBody HashMap<String, String> request) {
        userService.updatePassword(request);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * @param keyword
     * @return
     */
    @GetMapping(value = "search/{keyword}")
    public ResponseEntity<Collection<?>> searchUser(@PathVariable(value = "keyword") String keyword) {
        return ResponseEntity.ok(userService.searchUser(keyword));
    }

    /**
     * @param request
     * @return
     */
    @PostMapping(value = "/validaterecaptcha")
    public ResponseEntity<?> validateGoogleCaptcha(@RequestBody HashMap<String, String> request) {
        return ResponseEntity.ok(userService.validateCaptcha(request.get("key")));
    }

    /**
     * @param request
     * @return
     */
    @PutMapping(value = "/update")
    public ResponseEntity<?> updateUserUsername(@RequestBody HashMap<String, String> request) {
        String key = request.get("key");
        String useremail = request.get("email");
        switch (key) {
            case "username":
                userService.updateUserUsername(useremail, request.get("username"));
                break;
            case "githubaddress":
                userService.updateUserGithubAddress(useremail, request.get("githubaddress"));
                break;
            case "linkedinaddress":
                userService.updateUserLinkedinAddress(useremail, request.get("linkedinaddress"));
                break;
            case "birthdate":
                userService.updateUserBirthDate(useremail, request.get("birthdate"));
                break;
            default:
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();


    }

    /**
     * @param file
     * @param userId  User ıd
     * @return OK if user image is updated
     */
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
