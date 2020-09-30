package com.firatkaya.controller;

import com.firatkaya.entity.User;
import com.firatkaya.service.EmailService;
import com.firatkaya.service.OauthService;
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

import javax.mail.MessagingException;
import java.util.HashMap;

@RestController
@RequestMapping("api/v1/mail")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MailController {

    private final UserService userService;
    private final EmailService emailService;
    private final OauthService oauthService;

    @PostMapping("/verification")
    public ResponseEntity<?> verificationUser(@RequestBody HashMap<String, String> request) {

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

    @PostMapping
    public ResponseEntity<?> sendVerificationEmail(@RequestBody HashMap<String, String> request) throws MessagingException {
        emailService.sendVerificationEmail(request);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/reset")
    public ResponseEntity<?> sendResetEmail(@RequestBody HashMap<String, String> request) throws MessagingException {
        emailService.sendResetPasswordEmail(request);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/password")
    public ResponseEntity<?> resetPassword(@RequestBody HashMap<String, String> request) {
        userService.updatePassword(request);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
