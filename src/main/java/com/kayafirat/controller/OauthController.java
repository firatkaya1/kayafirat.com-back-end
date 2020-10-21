package com.kayafirat.controller;

import com.kayafirat.entity.User;
import com.kayafirat.service.OauthService;
import com.kayafirat.service.UserService;
import com.kayafirat.util.CookieUtil;
import com.kayafirat.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/v1/auth")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OauthController {

    private final OauthService oauthService;
    private final CookieUtil cookieUtil;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping(value = "/github")
    public ResponseEntity<?> authGithub(@RequestParam String code, HttpServletResponse res) throws Exception {
        String token = oauthService.oAuthGithubUserAuthenticate(code);
        User user = userService.getUserByEmail(jwtUtil.extractUsername(token));
        res.addCookie(cookieUtil.createCookie("authenticate",token,true,true));
        res.addCookie(cookieUtil.createCookie("username",user.getUserName(),false,false));
        res.addCookie(cookieUtil.createCookie("userPhoto",user.getUserProfilePhoto(),false,false));
        res.setHeader("Access-Control-Allow-Credentials", "true");
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping(value = "/linkedin")
    public ResponseEntity<?> authLinkedin(@RequestParam String code, HttpServletResponse res) throws Exception {
        String token = oauthService.oAuthLinkedinUserAuthenticate(code);
        User user = userService.getUserByEmail(jwtUtil.extractUsername(token));
        res.addCookie(cookieUtil.createCookie("authenticate",token,true,true));
        res.addCookie(cookieUtil.createCookie("username",user.getUserName(),false,false));
        res.addCookie(cookieUtil.createCookie("userPhoto",user.getUserProfilePhoto(),false,false));
        res.setHeader("Access-Control-Allow-Credentials", "true");
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
