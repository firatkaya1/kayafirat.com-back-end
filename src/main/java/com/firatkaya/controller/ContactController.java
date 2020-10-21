package com.firatkaya.controller;

import com.firatkaya.entity.Contact;
import com.firatkaya.entity.User;
import com.firatkaya.model.AuthenticationRequest;
import com.firatkaya.service.ContactService;
import com.firatkaya.validation.constraint.ValidateCaptcha;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/v1/contact")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ContactController {

    private final ContactService contactService;

    @GetMapping
    public ResponseEntity<?> login(@RequestBody Contact contact, @RequestParam @ValidateCaptcha String captcha)   {
        return ResponseEntity.ok(HttpStatus.OK);
    }


}
