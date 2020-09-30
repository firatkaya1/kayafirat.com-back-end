package com.firatkaya.controller;

import com.firatkaya.service.OauthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("api/v1/oauth")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OauthController {

    private final OauthService oauthService;

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

}
