package com.firatkaya.controller;

import com.firatkaya.entity.User;
import com.firatkaya.model.AuthenticationRequest;
import com.firatkaya.service.UserService;
import com.firatkaya.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
@RequestMapping("api/v1")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoginController {

    private final UserService userService;
    private final CookieUtil cookieUtil;

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authRequest, HttpServletResponse res) throws Exception {
        String token = userService.authenticateUser(authRequest);
        User user = userService.getUserByEmail(authRequest.getUsername());
        res.addCookie(cookieUtil.createCookie("authenticate",token,true,true));
        res.addCookie(cookieUtil.createCookie("username",user.getUserName(),false,false));
        res.addCookie(cookieUtil.createCookie("userPhoto",user.getUserProfilePhoto(),false,false));
        res.setHeader("Access-Control-Allow-Credentials", "true");
        return ResponseEntity.ok().body(token);
    }


    @PostMapping(value = "/logout")
    public ResponseEntity<?> logout(HttpServletResponse res) {
        res.addCookie(cookieUtil.deleteCookie("authenticate"));
        res.addCookie(cookieUtil.deleteCookie("username"));
        res.addCookie(cookieUtil.deleteCookie("userPhoto"));
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @PostMapping(value = "/recaptcha")
    public ResponseEntity<?> validateGoogleCaptcha(@RequestBody HashMap<String, String> request) {
        return ResponseEntity.ok(userService.validateCaptcha(request.get("key")));
    }

}
