package com.firatkaya.controller;

import com.firatkaya.entity.User;
import com.firatkaya.model.AuthenticationRequest;
import com.firatkaya.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
@RequestMapping("api/v1")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoginController {

    private final UserService userService;

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authRequest, HttpServletResponse res) throws Exception {
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


    @PostMapping(value = "/recaptcha")
    public ResponseEntity<?> validateGoogleCaptcha(@RequestBody HashMap<String, String> request) {
        return ResponseEntity.ok(userService.validateCaptcha(request.get("key")));
    }

}
