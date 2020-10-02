package com.firatkaya.controller;

import com.firatkaya.service.OauthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/oauth")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OauthController {

    private final OauthService oauthService;

    @PostMapping(value = "/github")
    public ResponseEntity<?> authGithub(@RequestParam String code) throws Exception {
        return ResponseEntity.ok(oauthService.oAuthGithubUserAuthenticate(code));
    }

    @PostMapping(value = "/linkedin")
    public ResponseEntity<?> authLinkedin(@RequestParam String code) throws Exception {
        return ResponseEntity.ok(oauthService.oAuthLinkedinUserAuthenticate(code));
    }

}
