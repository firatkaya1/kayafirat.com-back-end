package com.firatkaya.controller;

import com.firatkaya.entity.User;
import com.firatkaya.model.AuthenticationRequest;
import com.firatkaya.service.UserService;
import com.firatkaya.util.CookieUtil;
import com.firatkaya.validation.constraint.ValidateCaptcha;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/v1")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoginController {

    private final UserService userService;
    private final CookieUtil cookieUtil;

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authRequest, @RequestParam @ValidateCaptcha String captcha, HttpServletResponse res) throws Exception {
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
        res.addCookie(cookieUtil.deleteCookie("authenticate",true,true));
        res.addCookie(cookieUtil.deleteCookie("username",false,false));
        res.addCookie(cookieUtil.deleteCookie("userPhoto",false,false));
        res.setHeader("Access-Control-Allow-Credentials", "true");

        return ResponseEntity.ok(HttpStatus.OK);
    }

    /*
    BU method güvenlik açığına neden olduğu için iptal ediyorum.

    @PostMapping(value = "/recaptcha")
    public ResponseEntity<?> validateGoogleCaptcha(@RequestBody HashMap<String, String> request) {

        return ResponseEntity.ok(userService.validateCaptcha(request.get("key")));
    }

     */


}
